package vocabularystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vocabularystudy.config.SecurityConfig;
import vocabularystudy.model.Category;
import vocabularystudy.model.LearnPlan;
import vocabularystudy.model.User;
import vocabularystudy.repository.LearnPlanRepository;
import vocabularystudy.repository.LearnWordHistoryRepository;
import vocabularystudy.repository.VocabularyRepository;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping(value = "/")
public class MainController
{
    private final LearnPlanRepository learnPlanRepository;

    private final LearnWordHistoryRepository learnWordHistoryRepository;

    private final VocabularyRepository vocabularyRepository;

    private final ThreadLocal<Date> dateThreadLocal = ThreadLocal.withInitial(() -> new Date());

    @Autowired
    public MainController(LearnPlanRepository learnPlanRepository, LearnWordHistoryRepository learnWordHistoryRepository, VocabularyRepository vocabularyRepository)
    {
        this.learnPlanRepository = learnPlanRepository;
        this.learnWordHistoryRepository = learnWordHistoryRepository;
        this.vocabularyRepository = vocabularyRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model, HttpSession session)
    {
        User user = (User) session.getAttribute(SecurityConfig.SESSION_KEY);
        LearnPlan plan = null;
        Long total = null, learned = null, need = null;
        if(user != null)
        {
           plan = learnPlanRepository.getLearnPlan(user);
           if(plan != null)
           {
               total = vocabularyRepository.count(plan.getCategory());
               learned = learnWordHistoryRepository.count(plan);
               need = getTodayLearnCount(user, plan.getCategory());
           }
        }

        model.addAttribute("plan", plan);
        model.addAttribute("total", total);
        model.addAttribute("learned", learned);
        model.addAttribute("needLearn", need);
        return "home";
    }

    private Long getTodayLearnCount(User user, Category category)
    {
        //TODO
        return 0L;
    }
}
