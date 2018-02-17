package be.livingsmart.eindwerk.configuration;

import be.livingsmart.eindwerk.repository.ItemJpaRepository;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements ApplicationRunner {

    //@Autowired
    //private ItemJpaRepository itemRepo;

    public void run(ApplicationArguments args) {
        // add stuff to the itemrepo here
    }
}
