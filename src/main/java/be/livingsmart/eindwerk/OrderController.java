/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.livingsmart.eindwerk;

import be.livingsmart.hdr.Item;
import be.livingsmart.hdr.OrderBean;
import be.livingsmart.hdr.OrderedItem;
import be.livingsmart.hdr.Shift;
import be.livingsmart.hdr.ShiftItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *   This class is a {@link RestController} for mostly everything related to {@link OrderBean}s
 * @author Pieter
 */
@RestController
@RequestMapping("/order")
public class OrderController 
{
    @Autowired
    private OrderBeanJpaRepository orderRepo;
    
    @Autowired 
    private ShiftJpaRepository shiftRepo;
    
    @Autowired
    private ItemJpaRepository itemRepo;
    
    @Autowired 
    private OrderedItemJpaRepository orderedItemRepo;
    
    @Autowired 
    private ShiftItemJpaRepository shiftItemRepo;

    /**
     *  After calling /order, with RequestMethod = GET, this function returns a {@link List} of all {@link OrderBean}s
     * @return  {@link List} of {@link OrderBean}s  
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<OrderBean> getOrders() {
        return orderRepo.findAll();
    }
    
    /**
     *  After calling /order/{id}, with RequestMethod = GET, this function returns an {@link OrderBean} object
     * @param id    {@link String}
     * @return  {@link OrderBean}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OrderBean getOrder(@PathVariable ("id") String id) 
    {
        return orderRepo.findOne(new Long(id));
    }
    
    /**
     *  After calling /test, with the RequestMethod = POST, this function adds two {@link Item}s to the repository if there is an active {@link Shift} then creates two {@link OrderedItem}s that are connected to the aforementioned {@link Item}s and puts it in a newly created {@link OrderBean}. Then saves this {@link OrderBean} to the JPA repository
     * @return  The created {@link OrderBean} object
     * @throws Exception    If there's no active shift
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public OrderBean addOrder() throws Exception
    {
        Shift shift = shiftRepo.findActiveShift();
        if (shift == null) throw new Exception ("There's no active shift");
        
        OrderBean order = new OrderBean();
        order.setShift(shift);
        order.setOrderedItems(new HashMap<Long, OrderedItem>());
        order = orderRepo.saveAndFlush(order);
        
        Item item = new Item();
        item.setName("Beer");
//        item.setPrice(new BigDecimal("" + 3.5));
        item.setPrice(3.5);
        item = itemRepo.saveAndFlush(item);
        
        Item item2 = new Item();
        item2.setName("Beer2");
//        item2.setPrice(new BigDecimal("" + 2));
        item2.setPrice(2);
        item2 = itemRepo.saveAndFlush(item2);
        
        
        OrderedItem orderedItem = new OrderedItem();
        orderedItem.setItem(item);
        orderedItem.setAmount(5);
        orderedItem.setOrder(order);
        orderedItem = orderedItemRepo.saveAndFlush(orderedItem);
        
        OrderedItem orderedItem2 = new OrderedItem();
        orderedItem2.setItem(item2);
        orderedItem2.setAmount(10);
        orderedItem2.setOrder(order);
        orderedItem2 = orderedItemRepo.saveAndFlush(orderedItem2);
        
        
        order = orderRepo.findOne(order.getId());
        
        return order;
        
    }
    
    /**
     *  When /order/orderedItems is called, RequestMethod = GET, returns a {@link List} of all the {@link OrderedItem}s
     * @return  {@link List} of {@link OrderedItem}s
     */
    @RequestMapping(value = "/orderedItems")
    public List<OrderedItem> orderedItems()
    {
        return orderedItemRepo.findAll();
    }
    
    /**
     After calling /order, RequestMethod = POST, this function will make a new {@link OrderBean} with the values specified in the RequestBody and add it to the repository 
     * @param values    {@link Map} of {@link Long}s ({@link Item} id) and {@link Integer} (amount of {@link Item}s in this order)  
     * @return  Created {@link OrderBean}
     * @throws Exception    When there is no active shift
     * @throws IllegalArgumentException When there is an {@link Item} id in the {@link Map} while no {@link Item} with that id exists
     */
    @RequestMapping (method = RequestMethod.POST)
    public OrderBean addOrder(@RequestBody Map<Long, Integer> values) throws Exception
    {
        OrderBean order = new OrderBean();
        Shift shift = shiftRepo.findActiveShift();
        if (shift == null) throw new Exception ("There's no active shift");
        order.setShift(shift);
        order.setOrderedItems(new HashMap<Long, OrderedItem>());
        order = orderRepo.saveAndFlush(order);
        
        for (Map.Entry<Long, Integer> entry : values.entrySet()) {
            Long id = entry.getKey();
            int value = entry.getValue();
            Item item = itemRepo.getOne(id);
            if (item == null) throw new IllegalArgumentException("Item with id " + id +" doesn't exists");
            
            OrderedItem oItem = new OrderedItem();
            oItem.setItem(item);
            oItem.setAmount(value);
            oItem.setOrder(order);
            
            orderedItemRepo.saveAndFlush(oItem);
            
            
            ShiftItem shiftItem = shiftItemRepo.findActiveShiftItemWithItemId(id, shift);
            
            if (shiftItem == null ) {
                shiftItem = new ShiftItem();
                shiftItem.setItem(item);
                shiftItem.setAmount(0);
                shiftItem.setShift(shift);
            }
            
            
            int amount = shiftItem.getAmount();
            shiftItem.setAmount(amount + value);
           
            shiftItemRepo.saveAndFlush(shiftItem);
            
            
        }
        
        return orderRepo.findOne(order.getId());
    }
    
    /**
     *  When /order is called, RequestMethod = DELETE, the {@link OrderBean} with {id} gets deleted
     * @param id    {@link String} id 
     * @return The deleted {@link OrderBean}
     * @throws IllegalArgumentException When no item exists with the given id
     */
    @RequestMapping (value = "/{id}", method = RequestMethod.DELETE)
    public OrderBean deleteOrder(@PathVariable ("id") String id)
    {   
        OrderBean order = orderRepo.findOne(new Long(id));
        if (order == null) throw new IllegalArgumentException("Item with id " + id + "doesn't exist");
        orderRepo.delete(order);
        return order;
    }
}
