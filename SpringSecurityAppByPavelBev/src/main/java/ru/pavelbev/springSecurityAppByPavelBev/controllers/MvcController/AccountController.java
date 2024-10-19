package ru.pavelbev.springSecurityAppByPavelBev.controllers.MvcController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.pavelbev.springSecurityAppByPavelBev.models.User;
import ru.pavelbev.springSecurityAppByPavelBev.models.UserDTO;
import ru.pavelbev.springSecurityAppByPavelBev.repositories.UserRepository;

import java.util.Date;
import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/register")
    public String register(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute(userDTO);
        model.addAttribute("success", false);
        return "/registration/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserDTO userDTO, Model model, BindingResult bindingResult) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            bindingResult.addError(new FieldError("userDTO", "confirmPassword",
                    "Password and Confirm Password do not match"));
        }

        Optional<User> user = repository.findByName(userDTO.getName());
        if (user.isPresent()) {
            bindingResult.addError(new FieldError("userDTO", "name",
                    "Name is already used"));
        }

        if (bindingResult.hasErrors()) {
            // Удалить атрибут success, если есть ошибки в форме
            model.addAttribute("success", null);
            return "registration/register";
        }

        try {
            var bCryptEncoder = new BCryptPasswordEncoder();

            User newUser = new User();
            newUser.setName(userDTO.getName());
            newUser.setAge(Integer.valueOf(userDTO.getAge()));
            newUser.setRoles(getUserRole()); // Устанавливаем роль
            newUser.setCreatedAt(new Date());
            newUser.setPassword(bCryptEncoder.encode(userDTO.getPassword()));

            repository.save(newUser);

            model.addAttribute("userDTO", new UserDTO()); // Обновляем форму
            model.addAttribute("success", true);
        } catch (Exception ex) {
            bindingResult.addError(new FieldError("userDTO", "name", ex.getMessage()));
            model.addAttribute("success", null);
        }

        return "registration/register";
    }

    private String getUserRole() {
        // Проверка на наличие пользователей для определения роли
        if (repository.count() == 0) { // Если это первый пользователь
            return "ROLE_ADMIN";
        }
        return "ROLE_USER"; // Все остальные пользователи
    }
}
