/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.livingsmart.eindwerk;

import be.livingsmart.hdr.Logo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *  This class is a {@link RestController} for everything related to {@link Logo}s
 * @author Pieter
 */

@RestController
@RequestMapping("/logo")
public class LogoController 
{
    @Autowired 
    private LogoJpaRepository logoRepo;
    
    /**
     *  After calling /logo, with RequestMethod = GET, this function returns a {@link List} of all {@link Logo}s
     * @return  {@link List} of all {@link Logo}s
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Logo> getAllLogos() 
    {
        return logoRepo.findAll();
    }
    
    /**
     *  After calling /logo, with RequestMethod = POST, this function adds a {@link Logo} to the JpaRepository
     * @param logo  {@link RequestBody} {@link Logo}: Variables: "name" ({@link String}), "url" ({@link String})
     * @return  The added {@link Logo}
     */
    @RequestMapping(method = RequestMethod.POST)
    public Logo addLogo(@RequestBody Logo logo)
    {
        return logoRepo.saveAndFlush(logo);
    }
    
    /**
     *  After calling /logo/{id}, with RequestMethod = DELETE; this function deletes the {@link Logo} whose {id} is the PathVariable from the JpaRepository
     * @param id    {@link Long}
     * @return  The deleted {@link Logo}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Logo deleteLogo(@PathVariable ("id") String id)
    {
        Logo logo = logoRepo.findOne(new Long(id));
        logoRepo.delete(logo);
        return logo;
    }
            
}
