package vocabularystudy.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "LearnWordHistory")
public class LearnWordHistory
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "word")
    private Vocabulary word;

    @Column(name = "learn_time")
    private Date learnTime;

    public LearnWordHistory() {}

    public LearnWordHistory(User user, Category category, Vocabulary word, Date learnTime)
    {
        this.user = user;
        this.category = category;
        this.word = word;
        this.learnTime = learnTime;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public Vocabulary getWord()
    {
        return word;
    }

    public void setWord(Vocabulary word)
    {
        this.word = word;
    }

    public Date getLearnTime()
    {
        return learnTime;
    }

    public void setLearnTime(Date learnTime)
    {
        this.learnTime = learnTime;
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof LearnWordHistory)
            && (((LearnWordHistory) obj).id.equals(this.id));
    }
}
