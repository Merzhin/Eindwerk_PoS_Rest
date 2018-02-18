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

/**
 *
 * @author Pieter
 */
@RestController
public class UserController {
    
    @Autowired
    private UserBeanJpaRepository userRepo;
    
    @RequestMapping("/getUsers")
    public List<UserBean> getUsers(){
        return userRepo.findAll();
    }
    
    @RequestMapping("/GetUser")
    public List<UserBean> getUserByName() 
    {
        
        
        return userRepo.findUserByName("name");
    }
    
    @RequestMapping("/user")
    public List<String> getAllUsernames(){
        List<String> result = new ArrayList<>();
        for(UserBean u : userRepo.findAll()){
            result.add(u.getName());
        }
        return result;
    }
    
    @RequestMapping("/user/addtestuser") // this method is here for testing
    public UserBean addTestUser(){
        UserBean u = new UserBean();
        u.setHashedPassword("test");
        u.setName("Tom T");
        userRepo.saveAndFlush(u);
        return u;
    }
    
}
