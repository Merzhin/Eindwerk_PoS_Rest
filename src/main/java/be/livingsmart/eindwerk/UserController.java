/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.livingsmart.eindwerk;
import be.livingsmart.eindwerk.UserJpaRepository;
import be.livingsmart.eindwerk.domain.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Pieter
 */
@RestController
public class UserController {
    
    @Autowired
    private UserJpaRepository userRepo;
    
    @RequestMapping("/getUsers")
    public List<User> getUsers(){
        return userRepo.findAll();
    }
    
    @RequestMapping("/GetUser")
    public List<User> getUserByName() 
    {
        
        
        return userRepo.findUserByName("name");
    }
    
    @RequestMapping("/user")
    public List<String> getAllUsernames(){
        List<String> result = new ArrayList<>();
        for(User u : userRepo.findAll()){
            result.add(u.getName());
        }
        return result;
    }
    
    @RequestMapping("/user/addtestuser") // this method is here for testing
    public User addTestUser(){
        User u = new User();
        u.setHashedPassword("test");
        u.setName("Tom T");
        userRepo.saveAndFlush(u);
        return u;
    }
    
}
