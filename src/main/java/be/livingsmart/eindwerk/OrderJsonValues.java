/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.livingsmart.eindwerk;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PC
 */
class OrderJsonValues {
    Map<Long, Integer> order;

    OrderJsonValues() {
        this.order = new HashMap<Long, Integer>();
    }

    public Map<Long, Integer> getOrder() {
        return order;
    }

    public void setOrder(Map<Long, Integer> order) {
        this.order = order;
    }
    
    
    
    
}
