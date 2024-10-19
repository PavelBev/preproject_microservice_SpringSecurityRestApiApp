package ru.pavelbev.springSecurityAppByPavelBev.controllers.RestApiController;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pavelbev.springSecurityAppByPavelBev.models.User;
import ru.pavelbev.springSecurityAppByPavelBev.services.MyUserDetailsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest-api")
@AllArgsConstructor
public class RestAdminController {
    private final MyUserDetailsService myUserDetailsService;

    //Показать всех пользователей
    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = myUserDetailsService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //Сохранить изменения пользователя
    @PostMapping("/create-user")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        User savedUser = myUserDetailsService.saveUserRest(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    //Показать информацию о пользователе
    @GetMapping("/userProfile/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<User> showUserById(@PathVariable Long id) {
        Optional<User> userOptional = Optional.ofNullable(myUserDetailsService.getUserById(id));
        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Сохранить изменения пользователя
    @PatchMapping("/update-user/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user,
                                           BindingResult bindingResult) {
        user.setId(id);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        User updateUser = myUserDetailsService.saveUserRest(user);
        return ResponseEntity.ok(updateUser);
    }

    //Удалить пользователя
    @DeleteMapping("/delete-user/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        myUserDetailsService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
