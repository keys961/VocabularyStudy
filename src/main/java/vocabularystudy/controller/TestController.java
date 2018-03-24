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
import vocabularystudy.model.Category;
import vocabularystudy.model.TestHistory;
import vocabularystudy.model.User;
import vocabularystudy.repository.TestHistoryRepository;
import vocabularystudy.repository.VocabularyRepository;
import vocabularystudy.service.TestService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController
{
    @Autowired
    private TestService testService;

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
        TestService.Test test = testService.generateTest(categoryId, 20);
        model.addAttribute("test", test);

        return "test/test_page";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitTest(Model model, HttpSession session, TestService.Test test)
    {
        User user = (User)session.getAttribute(SecurityConfig.SESSION_KEY);
        TestHistory history = testService.saveTestResult(user, test);

        model.addAttribute("link", "/test/main");
        model.addAttribute("info", "您的成绩为: " + history.getScore() + "/" + history.getTotalScore());

        return "error";
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
