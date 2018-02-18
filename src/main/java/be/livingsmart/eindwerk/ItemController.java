package be.livingsmart.eindwerk;

import be.livingsmart.eindwerk.ItemJpaRepository;
import be.livingsmart.eindwerk.domain.Item;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
    
    @Autowired
    private ItemJpaRepository itemRepo;
    
    @RequestMapping("/item")
    public List<Item> getItems(){
        return itemRepo.findAll();
    }
    
    @RequestMapping("/item/addfreebeer") // this method is here for testing
    public List<Item> addFreeBeer(){
        Item item = new Item();
        item.setName("Free beer!");
        item.setDescription("The best beer");
        item.setPrice(BigDecimal.ZERO);
        itemRepo.saveAndFlush(item);
        return itemRepo.findAll();
    }
}

