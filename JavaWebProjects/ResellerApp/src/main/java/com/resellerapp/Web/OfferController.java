package com.resellerapp.Web;

import com.resellerapp.model.dto.AddOfferDto;
import com.resellerapp.service.OfferService;
import com.resellerapp.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private OfferService offerService;
    private CurrentUser currentUser;

    @Autowired
    public OfferController(OfferService offerService, CurrentUser currentUser) {
        this.offerService = offerService;
        this.currentUser = currentUser;
    }

    @ModelAttribute("addOfferDto")
    public AddOfferDto initModel() {
        return new AddOfferDto();
    }

    @GetMapping("/add")
    public String getOffer() {
        long id = this.currentUser.getId();

        if (id == 0) {
            return "redirect:/";
        }

        return "offer-add";
    }

    @PostMapping("/add")
    public String postOffer(@Valid AddOfferDto addOfferDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addOfferDto", addOfferDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addOfferDto", bindingResult);

            return "redirect:/offers/add";
        }

        long id = this.currentUser.getId();

        if (id == 0) {
            return "redirect:/";
        }

        this.offerService.addOffer(addOfferDto);

        return "redirect:/home";
    }

    @GetMapping("/buy/{id}")
    public String buyOffer(@PathVariable long id) {
        long userId = this.currentUser.getId();

        if (userId == 0) {
            return "redirect:/";
        }

        this.offerService.buyOffer(id, userId);

        return "redirect:/home";
    }

    @GetMapping("/remove/{id}")
    public String removeOffer(@PathVariable long id) {
        long userId = this.currentUser.getId();

        if (userId == 0) {
            return "redirect:/";
        }

        this.offerService.removeOffer(id);

        return "redirect:/home";
    }
}
