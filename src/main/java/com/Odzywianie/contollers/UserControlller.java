package com.Odzywianie.contollers;

import com.Odzywianie.Validators.ChangePasswordGroup;
import com.Odzywianie.Validators.CreatingUserGroup;
import com.Odzywianie.Validators.EditingInformationsGroup;
import com.Odzywianie.models.Uzytkownik;
import com.Odzywianie.modelsDTO.UzytkownikDTO;
import com.Odzywianie.repositories.UzytkownikRepository;
import com.Odzywianie.services.UzytkownikService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserControlller {

    private final UzytkownikService uzytkownikService;
    private final UzytkownikRepository uzytkownikRepository;
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();


    public UserControlller(UzytkownikService uzytkownikService, UzytkownikRepository uzytkownikRepository) {
        this.uzytkownikService = uzytkownikService;
        this.uzytkownikRepository = uzytkownikRepository;
    }

    @RequestMapping("/login")
    public String login(@RequestParam(defaultValue = "0") String error, Model model, @RequestParam(defaultValue = "false") String changed, @RequestParam(defaultValue = "false") String deleted )
    {
        model.addAttribute("error",error);
        model.addAttribute("passwordupdate",changed);
        model.addAttribute("deleteaccount",deleted);
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(UzytkownikDTO uzytkownikDTO, @RequestParam(defaultValue = "0") String error, Model model)
    {
        model.addAttribute("error",error);
        return "register";
    }

    @PostMapping("/register")
    public String addUser(@Validated(CreatingUserGroup.class)  UzytkownikDTO uzytkownikDTO, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "register";
        }
        Uzytkownik uzytkownik = uzytkownikService.getUzytkownik(uzytkownikDTO);
        if (uzytkownikService.isUzytkownik(uzytkownik.getName()) == null) {
            uzytkownikRepository.save(uzytkownik);
            return "redirect:/login?error=false";
        } else {
            return "redirect:/register?error=true";
        }
    }
    @RequestMapping("/logout")
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response)
    {
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/login";
    }

    @GetMapping("/konto")
    public String konto(UzytkownikDTO uzytkownikDTO, Model model) {
        uzytkownikDTO = uzytkownikService.getUzytkownikDTOByAuthentication();
        model.addAttribute("user", uzytkownikDTO);
        model.addAttribute("informations","true");
        return "konto";
    }

    @GetMapping("/konto/{tryb}")
    public String konto(UzytkownikDTO uzytkownikDTO, Model model, @PathVariable(required = false) String tryb) {
        uzytkownikDTO = uzytkownikService.getUzytkownikDTOByAuthentication();
        model.addAttribute("user", uzytkownikDTO);
        if (tryb.equals("editInformations")){
            model.addAttribute("edit", "true");
        }
        if (tryb.equals("editPassword")){
            model.addAttribute("passwordupdate", "true");
        }
        if (tryb.equals("deleteAccount")){
            model.addAttribute("delete","true");
            model.addAttribute("informations","true");
        }
        return "konto";
    }

    @PostMapping("/konto/editInformations")
    public String putUserInformation(@Validated(EditingInformationsGroup.class) UzytkownikDTO uzytkownikDTO, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("user", uzytkownikDTO);
            model.addAttribute("edit","true");
            return "konto";
        }
        uzytkownikService.updateUzytkownikInformations(uzytkownikDTO);
        return "redirect:/konto";

    }

    @PostMapping("/konto/editPassword")
    public String editPassword(@Validated(ChangePasswordGroup.class) UzytkownikDTO uzytkownikDTO, BindingResult bindingResult, Model model, @RequestParam(required = false) String oldpassword, Authentication authentication, HttpServletRequest request, HttpServletResponse response)
    {


        if(!uzytkownikService.matchesPassword(oldpassword))
        {
            model.addAttribute("passwordupdate","true");
            model.addAttribute("error","true");
            return "konto";
        }
        if(uzytkownikDTO.getPassword().equals(oldpassword))
        {
            model.addAttribute("passwordupdate","true");
            model.addAttribute("same","true");
            return "konto";
        }
        uzytkownikService.updatePassword(uzytkownikDTO);
        model.addAttribute("passwordupdate","true");
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/login?changed=true";
    }

    @PostMapping("/konto/deleteAccount")
    public String deleteAccount(Model model, Authentication authentication, HttpServletRequest request, HttpServletResponse response)
    {
        uzytkownikService.deleteAccount();
        model.addAttribute("deleteaccount","true");
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/login?deleted=true";
    }

}
