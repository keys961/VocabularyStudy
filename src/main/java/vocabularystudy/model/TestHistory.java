package vocabularystudy.model;


import javax.persistence.*;

@Entity
@Table(name = "TestHistory")
public class TestHistory
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

    @Column(name = "total_score", nullable = false)
    private Integer totalScore;

    @Column(name = "score", nullable = false)
    private Integer score;

    public TestHistory() {}

    public TestHistory(User user, Category category, int totalScore, int score)
    {
        this.user = user;
        this.category = category;
        this.totalScore = totalScore;
        this.score = score;
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

    public Integer getTotalScore()
    {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore)
    {
        this.totalScore = totalScore;
    }

    public Integer getScore()
    {
        return score;
    }

    public void setScore(Integer score)
    {
        this.score = score;
    }

    @Override
    public int hashCode()
    {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof TestHistory)
                && (((TestHistory) obj).id.equals(this.id));
    }
}
