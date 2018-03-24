package vocabularystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vocabularystudy.config.SecurityConfig;
import vocabularystudy.model.*;
import vocabularystudy.repository.*;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping(value = "/plan")
public class LearnPlanController
{
    @Autowired
    private LearnPlanRepository learnPlanRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Autowired
    private LearnWordHistoryRepository learnWordHistoryRepository;

    @Autowired
    private LearnCategoryHistoryRepository learnCategoryHistoryRepository;

    @RequestMapping(value = "/learnPlanPage", method = RequestMethod.GET)
    public String learnPlanPage(Model model, HttpSession session)
    {
        User user = (User) session.getAttribute(SecurityConfig.SESSION_KEY);
        LearnPlan plan = learnPlanRepository.getLearnPlan(user);
        List<Category> categoryList = null;
        List<Long> totalAmountList = null;
        Long total = null;
        Long learned = null;

        if(plan == null)
        {
            categoryList = categoryRepository.findAll();
            totalAmountList = new LinkedList<>();

            for(Category category : categoryList)
                totalAmountList.add(vocabularyRepository.count(category));
        }
        else
        {
            total = vocabularyRepository.count(plan.getCategory());
            learned = learnWordHistoryRepository.count(plan.getUser(), plan.getCategory());
        }

        model.addAttribute("learnPlan", plan);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("totalAmountList", totalAmountList);
        model.addAttribute("total", total);
        model.addAttribute("learned", learned);

        return "plan/plan";
    }

    @RequestMapping(value = "/addPlan/", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity addPlan(Long categoryId, String date, HttpSession session)
    {
        User user = (User) session.getAttribute(SecurityConfig.SESSION_KEY);
        Category category = categoryRepository.find(categoryId);
        if(category == null)
            return ResponseEntity.notFound().build();

        if(learnPlanRepository.exist(user, category))
            return ResponseEntity.ok().build();

        if(learnCategoryHistoryRepository.exist(user, category))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        Date endTime = Date.valueOf(date);
        Date startTime = new Date(System.currentTimeMillis());
        LearnPlan plan = new LearnPlan();
        plan.setUser(user);
        plan.setCategory(category);
        plan.setStartTime(startTime);
        plan.setEndTime(endTime);

        plan = learnPlanRepository.save(plan);

        if(plan == null)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/removePlan", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity removePlan(Long categoryId, HttpSession session)
    {
        User user = (User) session.getAttribute(SecurityConfig.SESSION_KEY);
        LearnPlan plan = learnPlanRepository.getLearnPlan(user);
        if(plan == null)
            return ResponseEntity.notFound().build();
        learnPlanRepository.delete(plan);
        List<LearnWordHistory> histories = learnWordHistoryRepository.getLatestHistoryList(user, plan.getCategory());
        for(LearnWordHistory history : histories)
            learnWordHistoryRepository.delete(history);
        return ResponseEntity.ok().build();
    }
}
