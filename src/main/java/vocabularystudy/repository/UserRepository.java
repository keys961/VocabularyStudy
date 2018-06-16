package vocabularystudy.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocabularystudy.model.User;

import java.util.List;

@Repository
@Transactional
public class UserRepository
{
    private SessionFactory sessionFactory;

    @Autowired
    public UserRepository(SessionFactory factory)
    {
        this.sessionFactory = factory;
    }

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    //@Cacheable(value = "userCache")
    public User findUser(Long id)
    {
        return (User) getCurrentSession().get(User.class, id);
    }

    //@Cacheable(value = "userCache")
    @SuppressWarnings("unchecked")
    public User findUser(String username)
    {
        List<User> userList = (List<User>)getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("username", username))
                .list();

        if(userList.size() == 0)
            return null;
        return userList.get(0);
    }

    //@Cacheable(value = "userCache")
    @SuppressWarnings("unchecked")
    public User findUserByEmail(String email)
    {
        List<User> userList = (List<User>)getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .list();

        if(userList.size() == 0)
            return null;
        return userList.get(0);
    }

    @CachePut(value = "userCache", key = "#result.username", condition = "#root.target != null",
            unless = "#result == null")
    public User save(User user)
    {
        Session session = getCurrentSession();
        // session.beginTransaction().begin();
        Long id = (Long)session.save(user);

        if(id == null)
            return null;
        // session.getTransaction().commit();
        //user.setId(id);
        User u = new User(user.getUsername(), user.getPassword(), user.getEmail());
        u.setId(id);
        return u;
    }

    @CachePut(value = "userCache", key = "#result.username", condition = "#root.target != null",
            unless = "#result == null")
    public User update(User user)
    {
        Session session = getCurrentSession();
        // Transaction transaction = session.beginTransaction();
        session.update(user);
        session.flush();
        //  transaction.commit();
        User u = new User(user.getUsername(), user.getPassword(), user.getEmail());
        u.setId(user.getId());
        return u;
    }

    @CacheEvict(value = "userCache")
    public void delete(User user)
    {
        Session session = getCurrentSession();
        //  Transaction transaction = session.beginTransaction();
        session.delete(user);
        session.flush();
        //  transaction.commit();
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll()
    {
        return (List<User>)
                getCurrentSession().createCriteria(User.class).list();
    }

    public boolean exist(Long id)
    {
        return getCurrentSession().get(User.class, id) != null;
    }

    public boolean exist(User user)
    {
        if(user.getId() == 0)
            return findUser(user.getUsername()) != null;
        return exist(user.getId());
    }
}
