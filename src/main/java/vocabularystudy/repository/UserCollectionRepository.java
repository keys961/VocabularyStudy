package vocabularystudy.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocabularystudy.model.User;
import vocabularystudy.model.UserCollection;
import vocabularystudy.model.Vocabulary;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class UserCollectionRepository
{
    private final SessionFactory sessionFactory;

    @Autowired
    public UserCollectionRepository(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    public UserCollection save(UserCollection collection)
    {
        Session session = getCurrentSession();
        Long id = (Long) session.save(collection);

        if(id == null)
            return null;

        collection.setId(id);
        return collection;
    }

    @SuppressWarnings("unchecked")
    public UserCollection find(User user, Vocabulary vocabulary)
    {
        Session session = getCurrentSession();
        List<UserCollection> list = session.createCriteria(UserCollection.class)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("word", vocabulary))
                .list();

        if(list == null || list.size() == 0)
            return null;
        return list.get(0);
    }

    public void delete(UserCollection collection)
    {
        getCurrentSession().delete(collection);
        getCurrentSession().flush();
    }

    public void deleteUserCollectionList(List<UserCollection> list)
    {
        Session session = getCurrentSession();
        for(UserCollection collection : list)
            session.delete(collection);

        session.flush();
    }

    @SuppressWarnings("unchecked")
    public List<UserCollection> getUserCollectionList(User user, Long offset, Long count)
    {
        Session session = getCurrentSession();
        List<UserCollection> collections = session.createCriteria(UserCollection.class)
                .add(Restrictions.eq("user", user))
                .setFirstResult(offset.intValue())
                .setMaxResults(count.intValue())
                .list();

        if(collections == null)
            return new ArrayList<>(1);

        return collections;
    }

    public boolean exist(UserCollection collection)
    {
        return getCurrentSession().get(UserCollection.class, collection.getId()) != null;
    }

    public boolean exist(Long userCollectionId)
    {
        return getCurrentSession().get(UserCollection.class, userCollectionId) != null;
    }

    public boolean exist(User user, Vocabulary vocabulary)
    {
        return getCurrentSession().createCriteria(UserCollection.class)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("word", vocabulary))
                .list().size() > 0;
    }

}
