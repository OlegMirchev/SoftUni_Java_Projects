package com.example.BattleShips.Web;

import com.example.BattleShips.Models.dto.BattleDto;
import com.example.BattleShips.Service.BattleService;
import com.example.BattleShips.Utils.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BattleController {

    private BattleService battleService;
    private CurrentUser currentUser;

    @Autowired
    public BattleController(BattleService battleService, CurrentUser currentUser) {
        this.battleService = battleService;
        this.currentUser = currentUser;
    }

    @PostMapping("/battle")
    public String battle(@Valid BattleDto battleDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !this.battleService.fightShips(battleDto)) {
            redirectAttributes.addFlashAttribute("battleDto", battleDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.battleDto", bindingResult);

            return "redirect:/home";
        }

        Long id = this.currentUser.getId();

        if (id == null) {
            return "redirect:/";
        }

        this.battleService.fightShips(battleDto);

        return "redirect:/home";
    }
}
