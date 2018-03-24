package vocabularystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vocabularystudy.config.SecurityConfig;
import vocabularystudy.model.User;
import vocabularystudy.model.Vocabulary;
import vocabularystudy.repository.UserCollectionRepository;
import vocabularystudy.repository.VocabularyRepository;

import javax.servlet.http.HttpSession;

@RequestMapping(value = "/vocabulary")
@Controller
public class VocabularyController
{
    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Autowired
    private UserCollectionRepository userCollectionRepository;

    @RequestMapping(value = "/{wordId}")
    public String vocabularyPage(HttpSession session, @PathVariable Long wordId, Model model)
    {
        User user = null;
        boolean isCollected = false;
        if(session.getAttribute(SecurityConfig.SESSION_KEY) != null)
            user = (User) session.getAttribute(SecurityConfig.SESSION_KEY);

        Vocabulary vocabulary = vocabularyRepository.find(wordId);
        vocabulary.setTranslation(vocabulary.getTranslation().replace("\n", "<br>"));

        if(user != null)
            isCollected = userCollectionRepository.exist(user, vocabulary);

        model.addAttribute("word", vocabulary);
        model.addAttribute("isCollected", isCollected);
        return "vocabulary/vocabulary_detail";
    }
}
