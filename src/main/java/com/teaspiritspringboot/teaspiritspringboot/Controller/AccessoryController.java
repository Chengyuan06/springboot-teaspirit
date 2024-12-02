package com.teaspiritspringboot.teaspiritspringboot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teaspiritspringboot.teaspiritspringboot.model.Accessory;
import com.teaspiritspringboot.teaspiritspringboot.repository.AccessoryRepository;

@Controller
@RequestMapping("/teaspirit/accessory")
public class AccessoryController {

    @Autowired private AccessoryRepository accessoryRepository;
    @GetMapping()
        public String geAllAccessories(Model model, Pageable pageable){
            Page<Accessory> accessories = accessoryRepository.findAll(pageable);
            if(!accessories.isEmpty()){
                model.addAttribute("accessories", accessories); 
            } else {
                model.addAttribute("accessories", null); 
        } return "accessoryList";
         
        }
    

    @GetMapping("/filter")
    public String getAccessoriesFiltered(@RequestParam("category") String category, Model model, Pageable pageable){
        System.out.println("Category received: " + category);
        Page<Accessory> accessories = accessoryRepository.findByCategory(category, pageable);
        System.out.println("Accessories found: " + accessories.getContent());
        if(!accessories.isEmpty()){
            model.addAttribute("accessories", accessories);
        } else {
            model.addAttribute("accessories", null);
        }
        return "accessoryList";
        
    }


}
