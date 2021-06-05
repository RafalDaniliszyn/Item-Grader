package com.example.demo.items;

import com.example.demo.IdModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Controller
public class ItemsController {

    ItemsService itemsService;

    @Autowired
    public ItemsController(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @PostMapping("/search")
    public String search(@ModelAttribute Search search, Model model){
        Optional<Items> item = itemsService.getItems(search.name);
        model.addAttribute("result",item);

        if (itemsService.exist(search.getName())){
            model.addAttribute("idModel", new IdModel(item.get().id));
            return "result";
        }
        return "Strona-1";
    }


    @PostMapping("/add")
    public RedirectView added(@ModelAttribute Items newItem, Model model){
        model.addAttribute("newItem", newItem);
        model.addAttribute("fromSearch", new Search());
        itemsService.add(newItem);

        return new RedirectView("/home");
    }


    @PostMapping("/rateup")
    public String rateUp(@ModelAttribute IdModel idModel, HttpServletRequest request, Model model){

        Search search = new Search();
        search.name = itemsService.getById(idModel.getId()).getName();

        model.addAttribute("fromSearch", search);

        String clientIp = request.getRemoteAddr();

        itemsService.rateUp(idModel.getId(), clientIp);
        return "rateAdded";
    }

    @PostMapping("/ratedown")
    public String rateDown(@ModelAttribute IdModel idModel, HttpServletRequest request, Model model){

        Search search = new Search();
        search.name = itemsService.getById(idModel.getId()).getName();

        model.addAttribute("fromSearch", search);

        String clientIp = request.getRemoteAddr();

        itemsService.rateDown(idModel.getId(), clientIp);
        return "rateAdded";
    }

}








