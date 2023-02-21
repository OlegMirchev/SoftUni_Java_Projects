package com.example.LikeBook.Web;

import com.example.LikeBook.Model.dto.PostOtherDto;
import com.example.LikeBook.Model.dto.PostUserDto;
import com.example.LikeBook.Model.entities.User;
import com.example.LikeBook.Service.PostService;
import com.example.LikeBook.Service.UserService;
import com.example.LikeBook.Utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private UserService userService;
    private PostService postService;
    private CurrentUser currentUser;

    @Autowired
    public HomeController(UserService userService, PostService postService, CurrentUser currentUser) {
        this.userService = userService;
        this.postService = postService;
        this.currentUser = currentUser;
    }

    @GetMapping("/")
    public String index() {

        if (this.currentUser.isLogged()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String getHome(Model model) {
        long id = this.currentUser.getId();

        if (!this.currentUser.isLogged()) {
            return "redirect:/";
        }

        Optional<User> userOpt = this.userService.selectCurrentUserBy(id);
        List<PostUserDto> userPosts = this.postService.selectUserByIdAndFindTheirAllPosts(id);
        List<PostOtherDto> otherPosts = this.postService.selectOtherAndFindTheirAllPosts(id);

        model.addAttribute("currentUser", userOpt.get());
        model.addAttribute("userPosts", userPosts);
        model.addAttribute("otherPosts", otherPosts);

        return "home";
    }
}
