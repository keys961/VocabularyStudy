package vocabularystudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vocabularystudy.model.*;
import vocabularystudy.repository.*;
import vocabularystudy.util.LearnTaskUtil;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public LearnTask getTodayTaskExist(LearnPlan plan) // Second, check existence
    {
        return learnTaskRepository.find(plan.getUser(), plan.getCategory(), Date.valueOf(LocalDate.now()));
    }

    public LearnTask generateLearnTask(LearnPlan plan) // Third, if not exist step..
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

    public List<LearnTaskItem> generateLearnTaskItem(LearnTask task, boolean exist) // Final step, get learn task item
    {
        // First find existed learn task item..
        if(exist)
            return learnTaskItemRepository.getTodayLearnTaskItemList(task);

        // If not found..
        Long amount = getTaskItemNumber(task.getLearnPlan());
        List<Vocabulary> wordNotLearnedList = vocabularyRepository.getWordListNotLearned(task.getUser(), task.getCategory(), amount);

        List<LearnTaskItem> learnTaskItemList = new ArrayList<>(amount.intValue());

        for(Vocabulary word : wordNotLearnedList)
        {
            //word.setTranslation(word.getTranslation().replace("\n", "<br>"));
            LearnTaskItem taskItem = new LearnTaskItem(task, word);
            learnTaskItemList.add(taskItem);
        }

        learnTaskItemList = learnTaskItemRepository.saveList(learnTaskItemList);

        return learnTaskItemList;
    }

    public List<LearnWordHistory> saveLearnStatus(List<LearnTaskItem> taskItemList, List<Long> learnedList)
    {
        List<LearnWordHistory> learnWordHistoryList = new ArrayList<>(learnedList.size());

        for(Long index : learnedList)
        {
            LearnWordHistory history = new LearnWordHistory();
            history.setUser(taskItemList.get(index.intValue()).getLearnTask().getUser());
            history.setCategory(taskItemList.get(index.intValue()).getWord().getCategory());
            history.setWord(taskItemList.get(index.intValue()).getWord());
            history.setLearnTime(Date.valueOf(LocalDate.now()));
            history = learnWordHistoryRepository.save(history);
            if(history.getId() != null)
                learnWordHistoryList.add(history);
        }

        return learnWordHistoryList;
    }

    private Long getTaskItemNumber(LearnPlan plan)
    {
        Long totalCount = vocabularyRepository.count(plan.getCategory());
        Long learnedCount = learnWordHistoryRepository.count(plan);

        return LearnTaskUtil.getTodayTaskAmount(plan.getEndTime(), totalCount - learnedCount);
    }

}
