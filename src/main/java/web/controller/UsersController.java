package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller
@Primary
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String printUsers(ModelMap model) {
        model.addAttribute("users", userService.listUsers());
        return "users";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping()
    public String newUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long userId) {
        userService.delete(userId);
        return "redirect:/users";
    }

    @GetMapping("/change")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        User user;
        if (userService.findUserById(id).isPresent()) {
            user = userService.findUserById(id).get();
        } else {
            return "cantFind";
        }
        model.addAttribute("user", user);
        return "change";
    }

    @PostMapping("/change")
    public String changeUser(@RequestParam("id") int id,
                             @RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("email") String email) {
        userService.change(id, firstName, lastName, email);
        return "redirect:/users"; // Перенаправляем на страницу с пользователями после изменения данных
    }

}