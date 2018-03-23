package vocabularystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vocabularystudy.model.Vocabulary;
import vocabularystudy.repository.VocabularyRepository;

import javax.servlet.http.HttpSession;

@RequestMapping(value = "/vocabulary")
@Controller
public class VocabularyController
{
    @Autowired
    private VocabularyRepository vocabularyRepository;

    @RequestMapping(value = "/{wordId}")
    public String vocabularyPage(HttpSession session, @PathVariable Long wordId, Model model)
    {
        Vocabulary vocabulary = vocabularyRepository.find(wordId);

        vocabulary.setTranslation(vocabulary.getTranslation().replace("\n", "<br>"));

        model.addAttribute("word", vocabulary);

        return "vocabulary/vocabulary_detail";
    }
}
