package be.livingsmart.eindwerk;


import be.livingsmart.eindwerk.domain.Item;
import be.livingsmart.eindwerk.domain.Logo;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
    
    @Autowired
    private ItemJpaRepository itemRepo;
    
    @Autowired
    private LogoJpaRepository logoRepo;
    
    @RequestMapping(method=RequestMethod.GET)
    public List<Item> getItems(){
        return itemRepo.findAll();
    }
    
    @RequestMapping("/addfreebeer") // this method is here for testing
    public List<Item> addFreeBeer(){
        Item item = new Item();
        item.setName("Free beer!");
        item.setPrice(0);
        
        
        itemRepo.saveAndFlush(item);
        return itemRepo.findAll();
    }
    
    
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Item addItem(@RequestBody Item values) 
    {
        Item item = new Item();
        item.setName(values.getName());
//        BigDecimal decimal = new BigDecimal("" + values.getPrice());
        //item.setLogoID(values.getLogoID());
        item.setPrice(values.getPrice());
        item.setIsFavorite(values.isIsFavorite());
        item = itemRepo.saveAndFlush(item);
        
        return item;
    }
    
    @RequestMapping(value = "/setFavorites", method = RequestMethod.POST)
    public String addLogosToItems(@RequestBody Map<Long, Long> values) 
    {
        List<Item> items = itemRepo.findAll();
        for (Item item : items)
        {
            item.setIsFavorite(false);
            item.setLogo(null);
            itemRepo.saveAndFlush(item);
        }
        
        
        for (Map.Entry<Long, Long> entry : values.entrySet() )
        {
            Item item = itemRepo.findOne(entry.getKey());
            if (item == null) return "Item with id " + entry.getKey() + " doesn't exist";
            Logo logo = logoRepo.findOne(entry.getValue());
            if (logo == null) return "Logo with id " + entry.getKey() + " doesn't exist";
            item.setLogo(logo);
            item.setIsFavorite(true);
            itemRepo.saveAndFlush(item);
        }
        
        
        return "Succesfully added logos";
    }
    
    @RequestMapping(value = "/getFavorites", method = RequestMethod.GET)
    public List<Item> getAllFavorites()
    {
        return itemRepo.getAllFavorites(true);
    }
    
    
    @RequestMapping("/{id}")
    public @ResponseBody Item getItem(@PathVariable("id") String id)
    {
        Long longId = new Long(id);
        return itemRepo.findOne(longId);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody Item deleteItem(@PathVariable("id") String id)
    {
        Long longId = new Long(id);
        Item item = itemRepo.findOne(longId);
        if (item == null) throw new IllegalArgumentException("Item with id doesn't exist");
        itemRepo.delete(item);
        return item;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody Item updateItem(@PathVariable("id") String id, @RequestBody Item values)
    {
        Long longId = new Long(id);
        Item item = itemRepo.findOne(longId);
        item.setName(values.getName());
//        item.setPrice(new BigDecimal("" + values.getPrice()));
        item.setPrice(values.getPrice());
        item = itemRepo.saveAndFlush(item);
        return item;
    }
            
    
}

