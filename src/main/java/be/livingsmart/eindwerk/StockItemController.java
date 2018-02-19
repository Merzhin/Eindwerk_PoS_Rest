/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.domain.StockItem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Pieter
 */
@RestController
public class StockItemController {
    
    @Autowired
    private StockItemJpaRepository stockItemRepo; 
    
    /*
    @RequestMapping
    public List<StockItem> getAllStockItems() 
    {
        return stockItemRepo.findAll();
    }
    
    @RequestMapping
    public StockItem addStockItem() {
        return new StockItem();
    }*/
}
