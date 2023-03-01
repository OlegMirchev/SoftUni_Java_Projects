package com.resellerapp.Web;

import com.resellerapp.model.dto.UserLoginDto;
import com.resellerapp.service.UserService;
import com.resellerapp.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
        long id = this.currentUser.getId();

        if (id != 0) {
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

        long id = this.currentUser.getId();

        if (id != 0) {
            return "redirect:/home";
        }

        if (!this.userService.login(userLoginDto)) {
            redirectAttributes.addFlashAttribute("userLoginDto", userLoginDto);
            redirectAttributes.addFlashAttribute("incorrectUser", true);

            return "redirect:/users/login";
        }

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout() {
        this.userService.logout();

        return "index";
    }
}
