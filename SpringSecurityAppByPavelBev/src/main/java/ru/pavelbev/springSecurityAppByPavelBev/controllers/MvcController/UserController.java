package ru.pavelbev.springSecurityAppByPavelBev.controllers.MvcController;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pavelbev.springSecurityAppByPavelBev.models.User;
import ru.pavelbev.springSecurityAppByPavelBev.services.MyUserDetailsService;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final MyUserDetailsService myUserDetailsService;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public String showUserById(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = myUserDetailsService.findByName(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(userDetails.getUsername()));
            model.addAttribute("user", user);
            return "/user/userProfile";
    }
}
