package vocabularystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import vocabularystudy.service.RevisionService;

@Controller
@RequestMapping("/revision")
public class RevisionController
{
    @Autowired
    private RevisionService revisionService;


}
