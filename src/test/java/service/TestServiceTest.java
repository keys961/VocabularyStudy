package service;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Ignore;
import vocabularystudy.config.RootConfig;
import vocabularystudy.model.Category;
import vocabularystudy.model.User;
import vocabularystudy.repository.CategoryRepository;
import vocabularystudy.repository.UserRepository;
import vocabularystudy.repository.VocabularyRepository;
import vocabularystudy.service.TestService;
import vocabularystudy.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class TestServiceTest
{
    @Autowired
    private TestService service;

    @Test
    @Ignore
    public void test()
    {
        TestService.Test test = service.generateTest(1L, 30);
        assertNotNull(test);
    }
}
