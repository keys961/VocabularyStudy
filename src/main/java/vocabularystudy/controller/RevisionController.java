package vocabularystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vocabularystudy.service.RevisionService;

@Controller
@RequestMapping("/revision")
public class RevisionController
{
    @Autowired
    private RevisionService revisionService;

    @RequestMapping(value = "/revise", method = RequestMethod.GET)
    public String revisionPage()
    {
        return "revision/revision_page";
    }


}
