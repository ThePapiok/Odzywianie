package com.Odzywianie.contollers;

import com.Odzywianie.models.Kategorie;
import com.Odzywianie.modelsDTO.KategorieDTO;
import com.Odzywianie.services.KategorieService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/kategorie")
public class KategorieController {

    private final KategorieService kategorieService;

    public KategorieController(KategorieService kategorieService) {
        this.kategorieService = kategorieService;
    }

    @GetMapping
    public String getKategorie(Model model, KategorieDTO kategorieDTO, @RequestParam(defaultValue = "false") String deleted)
    {
        model.addAttribute("lista", kategorieService.getKategorieDTO());
        model.addAttribute("error","false");
        model.addAttribute("deleted",deleted);
        return "kategorie";
    }

    @PostMapping
    public String addKategorie(@Valid KategorieDTO kategorieDTO, BindingResult bindingResult, Model model) throws IOException {
        if(kategorieService.existsKategoriaByName(kategorieDTO.getName()))
        {
            model.addAttribute("lista", kategorieService.getKategorieDTO());
            model.addAttribute("error","true");
            model.addAttribute("deleted",false);
            return "kategorie";
        }
        if(bindingResult.hasErrors())
        {
            model.addAttribute("error","false");
            model.addAttribute("lista", kategorieService.getKategorieDTO());
            model.addAttribute("deleted",false);
            return "kategorie";
        }
        kategorieService.addKategorie(kategorieDTO);
        return "redirect:/kategorie";
    }

    @GetMapping("/{id}/delete")
    public String deleteKategorie(@PathVariable Long id)
    {
        if(kategorieService.existsKategoriaById(id)) {
            kategorieService.deleteKategorie(id);
            return "redirect:/kategorie?deleted=true";
        }
        else {
            return "redirect:/kategorie?deleted=error";
        }

    }

    @GetMapping("/{id}/edit")
    public String editKategorie(@PathVariable Long id, Model model, KategorieDTO kategorieDTO)
    {
        model.addAttribute("edited","false");
        if(kategorieService.existsKategoriaById(id)) {
            model.addAttribute("edit", "true");
            model.addAttribute("kategoria", kategorieService.getKategoriaDTOById(id));
            return "kategoriaedit";
        }
        else {
            model.addAttribute("edit", "false");
            return "kategoriaedit";
        }
    }

    @PostMapping("/{id}/edit")
    public String editedKategorie(@PathVariable Long id, @Valid KategorieDTO kategorieDTO, BindingResult bindingResult, Model model) throws IOException {
        model.addAttribute("edit", "true");
        model.addAttribute("kategoria", kategorieDTO);
        if(bindingResult.hasErrors())
        {
            model.addAttribute("edited","false");

        }
        else {
            kategorieService.editKategoria(kategorieDTO, id);
            model.addAttribute("edited","true");
        }
        return "kategoriaedit";


    }
}
