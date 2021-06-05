package com.example.demo.user;

import com.example.demo.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    UserService userService;
    MailService mailService;

    @Autowired
    public UserController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping("/registernewuser")
    public RedirectView register(@ModelAttribute User user, HttpServletRequest request){

       user.setUserIp(request.getRemoteAddr());

       String key = Confirm.generateConfirmationKey();

       user.setConfirmationKey(key);

       userService.add(user);
       mailService.sendEmail(user.getEmail(), "Confirmation key", key);

       return new RedirectView("/register/sendkey/" + userService.findByIp(user.getUserIp()).getId());
    }

    @GetMapping("/register/sendkey/{id}")
    public String confirm(@PathVariable Long id, Model model){
        model.addAttribute("confirm", new Confirm());
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "confirm";
    }

    @PostMapping("/register/sendkey/confirm")
    public RedirectView confirmDone(@ModelAttribute Confirm confirm, User user){

        if (confirm.getKey().equals(userService.findByIp(user.getUserIp()).getConfirmationKey())){
           userService.confirmAccount(user.getUserIp());
        }else {
            System.out.println("wrong code");
        }
        return new RedirectView("/home");
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());

        return "login";
    }

    @PostMapping("/loggedin")
    @ResponseBody
    public String loggedin(@ModelAttribute User user){

        if (userService.findByEmail(user.getEmail()).getPassword().equals(user.getPassword())){
            user = userService.findByEmail(user.getEmail());
            return user.toString();
        }
        return "wrong password or email";
    }

}








