package repository;

import static org.junit.Assert.*;

import org.junit.Ignore;
import vocabularystudy.config.RootConfig;
import vocabularystudy.model.Category;
import vocabularystudy.model.User;
import vocabularystudy.model.Vocabulary;
import vocabularystudy.repository.CategoryRepository;
import vocabularystudy.repository.LearnWordHistoryRepository;
import vocabularystudy.repository.UserRepository;
import vocabularystudy.repository.VocabularyRepository;
import vocabularystudy.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class VocabularyRepositoryTest
{
    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LearnWordHistoryRepository learnWordHistoryRepository;

    @Test
    @Ignore
    public void countTest()
    {
        List<Category> categoryList = categoryRepository.findAll();

        for(Category category : categoryList)
            System.out.println(category.getCategory() + ": " +vocabularyRepository.count(category));
    }

    @Test
    @Ignore
    public void vocabularyTest()
    {
        User user = new User();
        user.setId(4L);
        Category category = new Category();
        category.setId(2L);
        System.out.println(vocabularyRepository.getWordList(category, 0L, 1L).get(0).getWord());
        List<Vocabulary> list = vocabularyRepository.getWordListNotLearned(user, category, 3L);

        for (Vocabulary vocabulary : list)
            System.out.println(vocabulary.getWord());
    }

    @Test
    @Ignore
    public void idSetTest()
    {
        List longList = vocabularyRepository.getWordIdList(categoryRepository.find(1L));

        assertNotNull(longList);
    }

    @Test
    public void historyTest()
    {
        User user = new User();
        user.setId(4L);

        System.out.println(learnWordHistoryRepository.count(user, categoryRepository.find(2L)));
    }
}
