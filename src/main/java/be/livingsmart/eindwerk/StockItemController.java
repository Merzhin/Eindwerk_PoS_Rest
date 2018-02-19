/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.Item;
import be.livingsmart.eindwerk.domain.StockItem;
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
    
    
    @RequestMapping(method = RequestMethod.GET)
    public List<StockItem> getAllStockItems() 
    {
        return stockItemRepo.findAll();
    }
    
    @RequestMapping(value="/{amount}", method=RequestMethod.POST)
    public StockItem addStockItem(@RequestBody Item item, @PathVariable("amount") String amount) {
      
        StockItem stockItem = new StockItem();
        stockItem.setItem(item);  
        stockItem.setAmount(Integer.parseInt(amount));
        return stockItemRepo.saveAndFlush(stockItem);
    }
}
