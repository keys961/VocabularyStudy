package vocabularystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vocabularystudy.config.SecurityConfig;
import vocabularystudy.model.Category;
import vocabularystudy.model.TestHistory;
import vocabularystudy.model.User;
import vocabularystudy.repository.TestHistoryRepository;
import vocabularystudy.repository.VocabularyRepository;
import vocabularystudy.service.TestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController
{
    @Autowired
    private TestService testService;

    private static class Result
    {
        int totalScore;
        int score;

        public int getTotalScore()
        {
            return totalScore;
        }

        public void setTotalScore(int totalScore)
        {
            this.totalScore = totalScore;
        }

        public int getScore()
        {
            return score;
        }

        public void setScore(int score)
        {
            this.score = score;
        }
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String testMainPage(Model model)
    {
        List<Category> categoryList = testService.showAllTestCategory();
        model.addAttribute("categoryList", categoryList);
        return "test/main_page";
    }

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    public String testPage(Model model, @PathVariable Long categoryId)
    {
        model.addAttribute("categoryId", categoryId);

        return "test/test_page";
    }

    @RequestMapping(value = "/generate/{categoryId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<TestService.Test> generateTest(@PathVariable Long categoryId)
    {
        TestService.Test test = testService.generateTest(categoryId, 20);
        if(test == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(test, HttpStatus.ACCEPTED);
    }


    @RequestMapping(value = "/submit", method = RequestMethod.POST, produces = "application/json", consumes = "application/json; charset=utf-8")
    public ResponseEntity<Result> submitTest(HttpSession session, @RequestBody TestService.Test test)
    {
        User user = (User)session.getAttribute(SecurityConfig.SESSION_KEY);
        TestHistory history = testService.saveTestResult(user, test);
        if(history == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        Result result = new Result();
        result.setTotalScore(history.getTotalScore());
        result.setScore(history.getScore());

        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public String testHistoryPage()
    {
        return "test/test_history";
    }

    @RequestMapping(value = "/history/{offset}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<TestHistory>> getTestHistory(HttpSession session, @PathVariable Long offset)
    {
        User user = (User)session.getAttribute(SecurityConfig.SESSION_KEY);
        List<TestHistory> testHistoryList = testService.showAllTestHistory(user, offset, 20L);

        if(testHistoryList == null || testHistoryList.size() == 0)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(testHistoryList, HttpStatus.ACCEPTED);
    }

}
