package vocabularystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import vocabularystudy.service.LearnService;

@Controller
@RequestMapping("/learn")
public class LearnController
{
    @Autowired
    private LearnService learnService;


}
