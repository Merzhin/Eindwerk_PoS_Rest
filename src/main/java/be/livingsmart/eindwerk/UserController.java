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
import static org.springframework.http.RequestEntity.method;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Pieter
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserBeanJpaRepository userRepo;
    
    
    @RequestMapping(method = RequestMethod.GET)
    public List<UserBean> getAllUsers() 
    {
        return userRepo.findAll();
    }
    
    @RequestMapping(value = "/getUser/{username}", method = RequestMethod.GET)
    public UserBean getUserByName(@PathVariable (value="username") String username) 
    {
        return userRepo.findUserByName(username);
    }
    
    @RequestMapping(value = "/getUsernames", method = RequestMethod.GET)
    public List<String> getAllUsernames(){
        List<String> result = new ArrayList<>();
        for(UserBean u : userRepo.findAll()){
            result.add(u.getName());
        }
        return result;
    }
    
    @RequestMapping("/addUser")
    public UserBean addUser(@RequestParam (value="username") String username, @RequestParam (value="password") String password) 
    {
        UserBean user = new UserBean();
        user.setHashedPassword(password);
        user.setName(username);
        userRepo.saveAndFlush(user);
        return user;
    }
    
    @RequestMapping(method = RequestMethod.POST )
    public UserBean addUser(@RequestBody UserJsonValues values)
    {
        if (this.exists(values.getUsername())) throw new IllegalArgumentException("User already exists");  // TODO
        UserBean user = new UserBean();
        user.setName(values.getUsername());
        user.setHashedPassword(values.getPassword());
        return userRepo.saveAndFlush(user);
    }
    
    @RequestMapping(value = "/validateUser", method = RequestMethod.GET)
    public boolean validateUser(@RequestParam (value="username") String username, @RequestParam (value="password") String password) 
    {
        if (!this.exists(username)) return false;
        UserBean user = userRepo.findUserByName(username);
        if (user == null) return false;
        return user.validatePassword(password);
    }

    @RequestMapping(value = "/validateUser", method = RequestMethod.POST)
    public boolean validateUser(@RequestBody UserJsonValues values) 
    {
        if (!this.exists(values.getUsername())) return false;
        UserBean user = userRepo.findUserByName(values.getUsername());
        return user.validatePassword(values.getPassword());
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public UserBean updateUser(@RequestBody UserJsonValues values) 
    {
        UserBean user = userRepo.findUserByName(values.getUsername());
        user.setHashedPassword(values.getPassword());
        return userRepo.saveAndFlush(user);
    }
    
    @RequestMapping(value = "/{username}",method = RequestMethod.DELETE)
    public UserBean deleteUser(@PathVariable ("username") String username)
    {
        UserBean user = userRepo.findUserByName(username);
        userRepo.delete(user);
        return user;
    }
    
    @RequestMapping(value = "/exists/{username}", method = RequestMethod.GET)
    public boolean exists(@PathVariable ("username") String username)
    {
        UserBean user = userRepo.findUserByName(username);
        if (user == null ) return false;
        return true;
    }
    
}
