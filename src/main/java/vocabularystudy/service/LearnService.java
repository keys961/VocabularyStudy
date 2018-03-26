package vocabularystudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vocabularystudy.model.*;
import vocabularystudy.repository.*;
import vocabularystudy.util.LearnTaskUtil;

import java.sql.Date;
import java.time.LocalDate;
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

    public LearnPlan getUserLearnPlan(User user) // First step..
    {
        return learnPlanRepository.getLearnPlan(user);
    }

    public LearnTask generateLearnTask(LearnPlan plan) // Second step..
    {
        Date currentDate = Date.valueOf(LocalDate.now());
        LearnTask task = null;

        if((task = learnTaskRepository.find(plan.getUser(), plan.getCategory(), currentDate)) != null)
            return task;

        task = new LearnTask();
        task.setUser(plan.getUser());
        task.setLearnPlan(plan);
        task.setCategory(plan.getCategory());
        task.setLearnTime(currentDate);

        task = learnTaskRepository.save(task);

        return task;
    }

    public List<LearnTaskItem> generateLearnTaskItem(LearnTask task) // Final step
    {
        Long amount = getTaskItemNumber(task.getLearnPlan());
        List<Vocabulary> wordNotLearnedList = vocabularyRepository.getWordListNotLearned(task.getUser(), task.getCategory(), amount);

        // TODO: Generate learn task item...

        return null;
    }

    private Long getTaskItemNumber(LearnPlan plan)
    {
        Long totalCount = vocabularyRepository.count(plan.getCategory());
        Long learnedCount = learnWordHistoryRepository.count(plan);

        return LearnTaskUtil.getTodayTaskAmount(plan.getEndTime(), totalCount - learnedCount);
    }

}
