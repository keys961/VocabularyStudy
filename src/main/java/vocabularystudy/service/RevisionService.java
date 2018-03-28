package vocabularystudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vocabularystudy.model.*;
import vocabularystudy.repository.*;
import vocabularystudy.util.LearnTaskUtil;

import java.util.List;

@Service
public class RevisionService
{
    @Autowired
    private LearnPlanRepository learnPlanRepository;

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Autowired
    private LearnWordHistoryRepository learnWordHistoryRepository;

    public LearnPlan getLearnPlan(User user)
    {
        return learnPlanRepository.getLearnPlan(user);
    }

    public List<LearnWordHistory> generateRevisionTaskItemList(LearnPlan plan)
    {
        return learnWordHistoryRepository.getLatestHistoryList(plan.getUser(), plan.getCategory(), getTaskItemNumber(plan));
    }

    public List<LearnWordHistory> generateAllRevisionTaskItemList(LearnPlan plan)
    {
        return learnWordHistoryRepository.getLatestHistoryList(plan.getUser(), plan.getCategory());
    }

    public void saveRevisionStatus(List<LearnWordHistory> revisionList, List<Long> notKnowList)
    {
        for(Long id : notKnowList)
            learnWordHistoryRepository.delete(revisionList.get(id.intValue()));
    }

    private Long getTaskItemNumber(LearnPlan plan)
    {
        Long totalCount = vocabularyRepository.count(plan.getCategory());
        Long learnedCount = learnWordHistoryRepository.count(plan);

        return LearnTaskUtil.getTodayTaskAmount(plan.getEndTime(), totalCount - learnedCount);
    }

}
