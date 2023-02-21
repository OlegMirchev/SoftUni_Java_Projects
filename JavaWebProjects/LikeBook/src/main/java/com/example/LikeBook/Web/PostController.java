package com.example.LikeBook.Web;

import com.example.LikeBook.Model.dto.AddPostsDto;
import com.example.LikeBook.Service.PostService;
import com.example.LikeBook.Utils.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posts")
public class PostController {

    private PostService postService;
    private CurrentUser currentUser;

    @Autowired
    public PostController(PostService postService, CurrentUser currentUser) {
        this.postService = postService;
        this.currentUser = currentUser;
    }

    @ModelAttribute("addPostsDto")
    public AddPostsDto initModel() {
        return new AddPostsDto();
    }

    @GetMapping("/add")
    public String getAddPosts() {

        if (!this.currentUser.isLogged()) {
            return "redirect:/";
        }

        return "post-add";
    }

    @PostMapping("/add")
    public String postAddPosts(@Valid AddPostsDto addPostsDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (!this.currentUser.isLogged()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addPostsDto", addPostsDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addPostsDto", bindingResult);

            return "redirect:/ships/add";
        }

        this.postService.create(addPostsDto);

        return "redirect:/home";
    }

    @GetMapping("/like/{id}")
    public String likePosts(@PathVariable long id) {
        long userId = this.currentUser.getId();

        if (!this.currentUser.isLogged()) {
            return "redirect:/";
        }

        this.postService.likePostsOnOtherUserWithId(id, userId);

        return "redirect:/home";
    }

    @GetMapping("/remove/{id}")
    public String deletePost(@PathVariable long id) {

        if (!this.currentUser.isLogged()) {
            return "redirect:/";
        }

        this.postService.removePostWithId(id);

        return "redirect:/home";
    }
}
