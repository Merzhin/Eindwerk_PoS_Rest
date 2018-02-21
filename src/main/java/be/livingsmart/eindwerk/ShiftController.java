/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.OrderBean;
import be.livingsmart.eindwerk.domain.Shift;
import be.livingsmart.eindwerk.domain.UserBean;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PC
 */
@RestController
@RequestMapping("/shift")
public class ShiftController {
    
    @Autowired 
    private ShiftJpaRepository shiftRepo; 
    
    @Autowired
    private OrderBeanJpaRepository orderRepo;
    
    @Autowired
    private OrderedItemJpaRepository orderedItemRepo;
    
    @Autowired 
    private UserBeanJpaRepository userRepo;
    
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Shift> getAllShifts() 
    {
        return shiftRepo.findAll();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Shift getShift(@PathVariable ("id") String id) 
    {
        Long longId = new Long(id);
        return shiftRepo.findOne(longId);
    }    
    
    @RequestMapping("/testTime")
    public Time test() {
        
        return new Time(System.currentTimeMillis());
    }
    
    @RequestMapping("/testDate")
    public Date test2() {
        
        return new Date(System.currentTimeMillis());
    }
    
    @RequestMapping(value = "/{supervisor}", method = RequestMethod.POST)
    public Shift startShift(@PathVariable ("supervisor") String supervisor) throws Exception
    {
        Shift currentShift = shiftRepo.findCurrentShift();
        if ( !(currentShift == null)) throw new Exception("There's already an active shift");
        UserBean user = userRepo.findUserByName(supervisor);
        if (user == null) throw new IllegalArgumentException("Supervisor doesn't exist");
        Shift shift = new Shift();
        shift.setSupervisor(user);
        shift.setCurrentDate(new Date(System.currentTimeMillis()));
        shift.setStartTime(new Time(System.currentTimeMillis()));
        return shiftRepo.saveAndFlush(shift);
    }
    
    @RequestMapping(value = "/endShift", method = RequestMethod.POST)
    public Shift endShift() throws Exception
    {
        Shift currentShift = shiftRepo.findCurrentShift();
        if (currentShift == null) throw new Exception("There's no active shift");
        currentShift.setEndTime(new Time(System.currentTimeMillis()));
        return shiftRepo.saveAndFlush(currentShift);
    }
    
    @RequestMapping(value = "/{id}/orders", method = RequestMethod.GET)
    public Map<Long, OrderBean> getOrdersForShift(@PathVariable ("id") String id)
    {
        Long longId = new Long(id);
        Shift shift = shiftRepo.findOne(longId);
        return shift.getOrders();
    }   
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Shift deleteShift(@PathVariable ("id") String id)
    {
        Long longId = new Long(id);
        Shift shift = shiftRepo.findOne(longId);
        shiftRepo.delete(shift);
        return shift;
    }
    
    @RequestMapping(value = "/{id}/{supervisor}", method = RequestMethod.PUT)
    public Shift updateShift(@PathVariable ("id") String id, @PathVariable ("supervisor") String supervisor) 
    {
        Long longId = new Long(id);
        Shift shift = shiftRepo.findOne(longId);
        UserBean user = userRepo.findUserByName(supervisor);
        shift.setSupervisor(user);
        return shiftRepo.saveAndFlush(shift);
    }
         
    
        
}
