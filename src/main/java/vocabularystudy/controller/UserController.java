package vocabularystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vocabularystudy.config.SecurityConfig;
import vocabularystudy.form.ModifyPasswordForm;
import vocabularystudy.form.ModifyUserProfileForm;
import vocabularystudy.form.UserLoginForm;
import vocabularystudy.form.UserRegisterForm;
import vocabularystudy.model.Category;
import vocabularystudy.model.User;
import vocabularystudy.model.Vocabulary;
import vocabularystudy.repository.CategoryRepository;
import vocabularystudy.repository.LearnWordHistoryRepository;
import vocabularystudy.repository.UserRepository;
import vocabularystudy.repository.VocabularyRepository;
import vocabularystudy.util.PasswordUtil;
import vocabularystudy.util.UserInfoUtil;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Autowired
    private LearnWordHistoryRepository learnWordHistoryRepository;

    private class UserProfileException extends Exception
    {
        private String link;

        private String info;

        public UserProfileException(String link, String info)
        {
            this.link = link;
            this.info = info;
        }

        public String getLink()
        {
            return link;
        }

        public void setLink(String link)
        {
            this.link = link;
        }

        public String getInfo()
        {
            return info;
        }

        public void setInfo(String info)
        {
            this.info = info;
        }
    }

    public class Progress
    {
        public User getUser()
        {
            return user;
        }

        public void setUser(User user)
        {
            this.user = user;
        }

        public Category getCategory()
        {
            return category;
        }

        public void setCategory(Category category)
        {
            this.category = category;
        }

        public Long getTotal()
        {
            return total;
        }

        public void setTotal(Long total)
        {
            this.total = total;
        }

        public Long getLearned()
        {
            return learned;
        }

        public void setLearned(Long learned)
        {
            this.learned = learned;
        }

        private User user;
        private Category category;
        private Long total;
        private Long learned;

        public Progress(User user, Category category, Long total, Long learned)
        {
            this.user = user;
            this.category = category;
            this.total = total;
            this.learned = learned;
        }
    }

    @ExceptionHandler(UserProfileException.class)
    public String handleUserProfileException(UserProfileException e, Model model)
    {
        model.addAttribute("info", e.getInfo());
        model.addAttribute("link", e.getLink());

        return "error";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model)
    {
        model.addAttribute("user", new UserRegisterForm());
        return "user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(UserRegisterForm form) throws UserProfileException
    {
        String username = form.getUsername();
        String email = form.getEmail();
        String password = form.getPassword();

        if(!UserInfoUtil.isUsernameValid(username))
            throw new UserProfileException("/user/register", "Username format incorrect!");
        if(!UserInfoUtil.isPasswordValid(password))
            throw new UserProfileException("/user/register", "Password format incorrect!");
        if(!UserInfoUtil.isEmailValid(email))
            throw new UserProfileException("/user/register", "Email format incorrect!");

        User user1 = userRepository.findUser(username);
        User user2 = userRepository.findUserByEmail(email);
        if(user1 != null || user2 != null)
            throw new UserProfileException("/user/register", "Username/Email has already been registered!");

        User user = new User(username, PasswordUtil.getMessageDigest(password), email);
        User newUser = userRepository.save(user);

        if(newUser == null)
            throw new UserProfileException("/user/register", "Internal Server Error! Register failed!");
        return "redirect:/user/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model)
    {
        UserLoginForm form = new UserLoginForm();
        model.addAttribute("loginForm", form);

        return "user/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(UserLoginForm form, HttpSession session) throws UserProfileException
    {
        String username = form.getUsername();
        String password = form.getPassword();

        User user = userRepository.findUser(username);
        if(user == null)
            throw new UserProfileException("/user/login", "This username doesn't exist!");

        if(!user.getPassword().equals(PasswordUtil.getMessageDigest(password)))
            throw new UserProfileException("/user/login", "Incorrect password!");
        // OK
        session.setAttribute(SecurityConfig.SESSION_KEY, user);
        session.setMaxInactiveInterval(3600 * 12);

        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session)
    {
        session.removeAttribute(SecurityConfig.SESSION_KEY);
        return "redirect:/";
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String modifyPasswordPage(Model model, HttpSession session)
    {
        ModifyPasswordForm form = new ModifyPasswordForm();
        User currentUser = (User)session.getAttribute(SecurityConfig.SESSION_KEY);
        form.setUsername(currentUser.getUsername());
        model.addAttribute("form", form);

        return "user/modify_password";
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String modifyPassword(ModifyPasswordForm form, HttpSession session) throws UserProfileException
    {
        if(form.getOriginPassword().equals(form.getNewPassword()))
            throw new UserProfileException("/user/password", "2 passwords cannot be the same!");

        if(!UserInfoUtil.isPasswordValid(form.getNewPassword()))
            throw new UserProfileException("/user/password", "New password format incorrect!");
        User user = (User)session.getAttribute(SecurityConfig.SESSION_KEY);
        if(!user.getPassword().equals(PasswordUtil.getMessageDigest(form.getOriginPassword())))
            throw new UserProfileException("/user/password", "Original password is not correct!");

        user.setPassword(PasswordUtil.getMessageDigest(form.getNewPassword()));
        userRepository.update(user);

        session.removeAttribute(SecurityConfig.SESSION_KEY);

        return "redirect:/";
    }

    @RequestMapping(value = "/modifyProfile", method = RequestMethod.GET)
    public String modifyProfilePage(Model model, HttpSession session) //now just change the email
    {
        User user = (User)session.getAttribute(SecurityConfig.SESSION_KEY);

        ModifyUserProfileForm form = new ModifyUserProfileForm();
        form.setUsername(user.getUsername());
        form.setEmail(user.getEmail());

        model.addAttribute("form", form);

        return "user/modify_profile";
    }

    @RequestMapping(value = "/modifyProfile", method = RequestMethod.POST)
    public String modifyProfile(ModifyUserProfileForm form, HttpSession session) throws UserProfileException
    {
        User user = (User)session.getAttribute(SecurityConfig.SESSION_KEY);
        if(!UserInfoUtil.isEmailValid(form.getEmail()))
            throw new UserProfileException("/user/modifyProfile", "Email format incorrect!");

        User userInDb = userRepository.findUserByEmail(form.getEmail());
        if(userInDb != null && !userInDb.getId().equals(user.getId()))
            throw new UserProfileException("/user/modifyProfile", "Email has been used by another user!");

        user.setEmail(form.getEmail());
        userRepository.update(user);
        session.setAttribute(SecurityConfig.SESSION_KEY, user);

        return "redirect:/";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profilePage(Model model, HttpSession session)
    {
        User user = (User)session.getAttribute(SecurityConfig.SESSION_KEY);
        model.addAttribute("user", user);

        List<Category> categoryList = categoryRepository.findAll();
        List<Progress> progressList = new ArrayList<>(4);
        //List<Long> totalList = new ArrayList<>(4);
        //List<Long> learnedList = new ArrayList<>(4);

        for(Category category : categoryList)
        {
            Long total = vocabularyRepository.count(category);
            Long learned = learnWordHistoryRepository.count(user, category);

            Progress progress = new Progress(user, category, total, learned);
            progressList.add(progress);
        }

        model.addAttribute("progressList", progressList);
        return "user/profile";
    }
}
