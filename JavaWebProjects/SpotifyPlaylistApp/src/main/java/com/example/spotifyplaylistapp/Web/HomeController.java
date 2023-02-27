package com.example.spotifyplaylistapp.Web;

import com.example.spotifyplaylistapp.model.dto.SongDto;
import com.example.spotifyplaylistapp.model.entities.Song;
import com.example.spotifyplaylistapp.model.entities.User;
import com.example.spotifyplaylistapp.service.SongService;
import com.example.spotifyplaylistapp.service.UserService;
import com.example.spotifyplaylistapp.util.CurrentUser;
import com.example.spotifyplaylistapp.util.Enum.StyleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class HomeController {

    private SongService songService;
    private CurrentUser currentUser;
    private UserService userService;

    @Autowired
    public HomeController(CurrentUser currentUser, SongService songService, UserService userService) {
        this.songService = songService;
        this.currentUser = currentUser;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {

        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        long id = this.currentUser.getId();
        int totalDurationOfPlaylist = 0;

        List<SongDto> popSongs = this.songService.selectAllSongsWithStylePop(StyleType.POP);
        List<SongDto> rockSongs = this.songService.selectAllSongsWithStylePop(StyleType.ROCK);
        List<SongDto> jazzSongs = this.songService.selectAllSongsWithStylePop(StyleType.JAZZ);

        Optional<User> user = this.userService.selectAllPlayListWithId(id);
        Set<Song> playList = user.get().getPlayList();

        for (Song song : playList) {
            totalDurationOfPlaylist += song.getDuration();
        }

        model.addAttribute("popSongs", popSongs);
        model.addAttribute("rockSongs", rockSongs);
        model.addAttribute("jazzSongs", jazzSongs);
        model.addAttribute("userSongs", playList);
        model.addAttribute("totalDurationOfPlaylist", totalDurationOfPlaylist);

        return "home";
    }
}
