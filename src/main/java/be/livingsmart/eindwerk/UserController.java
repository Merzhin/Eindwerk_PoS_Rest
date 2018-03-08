/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.livingsmart.eindwerk;
import be.livingsmart.eindwerk.domain.UserBean;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  This class is a {@link RestController} for everything related to {@link UserBean}s
 * @author Pieter
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserBeanJpaRepository userRepo;
    
    /**
     *  After calling /user/{username}, RequestMethod = GET, the function returns the {@link UserBean} with the given {id} 
     * @param username  {@link String}
     * @return  {@link UserBean}, returns null if username doesn't exist
     */
    @RequestMapping(value = "/getUser/{username}", method = RequestMethod.GET)
    public UserBean getUserByName(@PathVariable (value="username") String username) 
    {
        return userRepo.findUserByName(username);
    }
    
    /**
     *  After calling /user, @return  with RequestMethod = GET, this function returns a {@link List} of all {@link String} usernames
     * @return  {@link List} of {@link String}s  
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<String> getAllUsernames(){
        List<String> result = new ArrayList<>();
        for(UserBean u : userRepo.findAll()){
            result.add(u.getName());
        }
        return result;
    }
    
    /**
     *  After calling /user/addUser, with the username and password parameters, a {@link UserBean} gets created and added (password gets hashed). This function sends plaintext passwords please use the RequestBody addUser
     * @param username  {@link String}
     * @param password  {@link String}
     * @return  The created {@link UserBean}
     */
    @RequestMapping("/addUser")
    public UserBean addUser(@RequestParam (value="username") String username, @RequestParam (value="password") String password) 
    {
        UserBean user = new UserBean();
        user.setHashedPassword(password);
        user.setName(username);
        userRepo.saveAndFlush(user);
        return user;
    }
    
    /**
     *  After calling /user, RequestMethod = POST, a new {@link UserBean} gets created using the values
     * @param values    "username" ({@link String}, "password" ({@link String}, gets hashed when added)
     * @return  The created {@link UserBean}
     */
    @RequestMapping(method = RequestMethod.POST )
    public UserBean addUser(@RequestBody UserJsonValues values)
    {
        if (this.exists(values.getUsername())) throw new IllegalArgumentException("User already exists");
        UserBean user = new UserBean();
        user.setName(values.getUsername());
        user.setHashedPassword(values.getPassword());
        return userRepo.saveAndFlush(user);
    }
    
    /**
     *  After calling /user/validateUser, RequestMethod = GET, the user gets validated with the password, this function sends plaintext passwords please use the RequestBody validateUser
     * @param username  {@link String}
     * @param password  {@link String}
     * @return  A {@link Boolean}, if the user is validated: true, if the user isn't validated or user doesn't exist: false
     */
    @RequestMapping(value = "/validateUser", method = RequestMethod.GET)
    public boolean validateUser(@RequestParam (value="username") String username, @RequestParam (value="password") String password) 
    {
        if (!this.exists(username)) return false;
        UserBean user = userRepo.findUserByName(username);
        if (user == null) return false;
        return user.validatePassword(password);
    }

    /**
     *  After calling /user/validateUser, RequestMethod = POST, the user gets validated with the password from the RequestBody
     * @param values   "username": ({@link String}), "password": ({@link String}, the password get's hashed in the process of creating the object)
     * @return  A {@link Boolean}, if the user is validated: true, if the user isn't validated or user doesn't exist: false
     */
    @RequestMapping(value = "/validateUser", method = RequestMethod.POST)
    public boolean validateUser(@RequestBody UserJsonValues values) 
    {
        if (!this.exists(values.getUsername())) return false;
        UserBean user = userRepo.findUserByName(values.getUsername());
        return user.validatePassword(values.getPassword());
    }
    
    /**
     *  After calling /user, RequestMethod = PUT, the {@link UserBean} with "username" ({@link String}) updates their "password"
     * @param values    "username": ({@link String}), "password": ({@link String}, the password get's hashed
     * @return  The updated {@link UserBean}
     */
    @RequestMapping(method = RequestMethod.PUT)
    public UserBean updateUser(@RequestBody UserJsonValues values) 
    {
        UserBean user = userRepo.findUserByName(values.getUsername());
        user.setHashedPassword(values.getPassword());
        return userRepo.saveAndFlush(user);
    }
    
    /**
     *  After calling /user, RequestMethod = DELETE, the {@link UserBean} with "username" gets deleted
     * @param username  {@link String}
     * @return  The deleted {@link UserBean}
     */
    @RequestMapping(value = "/{username}",method = RequestMethod.DELETE)
    public UserBean deleteUser(@PathVariable ("username") String username)
    {
        UserBean user = userRepo.findUserByName(username);
        if (user == null) throw new IllegalArgumentException("User doesn't exist");
        userRepo.delete(user);
        userRepo.flush();
        return user;
    }
    
    /**
     *  After calling /user/exists/{username}, RequestMethod = GET, the {@link UserBean} with "username" gets deleted
     * @param username  {@link String}
     * @return  {@link Boolean} true if a {@link UserBean} with username ({@link String}) exists) 
     */
    @RequestMapping(value = "/exists/{username}", method = RequestMethod.GET)
    public boolean exists(@PathVariable ("username") String username)
    {
        UserBean user = userRepo.findUserByName(username);
        if (user == null ) return false;
        return true;
    }
    
}
