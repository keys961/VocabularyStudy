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
}
