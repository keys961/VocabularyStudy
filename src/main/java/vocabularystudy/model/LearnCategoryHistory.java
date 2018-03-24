package vocabularystudy.model;

import javax.persistence.*;
import java.sql.Date;

public class LearnCategoryHistory
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;

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

    public Date getLearnTime()
    {
        return learnTime;
    }

    public void setLearnTime(Date learnTime)
    {
        this.learnTime = learnTime;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "learn_time")
    private Date learnTime;

    public LearnCategoryHistory() {}

    public LearnCategoryHistory(User user, Category category, Date learnTime)
    {
        this.user = user;
        this.category = category;
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
        return (obj instanceof LearnCategoryHistory)
                && (((LearnCategoryHistory) obj).id.equals(this.id));
    }
}
