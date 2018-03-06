/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.Item;
import be.livingsmart.eindwerk.domain.OrderBean;
import be.livingsmart.eindwerk.domain.OrderedItem;
import be.livingsmart.eindwerk.domain.Shift;
import be.livingsmart.eindwerk.domain.ShiftItem;
import be.livingsmart.eindwerk.domain.StockItem;
import java.math.BigDecimal;
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
 *
 * @author PC
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

    
    @RequestMapping(method = RequestMethod.GET)
    public List<OrderBean> getOrders() {
        return orderRepo.findAll();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OrderBean getOrder(@PathVariable ("id") String id) 
    {
        return orderRepo.findOne(new Long(id));
    }
    
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
    
    @RequestMapping(value = "/orderedItems")
    public List<OrderedItem> orderedItems()
    {
        return orderedItemRepo.findAll();
    }
    
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
            
            
            ShiftItem shiftItem = shiftItemRepo.findShiftItemWithItemId(id);
            
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
    
    @RequestMapping (value = "/{id}", method = RequestMethod.DELETE)
    public OrderBean deleteOrder(@PathVariable ("id") String id)
    {
        return new OrderBean();
    }
}
