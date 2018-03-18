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
