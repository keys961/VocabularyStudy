package vocabularystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vocabularystudy.config.SecurityConfig;
import vocabularystudy.model.LearnPlan;
import vocabularystudy.model.LearnWordHistory;
import vocabularystudy.model.User;
import vocabularystudy.service.RevisionService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/revision")
public class RevisionController
{
    @Autowired
    private RevisionService revisionService;

    @RequestMapping(value = "/recent/", method = RequestMethod.GET)
    public String revisionRecentPage()
    {
        return "revision/revision_recent_page";
    }

    @RequestMapping(value = "/all/", method = RequestMethod.GET)
    public String revisionAllPage()
    {
        return "revision/revision_all_page";
    }

    @RequestMapping(value = "/all/get/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<LearnWordHistory>> getAllRevisionTask(HttpSession session)
    {
        User user = (User) session.getAttribute(SecurityConfig.SESSION_KEY);
        LearnPlan plan = revisionService.getLearnPlan(user);
        if(plan == null)
            return ResponseEntity.notFound().build();

        List<LearnWordHistory> revisionTaskList = revisionService.generateRevisionTaskItemList(plan);
        return ResponseEntity.ok(revisionTaskList);
    }

    @RequestMapping(value = "/recent/get/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<LearnWordHistory>> getRecentRevisionTask(HttpSession session)
    {
        User user = (User) session.getAttribute(SecurityConfig.SESSION_KEY);
        LearnPlan plan = revisionService.getLearnPlan(user);
        if(plan == null)
            return ResponseEntity.notFound().build();

        List<LearnWordHistory> revisionTaskList = revisionService.generateAllRevisionTaskItemList(plan);
        return ResponseEntity.ok(revisionTaskList);
    }

    @RequestMapping(value = "/save/", method = RequestMethod.POST,
            consumes = "application/json; charset=utf-8", produces = "application/json")
    public ResponseEntity saveRevisionStatus(HttpSession session, @RequestBody List<LearnWordHistory> notKnowList)
    {
        revisionService.saveRevisionStatus(notKnowList);
        return ResponseEntity.ok().build();
    }

}
