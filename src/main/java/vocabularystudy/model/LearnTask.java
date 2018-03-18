package vocabularystudy.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "LearnTask")
public class LearnTask
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
    @JoinColumn(name = "learn_plan")
    private LearnPlan learnPlan;

    @Column(name = "learn_time")
    private Date learnTime;

    public LearnTask() {}

    public LearnTask(User user, Category category, LearnPlan plan, Date learnTime)
    {
        this.user = user;
        this.category = category;
        this.learnPlan = plan;
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

    public LearnPlan getLearnPlan()
    {
        return learnPlan;
    }

    public void setLearnPlan(LearnPlan learnPlan)
    {
        this.learnPlan = learnPlan;
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
        return (obj instanceof LearnTask)
                && (((LearnTask) obj).id.equals(this.id));
    }
}
