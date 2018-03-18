package repository;

import static org.junit.Assert.*;
import vocabularystudy.config.RootConfig;
import vocabularystudy.model.Category;
import vocabularystudy.model.User;
import vocabularystudy.repository.CategoryRepository;
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

    @Test
    public void countTest()
    {
        List<Category> categoryList = categoryRepository.findAll();

        for(Category category : categoryList)
            System.out.println(category.getCategory() + ": " +vocabularyRepository.count(category));
    }
}
