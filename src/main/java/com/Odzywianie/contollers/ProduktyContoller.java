package com.Odzywianie.contollers;

import com.Odzywianie.models.Produkty;
import com.Odzywianie.modelsDTO.ProduktyDTO;
import com.Odzywianie.modelsDTO.ZapotrzebowanieDTO;
import com.Odzywianie.services.KategorieService;
import com.Odzywianie.services.ProduktyService;
import com.Odzywianie.services.ZapotrzebowanieService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/produkty")
public class ProduktyContoller {

    private final ProduktyService produktyService;
    private final ZapotrzebowanieService zapotrzebowanieService;
    private final KategorieService kategorieService;

    public ProduktyContoller(ProduktyService produktyService, ZapotrzebowanieService zapotrzebowanieService, KategorieService kategorieService) {
        this.produktyService = produktyService;
        this.zapotrzebowanieService = zapotrzebowanieService;
        this.kategorieService = kategorieService;
    }

    @GetMapping("/dodaj")
    public String addProdukt(ProduktyDTO produktyDTO, Model model)
    {
        model.addAttribute("lista", kategorieService.getNamesOfKategorie());
        return "dodajProdukt";
    }

    @PostMapping("/dodaj")
    public String dodanyProdukt(@Valid ProduktyDTO produktyDTO, Model model, BindingResult bindingResult) throws IOException {

        if(bindingResult.hasErrors()) {
            model.addAttribute("lista", kategorieService.getNamesOfKategorie());
            return "dodajProdukt";
        }
        else {
            produktyService.addProdukt(produktyDTO);
            return "redirect:/produkty/lista";
        }
    }


    @GetMapping("/lista")
    public String listaProduktow(Model model, @RequestParam(defaultValue = "") String text,  @RequestParam(defaultValue = "name") String sort, @RequestParam(defaultValue = "ascending") String type, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "-") String kategoria, @RequestParam(defaultValue = "false") String valid)
    {

        Sort sorted = null;
        if(type.equals("ascending"))
        {
            sorted = Sort.by(sort).ascending();
        }
        else{
            sorted = Sort.by(sort).descending();
        }
        Pageable pageable = PageRequest.of(page,size,sorted);
        Page<Produkty> produkty = null;
        if(text.length()==0) {
            produkty = produktyService.getProdukty(pageable, kategoria);
        }
        else{
            produkty = produktyService.getProduktyStartsWith(pageable, text, kategoria);
        }
        List<String> names = new ArrayList<>();
        List<String> pagesBefore = new ArrayList<>();
        List<String> pagesAfter = new ArrayList<>();
        if(produkty.getContent().size()!=0) {
            pagesBefore = produktyService.getPagesBefore(produkty);
            pagesAfter = produktyService.getPagesAfter(produkty);
        }
        names.add("-");
        names.addAll(kategorieService.getNamesOfKategorie());
        if(pagesBefore.size()==0)
        {
            model.addAttribute("left", "false");
        }
        else{
            if(pagesBefore.get(0).equals("..."))
            {
                model.addAttribute("left", "true");
                pagesBefore.remove(0);
            }
            else
            {
                model.addAttribute("left", "false");
            }
        }
        if(pagesAfter.size()==0)
        {
            model.addAttribute("right", "false");

        }
        else{

            if(pagesAfter.get(pagesAfter.size()-1).equals("...")) {
                model.addAttribute("right", "true");
                pagesAfter.remove(pagesAfter.size()-1);
            }
            else {
                model.addAttribute("right", "false");
            }
        }
        model.addAttribute("kategorie", names);
        model.addAttribute("paramtext", text);
        model.addAttribute("paramsort", sort);
        model.addAttribute("paramtype", type);
        model.addAttribute("paramsize", size);
        model.addAttribute("parampage", page);
        model.addAttribute("paramvalid", valid);
        model.addAttribute("paramkategoria", kategoria);
        model.addAttribute("pagesafter", pagesAfter);
        model.addAttribute("pagesbefore", pagesBefore);
        model.addAttribute("time", String.valueOf(LocalTime.now()).substring(0,5));
        model.addAttribute("lista", produktyService.getProdukty(produkty.getContent()));
        return "listaProduktow";
    }

    @GetMapping("{id}")
    public String specificProdukt(Model model, @PathVariable Long id)
    {
        ProduktyDTO produktyDTO=produktyService.getProduktDTOById(id);
        model.addAttribute("time", String.valueOf(LocalTime.now()).substring(0,5));
        if(produktyDTO==null)
        {
            model.addAttribute("error","true");
        }
        else
        {
            model.addAttribute("produkt", produktyDTO);
        }
        return "produkt";
    }

    @PostMapping("{id}")
    public String addToZapotrzebowanie(@Valid ZapotrzebowanieDTO zapotrzebowanieDTO, Model model, BindingResult bindingResult, @PathVariable Long id, @RequestParam float weight, @RequestParam LocalTime time) {
        if (bindingResult.hasErrors())
        {
            return "redirect:/produkty/lista?valid=true";
        }
        else {
            zapotrzebowanieService.addZapotrzebowanie(weight, time, id);
            return "redirect:/produkty/lista";
        }
    }

    @GetMapping("{id}/delete")
    public String deleteProdukt(@PathVariable Long id)
    {
        if(produktyService.existsProduktById(id)) {
            produktyService.deleteProdukt(id);
            return "redirect:/produkty/lista";
        }
        else {
            return "redirect:/produkty/lista";
        }
    }

    @GetMapping("{id}/edit")
    public String editProdukt(@PathVariable Long id, Model model, ProduktyDTO produktyDTO)
    {
        ProduktyDTO produkty=produktyService.getProduktDTOById(id);
        if(produkty==null)
        {
            model.addAttribute("error","true");
        }
        else
        {
            model.addAttribute("produkt", produkty);
        }
        model.addAttribute("lista", kategorieService.getNamesOfKategorie());
        return "produktedit";

    }

    @PostMapping("{id}/edit")
    public String editedProdukt(@PathVariable Long id, Model model, @Valid ProduktyDTO produktyDTO, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors())
        {
            ProduktyDTO produkty=produktyService.getProduktDTOById(id);
            if(produkty==null)
            {
                model.addAttribute("error","true");
            }
            else
            {
                model.addAttribute("produkt", produkty);
            }
            model.addAttribute("lista", kategorieService.getNamesOfKategorie());
            return "produktedit";
        }
        else {
            produktyService.editProdukt(produktyDTO, id);
            return "redirect:/produkty/lista";
        }

    }

}
