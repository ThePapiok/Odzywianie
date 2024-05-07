package com.Odzywianie.contollers;

import com.Odzywianie.modelsDTO.ZapotrzebowanieDTO;
import com.Odzywianie.services.ProduktyService;
import com.Odzywianie.services.ZapotrzebowanieService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/zapotrzebowanie")
public class ZapotrzebowanieController {

    private final ZapotrzebowanieService zapotrzebowanieService;
    private final ProduktyService produktyService;

    public ZapotrzebowanieController(ZapotrzebowanieService zapotrzebowanieService, ProduktyService produktyService) {
        this.zapotrzebowanieService = zapotrzebowanieService;
        this.produktyService = produktyService;
    }

    @GetMapping
    public String zapotrzebowanie(Model model, @RequestParam(defaultValue = "false") String valid)
    {
        float kcal = zapotrzebowanieService.calculateKcal();
        List <Float> fat = zapotrzebowanieService.calculateFat(kcal);
        List <Float> satuaratedFat = zapotrzebowanieService.calculateSaturatedFat(kcal);
        List <Float> cholesterol = zapotrzebowanieService.calculateCholesterol();
        List <Float> sodium = zapotrzebowanieService.calculateSodium();
        List <Float> potasium = zapotrzebowanieService.calculatePotasium();
        List <Float> carbohydrates = zapotrzebowanieService.calculateCarbohydrates(kcal);
        List <Float> fiber = zapotrzebowanieService.calculateFiber(kcal);
        List <Float> sugars = zapotrzebowanieService.calculateSugars(kcal);
        List <Float> protein = zapotrzebowanieService.calculateProteins();
        List <Float> ascorbicAcid = zapotrzebowanieService.calculateAscorbicAcid();
        List <Float> iron = zapotrzebowanieService.calculateIron();
        List <Float> vitaminB6 = zapotrzebowanieService.calculateVitaminB6();
        float magnesium = zapotrzebowanieService.calculateMagnesium();
        List <Float> calcium = zapotrzebowanieService.calculateCalcium();
        List <Float> vitaminD = zapotrzebowanieService.calculateVitaminD();
        float vitaminB12 = zapotrzebowanieService.calculateVitaminB12();
        List <Float> nutrein = zapotrzebowanieService.getNutreins();
        model.addAttribute("minkcal", kcal);
        model.addAttribute("kcal", nutrein.get(0));
        model.addAttribute("minfat", fat.get(0));
        model.addAttribute("maxfat", fat.get(1));
        model.addAttribute("fat", nutrein.get(1));
        model.addAttribute("minsaturatedfat", satuaratedFat.get(0));
        model.addAttribute("maxsaturatedfat", satuaratedFat.get(1));
        model.addAttribute("saturatedfat", nutrein.get(2));
        model.addAttribute("mincholesterol", cholesterol.get(0));
        model.addAttribute("maxcholesterol", cholesterol.get(1));
        model.addAttribute("cholesterol", nutrein.get(3));
        model.addAttribute("minsodium", sodium.get(0));
        model.addAttribute("maxsodium", sodium.get(1));
        model.addAttribute("sodium", nutrein.get(4));
        model.addAttribute("minpotasium", potasium.get(0));
        model.addAttribute("maxpotasium", potasium.get(1));
        model.addAttribute("potasium", nutrein.get(5));
        model.addAttribute("mincarbohydrates", carbohydrates.get(0));
        model.addAttribute("maxcarbohydrates", carbohydrates.get(1));
        model.addAttribute("carbohydrates", nutrein.get(6));
        model.addAttribute("minfiber", fiber.get(0));
        model.addAttribute("maxfiber", fiber.get(1));
        model.addAttribute("fiber", nutrein.get(7));
        model.addAttribute("minsugars", sugars.get(0));
        model.addAttribute("maxsugars", sugars.get(1));
        model.addAttribute("sugars", nutrein.get(8));
        model.addAttribute("minprotein", protein.get(0));
        model.addAttribute("maxprotein", protein.get(1));
        model.addAttribute("protein", nutrein.get(9));
        model.addAttribute("minascorbicacid", ascorbicAcid.get(0));
        model.addAttribute("maxascorbicacid", ascorbicAcid.get(1));
        model.addAttribute("ascorbicacid", nutrein.get(10));
        model.addAttribute("miniron", iron.get(0));
        model.addAttribute("maxiron", iron.get(1));
        model.addAttribute("iron", nutrein.get(11));
        model.addAttribute("minvitaminb6", vitaminB6.get(0));
        model.addAttribute("maxvitaminb6", vitaminB6.get(1));
        model.addAttribute("vitaminb6", nutrein.get(12));
        model.addAttribute("minmagnesium", magnesium);
        model.addAttribute("magnesium", nutrein.get(13));
        model.addAttribute("mincalcium", calcium.get(0));
        model.addAttribute("maxcalcium", calcium.get(1));
        model.addAttribute("calcium", nutrein.get(14));
        model.addAttribute("minvitamind", vitaminD.get(0));
        model.addAttribute("maxvitamind", vitaminD.get(1));
        model.addAttribute("vitamind", nutrein.get(15));
        model.addAttribute("minvitaminb12", vitaminB12);
        model.addAttribute("vitaminb12", nutrein.get(16));
        model.addAttribute("lista", zapotrzebowanieService.getZapotrzebowanieDTO());
        model.addAttribute("paramvalid", valid);
        return "zapotrzebowanie";
    }

    @GetMapping("{id}/delete")
    public String zapotrzebowanieDelete(@PathVariable Long id)
    {
        if(zapotrzebowanieService.existsZapotrzebowanieById(id)) {
            zapotrzebowanieService.deleteZapotrzebowanie(id);
        }
        return "redirect:/zapotrzebowanie";
    }

    @PostMapping("{id}")
    public String zapotrzebowanieEdit(@Valid ZapotrzebowanieDTO zapotrzebowanieDTO, BindingResult bindingResult, @PathVariable Long id, @RequestParam float weight, @RequestParam LocalTime time) {
        if (bindingResult.hasErrors())
        {
            return "redirect:/zapotrzebowanie?valid=true";
        }
        else {
            zapotrzebowanieService.editZapotrzebowanie(id, weight, time);
            return "redirect:/zapotrzebowanie";
        }
    }

    @GetMapping("{id}")
    public String redirectZapotrzebowanie(@PathVariable Long id)
    {
        String link = "redirect:/produkty/" + zapotrzebowanieService.getIdProduktByIdZapotrzebowanie(id);
        return link;
    }


}
