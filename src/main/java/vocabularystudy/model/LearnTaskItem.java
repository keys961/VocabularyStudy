package vocabularystudy.model;

import javax.persistence.*;

@Entity
@Table(name = "LearnTaskItem")
public class LearnTaskItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private LearnTask learnTask;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "word")
    private Vocabulary word;

    public LearnTaskItem() {}

    public LearnTaskItem(LearnTask task, Vocabulary word)
    {
        this.learnTask = task;
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

    public LearnTask getLearnTask()
    {
        return learnTask;
    }

    public void setLearnTask(LearnTask learnTask)
    {
        this.learnTask = learnTask;
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
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof LearnTaskItem)
                &&(((LearnTaskItem) obj).id.equals(this.id));
    }
}
