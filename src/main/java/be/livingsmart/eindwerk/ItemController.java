package be.livingsmart.eindwerk;


import be.livingsmart.eindwerk.ItemJpaRepository;
import be.livingsmart.eindwerk.domain.Item;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    /*
    @RequestMapping("/item/additem")
    public Item addItem(@RequestParam (value="name") String name, @RequestParam (value="description") String description, @RequestParam (value="name") String price) 
    {
        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(new BigDecimal(price));
        
        itemRepo.saveAndFlush(item);
        return item;
    } */
    
    @RequestMapping("/item/additem")
    public Item addItem(@RequestBody ItemJsonValues values) 
    {
        Item item = new Item();
        item.setName(values.getName());
        item.setDescription(values.getDescription());
        item.setPrice(new BigDecimal(values.getPrice()));
        
        itemRepo.saveAndFlush(item);
        return item;
    }
}

