package vocabularystudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vocabularystudy.model.LearnTask;
import vocabularystudy.repository.LearnTaskItemRepository;
import vocabularystudy.repository.LearnTaskRepository;
import vocabularystudy.repository.LearnWordHistoryRepository;
import vocabularystudy.repository.VocabularyRepository;

@Service
public class RevisionService
{
    @Autowired
    private LearnTaskRepository learnTaskRepository;

    @Autowired
    private LearnTaskItemRepository learnTaskItemRepository;

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Autowired
    private LearnWordHistoryRepository learnWordHistoryRepository;

    public LearnTask generateRevisionTask()
    {

        return null;
    }

}
