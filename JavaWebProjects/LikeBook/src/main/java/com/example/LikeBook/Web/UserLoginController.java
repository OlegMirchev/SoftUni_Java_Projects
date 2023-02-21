package com.example.LikeBook.Web;

import com.example.LikeBook.Model.dto.LoginUserDto;
import com.example.LikeBook.Service.UserService;
import com.example.LikeBook.Utils.CurrentUser;
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

    @ModelAttribute("loginUserDto")
    public LoginUserDto initModel() {
        return new LoginUserDto();
    }

    @GetMapping("/login")
    public String getLogin() {

        if (this.currentUser.isLogged()) {
            return "redirect:/home";
        }

        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@Valid LoginUserDto loginUserDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (this.currentUser.isLogged()) {
            return "redirect:/home";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loginUserDto", loginUserDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginUserDto", bindingResult);

            return "redirect:/users/login";
        }

        if (!this.userService.login(loginUserDto)) {
            redirectAttributes.addFlashAttribute("loginUserDto", loginUserDto);
            redirectAttributes.addFlashAttribute("loginError", true);

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
