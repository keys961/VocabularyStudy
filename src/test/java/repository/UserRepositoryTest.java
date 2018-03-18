package repository;

import static org.junit.Assert.*;
import vocabularystudy.config.RootConfig;
import vocabularystudy.model.User;
import vocabularystudy.repository.UserRepository;
import vocabularystudy.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class UserRepositoryTest
{
    @Autowired
    private UserRepository repository;

    @Test
    public void addUser()
    {
        User user = new User();
        user.setUsername("keys961");
        user.setPassword(PasswordUtil.getMessageDigest("123456"));
        user.setEmail("test@test.com");

        User user1 = repository.save(user);
        assertNotNull(user1);
    }

    @Test
    public void updateUser()
    {
        User user = repository.findUser("keys961");
        assertNotNull(user);

        user.setPassword(PasswordUtil.getMessageDigest("654321"));
        repository.update(user);

        assertEquals(repository.findUser("keys961").getPassword(), PasswordUtil.getMessageDigest("654321"));
    }

//    @Test
//    public void updateUser()
//    {
//        User user = repository.findUser(2);
//        user.setPassword("shitPassword");
//        repository.update(user);
//
//        user = repository.findUser(2);
//        assertEquals("shitPassword", user.getPassword());
//    }
//
//    @Test
//    public void queryUser()
//    {
//        int id = 1;
//        String name = "keys961";
//
//        User user1 = repository.findUser(id);
//        User user2 = repository.findUser(name);
//
//        boolean exist1 = repository.exist(id);
//        boolean exist2 = repository.exist(user1);
//
//        assertNotNull(user1);
//        assertNotNull(user2);
//        assertEquals(id, user1.getId());
//        assertEquals(id, user2.getId());
//        assertTrue(exist1);
//        assertTrue(exist2);
//    }
//
    @Test
    public void queryUserList()
    {
        List<User> list = repository.findAll();

        assertEquals(list.size(), 1);
    }
//
    @Test
    public void deleteUser()
    {
        User user = repository.findUser("keys961");

        //repository.delete(user);
        assertNull(repository.findUser("keys961"));
    }
}