/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.Logo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Pieter
 */

@RestController
@RequestMapping("/logo")
public class LogoController 
{
    @Autowired 
    private LogoJpaRepository logoRepo;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Logo> getAllLogos() 
    {
        return logoRepo.findAll();
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public Logo addLogo(@RequestBody Logo logo)
    {
        return logoRepo.saveAndFlush(logo);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Logo deleteLogo(@PathVariable ("id") String id)
    {
        Logo logo = logoRepo.findOne(new Long(id));
        logoRepo.delete(logo);
        return logo;
    }
            
}
