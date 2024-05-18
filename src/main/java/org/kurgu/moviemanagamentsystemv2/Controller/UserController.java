package org.kurgu.moviemanagamentsystemv2.Controller;

import org.kurgu.moviemanagamentsystemv2.Model.User;
import org.kurgu.moviemanagamentsystemv2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public String getUsers(Model model) {
        if (hasAdminRole()) {
            Iterable<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            return "user/index";
        } else {
            return "redirect:/access-denied";
        }
    }

    private boolean hasAdminRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
