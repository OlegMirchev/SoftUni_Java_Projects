package com.resellerapp.Web;

import com.resellerapp.model.dto.OfferOtherDto;
import com.resellerapp.model.dto.OfferUserDto;
import com.resellerapp.model.entities.Offer;
import com.resellerapp.model.entities.User;
import com.resellerapp.service.OfferService;
import com.resellerapp.service.UserService;
import com.resellerapp.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class HomeController {

    private UserService userService;
    private OfferService offerService;
    private CurrentUser currentUser;

    @Autowired
    public HomeController(UserService userService, OfferService offerService, CurrentUser currentUser) {
        this.userService = userService;
        this.offerService = offerService;
        this.currentUser = currentUser;
    }

    @GetMapping("/")
    public String index() {
        long id = this.currentUser.getId();

        if (id != 0) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        long id = this.currentUser.getId();

        if (id == 0) {
            return "redirect:/";
        }

        Optional<User> loggedUser = this.userService.selectCurrentUserWithId(id);
        List<OfferUserDto> userOffers = this.offerService.selectAllOfferWithUserId(id);
        List<OfferOtherDto> otherOffers = this.offerService.selectAllOfferWithOtherUserIdNot(id);

        Optional<User> user = this.userService.selectBoughtOffersWithUserId(id);
        Set<Offer> boughtOffers = user.get().getBoughtOffers();

        model.addAttribute("loggedUser", loggedUser.get());
        model.addAttribute("userOffers", userOffers);
        model.addAttribute("otherOffers", otherOffers);
        model.addAttribute("boughtOffers", boughtOffers);

        return "home";
    }
}
