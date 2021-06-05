package com.example.demo;

import com.example.demo.items.Items;
import com.example.demo.items.ItemsService;
import com.example.demo.items.Search;
import com.example.demo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class HomeController {

    ItemsService itemsService;

    @Autowired
    public HomeController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request){
        model.addAttribute("fromSearch", new Search());
        Items item = itemsService.getBest();
        model.addAttribute("best", item);
        System.out.println(request.getRemoteAddr());
        return "Home";
    }

    @GetMapping("/addnew")
    public String add(Model model){
        model.addAttribute("newItem", new Items());
        return "add";
    }


    @GetMapping("/registernew")
    public String register(Model model){

        model.addAttribute("fromUser", new User());

        return "register";
    }


}
