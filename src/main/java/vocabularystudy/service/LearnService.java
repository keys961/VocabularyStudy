package vocabularystudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vocabularystudy.model.*;
import vocabularystudy.repository.*;

import java.util.List;

@Service
public class LearnService
{
    @Autowired
    private LearnPlanRepository learnPlanRepository;

    @Autowired
    private LearnTaskRepository learnTaskRepository;

    @Autowired
    private LearnTaskItemRepository learnTaskItemRepository;

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Autowired
    private LearnWordHistoryRepository learnWordHistoryRepository;

    public LearnTask generateLearnTask(LearnPlan plan)
    {

        return null;
    }

    public List<LearnTaskItem> generateLearnTaskItem(LearnTask task)
    {

        return null;
    }

}
