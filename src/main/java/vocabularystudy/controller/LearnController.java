package vocabularystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vocabularystudy.config.SecurityConfig;
import vocabularystudy.model.*;
import vocabularystudy.service.LearnService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/learn")
public class LearnController
{
    @Autowired
    private LearnService learnService;

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public String learnPage()
    {
        return "learn/learn_page";
    }

    @RequestMapping(value = "/getLearnTask", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<LearnTaskItem>> getLearnTaskItem(HttpSession session)
    {
        User user = (User) session.getAttribute(SecurityConfig.SESSION_KEY);
        LearnPlan plan = learnService.getUserLearnPlan(user);
        LearnTask task = learnService.getTodayTaskExist(plan);
        boolean exist = task != null;

        if(!exist)
            task = learnService.generateLearnTask(plan);

        List<LearnTaskItem> learnTaskItemList = learnService.generateLearnTaskItem(task, exist);
        if(learnTaskItemList == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(learnTaskItemList);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST,
            produces = "application/json", consumes = "application/json; charset=utf-8")
    public ResponseEntity saveLearnStatus(HttpSession session, @RequestBody LearnTaskWrapper learnTaskWrapper)
    {
        List<LearnTaskItem> learnTaskItemList = learnTaskWrapper.learnTaskItemList;
        List<Long> learnedList = learnTaskWrapper.learnedList;
        List<LearnWordHistory> learnWordHistoryList = learnService.saveLearnStatus(learnTaskItemList, learnedList);
        if(learnTaskItemList.size() == 0 && learnedList.size() > 0)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return ResponseEntity.ok().build();
    }

    static class LearnTaskWrapper
    {
        public List<LearnTaskItem> learnTaskItemList;
        public List<Long> learnedList;
    }
}
