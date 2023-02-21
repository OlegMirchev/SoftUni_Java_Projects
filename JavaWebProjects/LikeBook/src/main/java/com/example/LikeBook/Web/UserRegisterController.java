package com.example.LikeBook.Web;

import com.example.LikeBook.Model.dto.RegisterUserDto;
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
public class UserRegisterController {

    private UserService userService;
    private CurrentUser currentUser;

    @Autowired
    public UserRegisterController(UserService userService, CurrentUser currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @ModelAttribute("registerUserDto")
    public RegisterUserDto initModel() {
        return new RegisterUserDto();
    }

    @GetMapping("/register")
    public String getRegister() {

        if (this.currentUser.isLogged()) {
            return "redirect:/home";
        }

        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid RegisterUserDto registerUserDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (this.currentUser.isLogged()) {
            return "redirect:/home";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerUserDto", redirectAttributes);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerUserDto", bindingResult);

            return "redirect:/users/register";
        }

        this.userService.register(registerUserDto);

        return "redirect:/home";
    }
}
