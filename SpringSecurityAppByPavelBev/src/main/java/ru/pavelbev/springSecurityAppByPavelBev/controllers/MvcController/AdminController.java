package ru.pavelbev.springSecurityAppByPavelBev.controllers.MvcController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pavelbev.springSecurityAppByPavelBev.models.User;
import ru.pavelbev.springSecurityAppByPavelBev.services.MyUserDetailsService;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final MyUserDetailsService myUserDetailsService;

    //Показать всех пользователей
    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String showAllUsers(Model model) {
        List<User> users = myUserDetailsService.getAllUsers();
        model.addAttribute("users", users);
        return "/admin/users";
    }

    //Показать форму редактирования пользователя
    @GetMapping("/create-user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String showCreateUserForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", new User());
        return "/admin/create-user";
    }

    //Сохранить изменения пользователя
    @PostMapping("/create-user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/admin/create-user";
        }
        myUserDetailsService.saveUser(user);
        return "redirect:/admin";
    }

    //Показать информацию о пользователе
    @GetMapping("/userProfile/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public String showUserById(@PathVariable Long id, Model model) {
        Optional<User> userOptional = Optional.ofNullable(myUserDetailsService.getUserById(id));
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "/user/userProfile";
        }
        return "redirect:/admin";
    }

    //Показать форму для редактирования данных пользователя
    @GetMapping("/update-user/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public String showUpdateUserForm(@PathVariable Long id, Model model) {
        User user = myUserDetailsService.getUserById(id);
        model.addAttribute("user", user);
        return "/admin/update-user";
    }

    //Сохранить изменения пользователя
    @PostMapping("/update-user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/update-user";
        }
        myUserDetailsService.updateUser(user);
        return "redirect:/admin";
    }

    //Удалить пользователя
    @GetMapping("delete-user/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteUserById(@PathVariable Long id) {
        myUserDetailsService.deleteUserById(id);
        return "redirect:/admin";
    }
}
