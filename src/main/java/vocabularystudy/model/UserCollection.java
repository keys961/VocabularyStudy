package vocabularystudy.model;

import javax.persistence.*;

@Entity
@Table(name = "UserCollection")
public class UserCollection
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "user")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "word")
    private Vocabulary word;

    public UserCollection(){}

    public UserCollection(User user, Vocabulary word)
    {
        this.user = user;
        this.word = word;
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

    public Vocabulary getWord()
    {
        return word;
    }

    public void setWord(Vocabulary word)
    {
        this.word = word;
    }

    @Override
    public int hashCode()
    {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof UserCollection
                && this.id.equals(((UserCollection) obj).id);
    }
}
