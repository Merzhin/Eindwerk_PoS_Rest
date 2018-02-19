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
import be.livingsmart.eindwerk.UserBeanJpaRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Pieter
 */
@RestController
public class UserController {
    
    @Autowired
    private UserBeanJpaRepository userRepo;
    
        
    @RequestMapping("/getUser")
    public UserBean getUserByName(@RequestParam (value="username") String username) 
    {
        return userRepo.findUserByName(username);
    }
    
    @RequestMapping("/user")
    public List<String> getAllUsernames(){
        List<String> result = new ArrayList<>();
        for(UserBean u : userRepo.findAll()){
            result.add(u.getName());
        }
        return result;
    }
    
    @RequestMapping("/user/addUser")
    public UserBean addUser(@RequestParam (value="username") String username, @RequestParam (value="password") String password) 
    {
        UserBean user = new UserBean();
        user.setHashedPassword(password);
        user.setName(username);
        userRepo.saveAndFlush(user);
        return user;
    }
    
    @RequestMapping("/user/validateUser")
    public boolean validateUser(@RequestParam (value="username") String username, @RequestParam (value="password") String password) 
    {
        UserBean user = userRepo.findUserByName(username);
        return user.validatePassword(password);
    }
    
    @RequestMapping("/user/validateUser")
    public boolean validateUser(@RequestBody UserJsonValues values) 
    {
        UserBean user = userRepo.findUserByName(values.getUsername());
        return user.validatePassword(values.getPassword());
    }
    
}
