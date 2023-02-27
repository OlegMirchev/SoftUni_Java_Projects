package com.example.spotifyplaylistapp.Web;

import com.example.spotifyplaylistapp.model.dto.AddSongDto;
import com.example.spotifyplaylistapp.service.SongService;
import com.example.spotifyplaylistapp.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/songs")
public class SongController {

    private SongService songService;
    private CurrentUser currentUser;

    @Autowired
    public SongController(SongService songService, CurrentUser currentUser) {
        this.songService = songService;
        this.currentUser = currentUser;
    }

    @ModelAttribute("addSongDto")
    public AddSongDto initModel() {
        return new AddSongDto();
    }

    @GetMapping("/add")
    public String getSong() {

        return "song-add";
    }

    @PostMapping("/add")
    public String postSong(@Valid AddSongDto addSongDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addSongDto", addSongDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addSongDto", bindingResult);

            return "redirect:/songs/add";
        }

        this.songService.add(addSongDto);

        return "redirect:/home";
    }

    @GetMapping("/add-song/{id}")
    public String addPlayList(@PathVariable long id) {
        long userId = this.currentUser.getId();

        this.songService.addSongWithId(id, userId);

        return "redirect:/home";
    }

    @GetMapping("/remove-all")
    public String removeAllPlayList() {
        long userId = this.currentUser.getId();

        this.songService.removeAllSongsAtPlayListUserWithId(userId);

        return "redirect:/home";
    }
}
