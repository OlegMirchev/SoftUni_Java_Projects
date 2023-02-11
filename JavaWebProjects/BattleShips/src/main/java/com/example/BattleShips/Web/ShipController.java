package com.example.BattleShips.Web;

import com.example.BattleShips.Models.dto.AddShipDto;
import com.example.BattleShips.Service.ShipService;
import com.example.BattleShips.Utils.CurrentUser;
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
@RequestMapping("/ships")
public class ShipController {

    private ShipService shipService;
    private CurrentUser currentUser;

    @Autowired
    public ShipController(ShipService shipService, CurrentUser currentUser) {
        this.shipService = shipService;
        this.currentUser = currentUser;
    }

    @ModelAttribute("addShipDto")
    public AddShipDto initModel() {
        return new AddShipDto();
    }

    @GetMapping("/add")
    public String getAddShips() {
        Long id = this.currentUser.getId();

        if (id == null) {
            return "redirect:/";
        }

        return "ship-add";
    }

    @PostMapping("/add")
    public String postAddShips(@Valid AddShipDto addShipDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addShipDto", addShipDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addShipDto", bindingResult);

            return "redirect:/ships/add";
        }

        Long id = this.currentUser.getId();

        if (id == null) {
            return "redirect:/";
        }

        this.shipService.addShip(addShipDto);

        return "redirect:/home";
    }
}
