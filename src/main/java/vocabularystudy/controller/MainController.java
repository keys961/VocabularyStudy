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
import vocabularystudy.model.LearnTask;
import vocabularystudy.model.User;
import vocabularystudy.repository.LearnPlanRepository;
import vocabularystudy.repository.LearnWordHistoryRepository;
import vocabularystudy.repository.VocabularyRepository;
import vocabularystudy.util.LearnTaskUtil;

import javax.servlet.http.HttpSession;
import java.sql.Date;

@Controller
@RequestMapping(value = "/")
public class MainController
{
    private final LearnPlanRepository learnPlanRepository;

    private final LearnWordHistoryRepository learnWordHistoryRepository;

    private final VocabularyRepository vocabularyRepository;

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
        LearnPlan plan = learnPlanRepository.getLearnPlan(user);
        if(plan == null)
            return 0L;

        Long totalAmount = vocabularyRepository.count(category);
        Long learnedAmount = learnWordHistoryRepository.count(user, category);
        Date endDate = plan.getEndTime();
        return LearnTaskUtil.getTodayTaskAmount(endDate, totalAmount - learnedAmount);
    }
}
