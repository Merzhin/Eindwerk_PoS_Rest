/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.Item;
import be.livingsmart.eindwerk.domain.StockItem;
import java.math.BigDecimal;
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
@RequestMapping("/stockItem")
public class StockItemController {
    
    @Autowired
    private StockItemJpaRepository stockItemRepo; 
    
    @Autowired 
    private ItemJpaRepository itemRepo;
    
    
    @RequestMapping(method = RequestMethod.GET)
    public List<StockItem> getAllStockItems() 
    {
        return stockItemRepo.findAll();
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public StockItem getStockItem(@PathVariable("id") String id) {
        Long longId = new Long(id);
        return stockItemRepo.findOne(longId);
    }
    
    @RequestMapping(value="/{amount}", method=RequestMethod.POST)
    public StockItem addStockItem(@RequestBody ItemJsonValues values, @PathVariable("amount") String amount) {
        
        int intAmount = Integer.parseInt(amount);
        StockItem stockItem = new StockItem();
        
        Item item = new Item();
        item.setName(values.getName());
        item.setDescription(values.description);
//        item.setPrice(new BigDecimal("" + values.getPrice()));
        item.setPrice(values.getPrice());
        item = itemRepo.saveAndFlush(item);
        
        stockItem.setItem(item);
        stockItem.setAmount(intAmount);
        return stockItemRepo.saveAndFlush(stockItem);
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public StockItem deleteStockItem(@PathVariable ("id") String id)
    {
        Long longId = new Long(id);
        StockItem stockItem = stockItemRepo.findOne(longId);
        itemRepo.delete(stockItem.getItem());
        stockItemRepo.delete(longId);
        return stockItem;
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public StockItem updateStockItem(@RequestBody StockItem stockItem)
    {
        itemRepo.saveAndFlush(stockItem.getItem());
        return stockItemRepo.saveAndFlush(stockItem);
    }
    
    
}
