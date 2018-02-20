package be.livingsmart.eindwerk;


import be.livingsmart.eindwerk.ItemJpaRepository;
import be.livingsmart.eindwerk.domain.Item;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
    
    @Autowired
    private ItemJpaRepository itemRepo;
    
    @RequestMapping(method=RequestMethod.GET)
    public List<Item> getItems(){
        return itemRepo.findAll();
    }
    
    @RequestMapping("/addfreebeer") // this method is here for testing
    public List<Item> addFreeBeer(){
        Item item = new Item();
        item.setName("Free beer!");
        item.setDescription("The best beer");
        item.setPrice(BigDecimal.ZERO);
        itemRepo.saveAndFlush(item);
        return itemRepo.findAll();
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Item addItem(@RequestBody ItemJsonValues values) 
    {
        Item item = new Item();
        item.setName(values.getName());
        item.setDescription(values.getDescription());
        BigDecimal decimal = new BigDecimal("" + values.getPrice());
        item.setPrice(decimal);
        itemRepo.saveAndFlush(item);
        return item;
    }
    
    @RequestMapping("/{id}")
    public @ResponseBody Item getItem(@PathVariable("id") String id)
    {
        Long longId = new Long(id);
        return itemRepo.findOne(longId);
    }
}

