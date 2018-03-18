package vocabularystudy.model;

import javax.persistence.*;

@Entity
@Table(name = "Category")
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "category", unique = true)
    private String category;

    public Category()
    {

    }

    public Category(String category)
    {
        this.category = category;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }
}
