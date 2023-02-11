package com.example.BattleShips.Web;

import com.example.BattleShips.Models.dto.BattleDto;
import com.example.BattleShips.Models.dto.ShipDto;
import com.example.BattleShips.Service.ShipService;
import com.example.BattleShips.Utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {

    private ShipService shipService;
    private CurrentUser currentUser;

    @Autowired
    public HomeController(ShipService shipService, CurrentUser currentUser) {
        this.shipService = shipService;
        this.currentUser = currentUser;
    }

    @ModelAttribute("battleDto")
    public BattleDto initModel() {
        return new BattleDto();
    }

    @GetMapping("/")
    public String getIndex() {
        Long id = this.currentUser.getId();

        if (id != null) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String getHome(Model model) {
        Long id = this.currentUser.getId();

        if (id == null) {
            return "redirect:/";
        }

        List<ShipDto> userShip = this.shipService.selectShipsOwnedBy(id);
        List<ShipDto> otherShip = this.shipService.selectShipsOtherBy(id);
        List<ShipDto> sortedShips = this.shipService.getSortedAllShips();


        model.addAttribute("userShip", userShip);
        model.addAttribute("otherShip", otherShip);
        model.addAttribute("sortedShips", sortedShips);

        return "/home";
    }
}
