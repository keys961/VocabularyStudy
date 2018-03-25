package vocabularystudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vocabularystudy.model.Category;
import vocabularystudy.model.TestHistory;
import vocabularystudy.model.User;
import vocabularystudy.model.Vocabulary;
import vocabularystudy.repository.CategoryRepository;
import vocabularystudy.repository.TestHistoryRepository;
import vocabularystudy.repository.VocabularyRepository;

import java.util.*;

@Service
public class TestService
{
    private final VocabularyRepository vocabularyRepository;

    private final CategoryRepository categoryRepository;

    private final TestHistoryRepository testHistoryRepository;

    private final Map<Category, List<Long>> vocabularyIdMap;

    @Autowired
    public TestService(VocabularyRepository vocabularyRepository, CategoryRepository categoryRepository, TestHistoryRepository testHistoryRepository)
    {
        this.vocabularyRepository = vocabularyRepository;
        this.categoryRepository = categoryRepository;
        this.testHistoryRepository = testHistoryRepository;

        vocabularyIdMap = new HashMap<>();
        List<Category> categoryList = categoryRepository.findAll();

        for(Category category : categoryList)
            vocabularyIdMap.put(category, vocabularyRepository.getWordIdList(category));
    }

    public static class Problem
    {
        private String problem;

        private String[] options;// = new String[3];

        private int correctOption;

        public int getCorrectOption()
        {
            return correctOption;
        }

        public void setCorrectOption(int correctOption)
        {
            this.correctOption = correctOption;
        }

        public String getProblem()
        {
            return problem;
        }

        public void setProblem(String problem)
        {
            this.problem = problem;
        }

        public String[] getOptions()
        {
            return options;
        }

        public void setOptions(String[] options)
        {
            this.options = options;
        }

        public void setOptions(String option, int index)
        {
            this.options[index] = option;
        }
    }

    public static class Test
    {
        private Category category;

        private List<Problem> problemList;

        private List<Integer> answerList;

        private int totalScore;

        public int getTotalScore()
        {
            return totalScore;
        }

        public void setTotalScore(int totalScore)
        {
            this.totalScore = totalScore;
        }

        public List<Integer> getAnswerList()
        {
            return answerList;
        }

        public void setAnswerList(List<Integer> answerList)
        {
            this.answerList = answerList;
        }

        public Category getCategory()
        {
            return category;
        }

        public void setCategory(Category category)
        {
            this.category = category;
        }

        public List<Problem> getProblemList()
        {
            return problemList;
        }

        public void setProblemList(List<Problem> problemList)
        {
            this.problemList = problemList;
        }
    }

    /**
     * Entry that generate a test by category & amount
     */
    public Test generateTest(Long categoryId, Integer amount)
    {
        Random random = new Random(System.currentTimeMillis());
        Test test = new Test();

        Category category = categoryRepository.find(categoryId);
        if(category == null)
            return null;

        test.setCategory(category);
        test.setTotalScore(amount);
        test.setAnswerList(new ArrayList<>(amount));

        List<Problem> problemList = new ArrayList<>(amount);
        Set<Long> problemSet = new HashSet<>();
        while(problemList.size() < amount)
        {
            Long id = (long) random.nextInt(vocabularyIdMap.get(category).size());
            Long anotherId = 0L;
            if(problemSet.contains(id))
                continue;

            problemSet.add(id);
            Problem problem = new Problem();
            Vocabulary word = vocabularyRepository.find(vocabularyIdMap.get(category).get(id.intValue()));
            problem.setProblem(word.getWord());

            String[] options = new String[3];
            String correctAnswer = word.getTranslation();
            options[0] = correctAnswer;
            while((anotherId = (long) random.nextInt(vocabularyIdMap.get(category).size())).equals(id));
            options[1] = vocabularyRepository.find(vocabularyIdMap.get(category).get(anotherId.intValue())).getTranslation();
            while((anotherId = (long) random.nextInt(vocabularyIdMap.get(category).size())).equals(id));
            options[2] = vocabularyRepository.find(vocabularyIdMap.get(category).get(anotherId.intValue())).getTranslation();
            Arrays.sort(options);
            problem.setOptions(options);

            for(int i = 0; i < 3; i++)
            {
                if(options[i].equals(correctAnswer))
                {
                    problem.setCorrectOption(i);
                    //break;
                }

                if(options[i].contains("\n"))
                    options[i] = options[i].substring(0, options[i].indexOf("\n"));
            }

            problemList.add(problem);
        }

        test.setProblemList(problemList);

        return test;
    }

    public TestHistory saveTestResult(User user, Test test)
    {
        TestHistory history = new TestHistory();
        history.setCategory(test.getCategory());
        history.setTotalScore(test.getTotalScore());
        history.setUser(user);
        history.setScore(getTestResult(test));

        return testHistoryRepository.save(history);
    }

    public List<Category> showAllTestCategory()
    {
        return categoryRepository.findAll();
    }

    public List<TestHistory> showAllTestHistory(User user)
    {
        return testHistoryRepository.getUserTestHistory(user);
    }

    public List<TestHistory> showAllTestHistory(User user, Long offset, Long count)
    {
        return testHistoryRepository.getUserTestHistory(user, offset, count);
    }

    /**
     * Get the score of a test.
     * @param test: The test that has been filled with answers that the user produced.
     * @return result: The test score
     */
    private int getTestResult(Test test)
    {
        int result = 0;

        List<Integer> answerList = test.getAnswerList();
        List<Problem> problemList = test.getProblemList();

        for(int i = 0; i < answerList.size(); i++)
        {
            if((problemList.get(i).getCorrectOption()) == answerList.get(i))
                result++;
        }

        return result;
    }
}
