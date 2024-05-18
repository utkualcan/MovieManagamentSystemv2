package org.kurgu.moviemanagamentsystemv2.Controller;

import org.kurgu.moviemanagamentsystemv2.Model.User;
import org.kurgu.moviemanagamentsystemv2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    private UserRepository ur;

    @GetMapping("/user")
    public String getUsers(Model model){
        Iterable<User> users = ur.findAll();
        model.addAttribute("users", users);
        return "user/index";
    }
}
