package vocabularystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vocabularystudy.config.SecurityConfig;
import vocabularystudy.model.User;
import vocabularystudy.model.UserCollection;
import vocabularystudy.model.Vocabulary;
import vocabularystudy.repository.UserCollectionRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/collection")
public class UserCollectionController
{
    @Autowired
    private UserCollectionRepository userCollectionRepository;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String userCollectionPage()
    {
        return "collection/user_collection";
    }

    @RequestMapping(value = "/get/{offset}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<UserCollection>> getUserCollectionList(HttpSession session, @PathVariable Long offset)
    {
        User user = (User)session.getAttribute(SecurityConfig.SESSION_KEY);
        List<UserCollection> list = userCollectionRepository.getUserCollectionList(user, offset, 30L);
        if(list.size() == 0)
            return ResponseEntity.notFound().build();
        return new ResponseEntity<List<UserCollection>>(list, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/removeList", method = RequestMethod.POST, produces = "application/json", consumes = "application/json; charset=utf-8")
    public ResponseEntity removeUserCollectionList(HttpSession session, @RequestBody List<Long> collectionList)
    {
        User user = (User)session.getAttribute(SecurityConfig.SESSION_KEY);
        List<UserCollection> collections = new ArrayList<>(20);
        for(Long id : collectionList)
        {
            UserCollection collection = new UserCollection();
            collection.setId(id);
            collections.add(collection);
        }

        userCollectionRepository.deleteUserCollectionList(collections);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity addCollection(HttpSession session, Long id)
    {
        User user = (User)session.getAttribute(SecurityConfig.SESSION_KEY);
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setId(id);

        if(userCollectionRepository.exist(user, vocabulary))
            return ResponseEntity.ok().build();

        UserCollection collection = new UserCollection();
        collection.setUser(user);
        collection.setWord(vocabulary);

        if(userCollectionRepository.save(collection) != null)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity removeCollection(HttpSession session, Long id)
    {
        User user = (User)session.getAttribute(SecurityConfig.SESSION_KEY);
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setId(id);

        UserCollection collection = userCollectionRepository.find(user, vocabulary);
        if(collection != null)
        {
            userCollectionRepository.delete(collection);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
