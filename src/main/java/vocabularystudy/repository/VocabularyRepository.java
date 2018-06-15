package vocabularystudy.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vocabularystudy.model.Category;
import vocabularystudy.model.LearnWordHistory;
import vocabularystudy.model.User;
import vocabularystudy.model.Vocabulary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Repository
public class VocabularyRepository
{
    private final SessionFactory sessionFactory;

    @Autowired
    public VocabularyRepository(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Cacheable("wordCache")
    public Vocabulary find(Long id)
    {
        return (Vocabulary) getCurrentSession().get(Vocabulary.class, id);
    }

    @SuppressWarnings("unchecked")
    @Cacheable("wordCache")
    public Vocabulary find(String word)
    {
        Session session = getCurrentSession();
        List<Vocabulary> vocabularyList = session.createCriteria(Vocabulary.class)
                .add(Restrictions.eq("word", word))
                .setMaxResults(1)
                .list();

        if(vocabularyList == null || vocabularyList.size() == 0)
            return null;

        return vocabularyList.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Vocabulary> getWordList(Category category, Long offset, Long count)
    {
        Session session = getCurrentSession();

        return (List<Vocabulary>) session.createCriteria(Vocabulary.class)
                .add(Restrictions.eq("category", category))
                .setFirstResult(offset.intValue())
                .setMaxResults(count.intValue())
                .list();
    }

    @SuppressWarnings("unchecked")
    public List<Vocabulary> getWordList(Long offset, Long count)
    {
        Session session = getCurrentSession();

        return (List<Vocabulary>) session.createCriteria(Vocabulary.class)
                .setFirstResult(offset.intValue())
                .setMaxResults(count.intValue())
                .list();
    }

    @SuppressWarnings("unchecked")
    public List<Long> getWordIdList(Category category, Long offset, Long count)
    {
        Session session = getCurrentSession();

        List<Long> list = session.createCriteria(Vocabulary.class)
                .add(Restrictions.eq("category", category))
                .setProjection(Projections.property("id"))
                .setFirstResult(offset.intValue())
                .setMaxResults(count.intValue())
                .list();

        if(list == null)
            return new ArrayList<>(1);
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<Vocabulary> getWordListNotLearned(User user, Category category, Long count)
    {
        Session session = getCurrentSession();
        List<Vocabulary> learnedWordList = session.createCriteria(LearnWordHistory.class)
                .add(Restrictions.eq("category", category))
                .add(Restrictions.eq("user", user))
                .setProjection(Projections.property("word"))
                .list();

        Set<Long> learnedSet = new HashSet<>();
        for(Vocabulary vocabulary : learnedWordList)
            learnedSet.add(vocabulary.getId());

        if(learnedWordList.isEmpty())
            return session.createCriteria(Vocabulary.class)
                    .add(Restrictions.eq("category", category))
                    .setMaxResults(count.intValue())
                    .list();

        return session.createCriteria(Vocabulary.class)
                .add(Restrictions.eq("category", category))
                .add(Restrictions.not(Restrictions.in("id", learnedSet)))
                .setMaxResults(count.intValue())
                .list();
    }


    @SuppressWarnings("unchecked")
    public List<Long> getWordIdList(Category category)
    {
        Session session = getCurrentSession();

        return  session.createCriteria(Vocabulary.class)
                .add(Restrictions.eq("category", category))
                .setProjection(Projections.property("id"))
                .list();
    }

    @Cacheable("wordCache")
    public Long count()
    {
        Session session = getCurrentSession();
        return (Long)session.createCriteria(Vocabulary.class)
                .setProjection(Projections.count("id"))
                .list().get(0);
    }

    @Cacheable("wordCache")
    public Long count(Category category)
    {
        Session session = getCurrentSession();
        return (Long)session.createCriteria(Vocabulary.class)
                .add(Restrictions.eq("category", category))
                .setProjection(Projections.count("id"))
                .list().get(0);
    }
}
