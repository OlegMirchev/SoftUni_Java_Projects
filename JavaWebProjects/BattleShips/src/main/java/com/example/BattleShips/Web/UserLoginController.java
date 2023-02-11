package com.example.BattleShips.Web;

import com.example.BattleShips.Models.dto.UserLoginDto;
import com.example.BattleShips.Service.UserService;
import com.example.BattleShips.Utils.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    private UserService userService;
    private CurrentUser currentUser;

    @Autowired
    public UserLoginController(UserService userService, CurrentUser currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @ModelAttribute("userLoginDto")
    public UserLoginDto initModel() {
        return new UserLoginDto();
    }

    @GetMapping("/login")
    public String getLogin() {
        Long id = this.currentUser.getId();

        if (id != null) {
            return "redirect:/home";
        }

        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@Valid UserLoginDto userLoginDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginDto", userLoginDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginDto", bindingResult);

            return "redirect:/users/login";
        }

        Long id = this.currentUser.getId();

        if (id != null) {
            return "redirect:/home";
        }

        if (!this.userService.login(userLoginDto)) {
            redirectAttributes.addFlashAttribute("validLogin", true);

            return "redirect:/users/login";
        }

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout() {
        this.userService.logout();

        return "redirect:/";
    }
}
