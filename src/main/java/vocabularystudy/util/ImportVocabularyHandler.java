package vocabularystudy.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import vocabularystudy.model.Category;
import vocabularystudy.model.Vocabulary;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class ImportVocabularyHandler
{
    private Category[] categories = new Category[]{
            new Category("Core Vocabulary"),
            new Category("CET-4"),
            new Category("CET-6"),
            new Category("GRE")
    };

    {
        for(int i = 0; i < categories.length; i++)
            categories[i].setId((long)i + 1);
    }

    private List<Vocabulary> vocabularyList = new LinkedList<>();

    private DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

    private Document parse(File file)
    {
        Document document = null;
        try
        {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            document = builder.parse(file);
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return document;
    }

    public void resolute(Document document)
    {
        NodeList itemList = document.getElementsByTagName("item");
        for(int i = 0; i < itemList.getLength(); i++)
        {
            Node node = itemList.item(i);
            NodeList childList = node.getChildNodes();
            Vocabulary vocabulary = new Vocabulary();
            vocabulary.setId((long)i + 1);
            for(int j = 0; j < childList.getLength(); j++)
            {
                switch (childList.item(j).getNodeName())
                {
                    case "word":
                        vocabulary.setWord(childList.item(j).getTextContent());
                        break;
                    case "trans":
                        vocabulary.setTranslation(childList.item(j).getTextContent());
                        break;
                    case "phonetic":
                        vocabulary.setPhonetic(childList.item(j).getTextContent());
                        break;
                    case "tags":
                        vocabulary.setTag(childList.item(j).getTextContent());
                        String tag = childList.item(j).getTextContent();
                        if(tag.contains("700"))
                            vocabulary.setCategory(categories[0]);
                        else if(tag.contains("CET-4"))
                            vocabulary.setCategory(categories[1]);
                        else if(tag.contains("CET-6"))
                            vocabulary.setCategory(categories[2]);
                        else if(tag.contains("GRE"))
                            vocabulary.setCategory(categories[3]);
                        else
                            vocabulary.setCategory(categories[0]);
                }
            }

            vocabularyList.add(vocabulary);
        }
    }

    public void recordVocabulary() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/VocabularyStudy?" +
                "useUnicode=true&characterEncoding=utf8&useSSL=true&useLegacyDatetimeCode=false&serverTimezone=America/New_York",
                "root", "123456");
        //insert category
        String categorySql = "INSERT INTO Category VALUES (?,?)";
        String vocabularySql = "INSERT INTO Vocabulary VALUES (?,?,?,?,?,?)";

        for(Category category : categories)
        {
            PreparedStatement statement = connection.prepareStatement(categorySql);
            statement.setLong(1, category.getId());
            statement.setString(2, category.getCategory());
            statement.executeUpdate();
        }

        for(Vocabulary vocabulary : vocabularyList)
        {
            PreparedStatement statement = connection.prepareStatement(vocabularySql);
            statement.setLong(1, vocabulary.getId());
            statement.setString(2, vocabulary.getWord());
            statement.setString(3, vocabulary.getTranslation());
            statement.setString(4, vocabulary.getPhonetic());
            statement.setString(5, vocabulary.getTag());
            statement.setLong(6, vocabulary.getCategory().getId());
            statement.executeUpdate();
        }

        connection.close();
    }

    public static void main(String[] args)
    {
        ImportVocabularyHandler handler = new ImportVocabularyHandler();
        File f = new File("src/main/resources/vocabulary-set.xml");
        Document document = handler.parse(f);
        handler.resolute(document);
        try {
            handler.recordVocabulary();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
