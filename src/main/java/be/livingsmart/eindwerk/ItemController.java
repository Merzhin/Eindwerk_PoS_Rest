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

/**
 *  This class is a {@link RestController} for mostly everything related to {@link Item}s
 * @author Pieter
 */
@RestController
@RequestMapping("/item")
public class ItemController {
    
    @Autowired
    private ItemJpaRepository itemRepo;
    
    @Autowired
    private LogoJpaRepository logoRepo;
    
    /**
     *  After calling /item, with RequestMethod = GET, this function returns a {@link List} of all {@link Item}s
     * @return  {@link List} of {@link Item}s  
     */
    @RequestMapping(method=RequestMethod.GET)
    public List<Item> getItems(){
        return itemRepo.findAll();
    }
    
    /**
     *  After calling the test method /addfreebeer, a new {@link Item} will be added and saved to the JpaRepository
     * @return  {@link List} of all {@link Item}s
     */
    @RequestMapping("/addfreebeer") // this method is here for testing
    public List<Item> addFreeBeer(){
        Item item = new Item();
        item.setName("Free beer!");
        item.setPrice(0);
        
        
        itemRepo.saveAndFlush(item);
        return itemRepo.findAll();
    }
    
    /**
     *  After calling /item, RequestMethod = POST, this function will make a new {@link Item} with the values specified in the RequestBody and add it to the repository 
     * @param values    {@link Item}: Parameters: "name" ({@link String}), "price" ({@link Double}), "isFavorite" ({@link Boolean}).   
     * @return The added {@link Item} 
     */
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
    
    /**
     *  After calling /item/setFavorites, RequestMethod = POST, this function will set the isFavorite {@link Boolean} of all {@link Item}s to false, then it will add all given {@link Logo}s to the {@link Item}s whose id correspond with the logoIds 
     * @param values    {@link Map} of ItemIds ({@link Long}) and LogoIds ({@link Long})    
     * @return  String whether or not the favorites have been set correctly
     */
    @RequestMapping(value = "/setFavorites", method = RequestMethod.POST)
    public String addLogosToItems(@RequestBody Map<Long, Long> values) 
    {
        List<Item> items = itemRepo.findAll();
        for (Item item : items)
        {
            item.setIsFavorite(false);
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
    
    /**
     *  After calling /item/getFavorites, RequestMethod = GET. Returns all {@link Item}s whose isFavorite {@link Boolean} is set to true  
     * @return  {@link List} of {@link Item}s  
     */
    @RequestMapping(value = "/getFavorites", method = RequestMethod.GET)
    public List<Item> getAllFavorites()
    {
        return itemRepo.getAllFavorites(true);
    }
    
    /**
     *  After calling /item/{id}, RequestMethod = GET. Returns the {@link Item} with {id} 
     * @param id    {@link Long} itemId
     * @return  {@link Item} 
     */
    @RequestMapping("/{id}")
    public @ResponseBody Item getItem(@PathVariable("id") String id)
    {
        Long longId = new Long(id);
        return itemRepo.findOne(longId);
    }
    
    /**
     *  After calling /item/{id}, RequestMethod = DELETE. Deletes and returns the {@link Item} with {id} 
     * @param id    {@link Long}
     * @return  {@link Item}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody Item deleteItem(@PathVariable("id") String id)
    {
        Long longId = new Long(id);
        Item item = itemRepo.findOne(longId);
        if (item == null) throw new IllegalArgumentException("Item with id doesn't exist");
        itemRepo.delete(item);
        return item;
    }
    
    /**
     *  After calling /item/{id}, RequestMethod = PUT. Updates and returns the {@link Item} with {id} 
     * @param id   {@link Long} itemId 
     * @param values    {@link Item}: Parameters: "name" ({@link String}), "price" ({@link Double}), "isFavorite" ({@link Boolean}). 
     * @return  The updated {@link Item}
     */
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

