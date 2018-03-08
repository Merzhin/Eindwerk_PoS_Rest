/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.OrderBean;
import be.livingsmart.eindwerk.domain.Shift;
import be.livingsmart.eindwerk.domain.ShiftItem;
import be.livingsmart.eindwerk.domain.Item;
import be.livingsmart.eindwerk.domain.UserBean;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *  This class is a {@link RestController} for mostly everything related to {@link Shift}s
 * @author Pieter
 */
@RestController
@RequestMapping("/shift")
public class ShiftController {
    
    @Autowired 
    private ShiftJpaRepository shiftRepo; 
    
    @Autowired 
    private UserBeanJpaRepository userRepo;
    
    /**
     *  After calling /shift, @return  with RequestMethod = GET, this function returns a {@link List} of all {@link Shift}s
     *  @return  {@link List} of {@link Item}s  
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Shift> getAllShifts() 
    {
        return shiftRepo.findAll();
    }
    
    /**
     *  After calling /shift/{id}, RequestMethod = GET, the function returns the {@link Shift} with the given {id} 
     * @param id {@link String}
     * @return  {@link Shift}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Shift getShift(@PathVariable ("id") String id) 
    {
        Long longId = new Long(id);
        Shift shift = shiftRepo.findOne(longId);
        if (shift == null) throw new IllegalArgumentException("No shift with id exists");
        return shift;
    }    
    
    /**
     *  After calling /shift/{supervisor}, RequestMethod = POST, the function starts a new {@link Shift}, sets the current time, date and the {supervisor} ({@link UserBean}) and saves it to the JPA repository
     * @param supervisor    {@link String} Supervisor must be a name that exists in the {@link UserBeanJpaRepository}
     * @return  The started {@link Shift}
     * @throws Exception    When there's already an active {@link Shift}
     * @throws IllegalArgumentException When the supervisor doesn't exist in the {@link UserBeanJpaRepository}
     */
    @RequestMapping(value = "/{supervisor}", method = RequestMethod.POST)
    public Shift startShift(@PathVariable ("supervisor") String supervisor) throws Exception
    {
        Shift currentShift = shiftRepo.findActiveShift();
        if ( !(currentShift == null)) throw new Exception("There's already an active shift");
        UserBean user = userRepo.findUserByName(supervisor);
        if (user == null) throw new IllegalArgumentException("Supervisor doesn't exist");
        Shift shift = new Shift();
        shift.setSupervisor(user);
        shift.setCurrentDate(new Date(System.currentTimeMillis()));
        shift.setStartTime(new Time(System.currentTimeMillis()));
        return shiftRepo.saveAndFlush(shift);
    }
    
    /**
     *  After calling /shift/endShift, RequestMethod = POST, the function ends the active {@link Shift}, then writes a new Excel file with {@link ExcelWriter} and sends an email to a hardcoded email address
     * @return the {@link Shift} that has ended
     * @throws Exception    When there currently isn't an active {@link Shift}
     */
    @RequestMapping(value = "/endShift", method = RequestMethod.POST)
    public Shift endShift() throws Exception
    {
        Shift currentShift = shiftRepo.findActiveShift();
        if (currentShift == null) throw new Exception("There's no active shift");
        currentShift.setEndTime(new Time(System.currentTimeMillis()));
        ExcelWriter writer = new ExcelWriter();
        writer.shiftReport(currentShift);
        return shiftRepo.saveAndFlush(currentShift);
    }
    
    /**
     *  After calling /shift/{id}/orders, RequestMethod = GET, this function will return a {@link Map} of all {@link OrderBean}s for the {@link Shift} with {id}
     * @param id    {@link String}
     * @return  {@link Map} of {@link Long}s (orderId) and {@link OrderBean}
     */
    @RequestMapping(value = "/{id}/orders", method = RequestMethod.GET)
    public Map<Long, OrderBean> getOrdersForShift(@PathVariable ("id") String id)
    {
        Long longId = new Long(id);
        Shift shift = shiftRepo.findOne(longId);
        if (shift == null) throw new IllegalArgumentException("No shift with id exists");
        return shift.getOrders();
    } 
    
    /**
     *  After calling /shift/orders, RequestMethod = GET, this function will return all {@link OrderBean}s for the active {@link Shift}
     * @return  {@link Map} of {@link Long}s (orderId) and {@link OrderBean}
     * @throws Exception    When there currently isn't an active {@link Shift}
     */
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public Map<Long, OrderBean> getOrdersForActiveShift() throws Exception
    {
        Shift shift = shiftRepo.findActiveShift();
        if (shift == null) throw new Exception("There's no active shift");
        return shift.getOrders();
    }  
    
    /**
     *  After calling /shift/{id}, RequestMethod = DELETE, this function will DELETE a {@link Shift} from the JPA repository with {id}
     * @param id   {@link String}
     * @return  {@link Shift}
     * @throws IllegalArgumentException When no shift with id exists
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Shift deleteShift(@PathVariable ("id") String id)
    {
        Long longId = new Long(id);
        Shift shift = shiftRepo.findOne(longId);
        if (shift == null) throw new IllegalArgumentException("No shift with id exists");
        shiftRepo.delete(shift);
        return shift;
    }
    
    /**
     *  After calling /shift/{id}, RequestMethod = DELETE, this function will DELETE the active {@link Shift} from the JPA repository
     * @return  The deleted {@link Shift}
     * @throws Exception    When there's no active {@link Shift}
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public Shift deleteActiveShift() throws Exception
    {
        Shift shift = shiftRepo.findActiveShift();
        if (shift == null) throw new Exception("There's no active shift");
        shiftRepo.delete(shift);
        return shift;
    }
    
    /**
     *  After calling /shift/{id}, RequestMethod = GET, this function will return the {@link ShiftItem} for the active {@link Shift}
     * @return  {@link Map} of {@link Long} (ids) and {@link ShiftItem}s
     * @throws Exception When there's no active {@link Shift}
     */
    @RequestMapping(value = "/shiftItems", method = RequestMethod.GET)
    public Map<Long, ShiftItem> getShiftItemsForActiveShift() throws Exception
    {
        Shift shift = shiftRepo.findActiveShift();
        if (shift == null) throw new Exception("There's no active shift");
        return shift.getShiftItems();
    } 
    
    /**
     *  After calling /shift/activeShift, RequestMethod = GET, this function will return the active {@link Shift}
     * @return  Active {@link Shift}
     */
    @RequestMapping(value = "/activeShift", method = RequestMethod.GET)
    public Shift getActiveShift()
    {
        return shiftRepo.findActiveShift();
    }
        
}
