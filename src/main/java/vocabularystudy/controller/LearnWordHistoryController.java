package vocabularystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vocabularystudy.config.SecurityConfig;
import vocabularystudy.model.LearnWordHistory;
import vocabularystudy.model.User;
import vocabularystudy.model.Vocabulary;
import vocabularystudy.repository.LearnWordHistoryRepository;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/learnedHistory")
@Controller
public class LearnWordHistoryController
{
    @Autowired
    private LearnWordHistoryRepository learnWordHistoryRepository;

    @RequestMapping(value = "/total/{offset}", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<List<LearnWordHistory>> getLearnedWordHistory(@PathVariable Long offset, HttpSession session)
    {
        final Long COUNT = 30L;
        User user = (User) session.getAttribute(SecurityConfig.SESSION_KEY);
        List<LearnWordHistory> list = learnWordHistoryRepository.getLatestHistoryList(user, offset, COUNT);

        if(list == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(list, HttpStatus.ACCEPTED);
    }
}
