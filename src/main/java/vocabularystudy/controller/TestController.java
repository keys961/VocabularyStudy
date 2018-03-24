package vocabularystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import vocabularystudy.repository.VocabularyRepository;

@Controller
@RequestMapping("/test")
public class TestController
{
    @Autowired
    private VocabularyRepository vocabularyRepository;

    
}
