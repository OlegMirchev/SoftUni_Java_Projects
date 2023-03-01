package com.resellerapp.Web;

import com.resellerapp.model.dto.UserRegisterDto;
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
public class UserRegisterController {

    private UserService userService;
    private CurrentUser currentUser;

    @Autowired
    public UserRegisterController(UserService userService, CurrentUser currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @ModelAttribute("userRegisterDto")
    public UserRegisterDto initModel() {
        return new UserRegisterDto();
    }

    @GetMapping("/register")
    public String getRegister() {
        long id = this.currentUser.getId();

        if (id != 0) {
            return "redirect:/home";
        }

        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid UserRegisterDto userRegisterDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterDto", userRegisterDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDto", bindingResult);

            return "redirect:/users/register";
        }

        long id = this.currentUser.getId();

        if (id != 0) {
            return "redirect:/home";
        }

        this.userService.register(userRegisterDto);

        return "redirect:/home";
    }
}
