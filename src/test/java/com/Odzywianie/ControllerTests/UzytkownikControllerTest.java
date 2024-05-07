package com.Odzywianie.ControllerTests;

import com.Odzywianie.configs.SecurityConfig;
import com.Odzywianie.contollers.UserControlller;
import com.Odzywianie.enums.Gender;
import com.Odzywianie.models.Uzytkownik;
import com.Odzywianie.modelsDTO.UzytkownikDTO;
import com.Odzywianie.repositories.UzytkownikRepository;
import com.Odzywianie.services.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserControlller.class)
@Import(SecurityConfig.class)
public class UzytkownikControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UzytkownikService uzytkownikService;
    @MockBean
    private UzytkownikRepository uzytkownikRepository;
    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private BindingResult bindingResult;

    @Test
    @WithAnonymousUser
    public void shouldReturnViewWhenLogin() throws Exception {
        this.mockMvc.perform(get("/login").with(csrf()).param("error", "0").param("changed", "false").param("deleted", "false"))
                .andExpect(model().attribute("error", "0"))
                .andExpect(model().attribute("passwordupdate", "false"))
                .andExpect(model().attribute("deleteaccount", "false"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithAnonymousUser
    public void shouldReturnViewWhenRegisterGet() throws Exception {
        this.mockMvc.perform(get("/register").param("error", "0")).andExpect(model().attribute("error", "0")).andExpect(status().isOk()).andExpect(view().name("register"));
    }

    @Test
    @WithAnonymousUser
    public void shouldRedirectWhenRegisterPost() throws Exception {
        UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
        uzytkownikDTO.setName("Testtttt");
        uzytkownikDTO.setPassword("Test123!");
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setName("Testtttt");
        uzytkownik.setPassword("Test123!");
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Mockito.when(uzytkownikService.getUzytkownik(uzytkownikDTO)).thenReturn(uzytkownik);
        Mockito.when(uzytkownikService.isUzytkownik("Test")).thenReturn(null);
        this.mockMvc.perform(post("/register").with(csrf()).flashAttr("uzytkownikDTO", uzytkownikDTO)).andExpect(redirectedUrl("/login?error=false"));
    }


    @Test
    @WithMockUser
    public void shouldRedirectWhenLogout() throws Exception {
        this.mockMvc.perform(get("/logout")).andExpect(redirectedUrl("/login?logout=true"));
    }

    @Test
    @WithMockUser
    public void shouldReturnViewWhenKontoGet() throws Exception{
        UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
        uzytkownikDTO.setGender(Gender.WOMAN);
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        this.mockMvc.perform(get("/konto").flashAttr("uzytkownikDTO", uzytkownikDTO)).andExpect(model().attribute("user", uzytkownikDTO)).andExpect(model().attribute("informations", "true")).andExpect(status().isOk()).andExpect(view().name("konto"));
    }

    @Test
    @WithMockUser
    public void shouldReturnViewWhenEditInformationsGet() throws Exception{
        UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
        uzytkownikDTO.setGender(Gender.WOMAN);
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        this.mockMvc.perform(get("/konto/editInformations").flashAttr("uzytkownikDTO", uzytkownikDTO)).andExpect(model().attribute("edit", "true")).andExpect(model().attribute("user", uzytkownikDTO)).andExpect(status().isOk()).andExpect(view().name("konto"));
    }

    @Test
    @WithMockUser
    public void shouldReturnViewWhenEditPasswordGet() throws Exception{
        UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
        uzytkownikDTO.setGender(Gender.WOMAN);
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        this.mockMvc.perform(get("/konto/editPassword").flashAttr("uzytkownikDTO", uzytkownikDTO)).andExpect(model().attribute("passwordupdate", "true")).andExpect(model().attribute("user", uzytkownikDTO)).andExpect(status().isOk()).andExpect(view().name("konto"));
    }

    @Test
    @WithMockUser
    public void shouldReturnViewWhenDeleteAccountGet() throws Exception{
        UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
        uzytkownikDTO.setGender(Gender.WOMAN);
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        this.mockMvc.perform(get("/konto/deleteAccount").flashAttr("uzytkownikDTO", uzytkownikDTO)).andExpect(model().attribute("delete", "true")).andExpect(model().attribute("informations", "true")).andExpect(model().attribute("user", uzytkownikDTO)).andExpect(status().isOk()).andExpect(view().name("konto"));
    }

    @Test
    @WithMockUser
    public void shouldReturnViewWhenEditInformationsPost() throws Exception{
        UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
        uzytkownikDTO.setGender(Gender.WOMAN);
        uzytkownikDTO.setName("Testtttt");
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        this.mockMvc.perform(post("/konto/editInformations").with(csrf()).flashAttr("uzytkownikDTO", uzytkownikDTO)).andExpect(redirectedUrl("/konto"));
    }

    @Test
    @WithMockUser
    public void shouldReturnViewWhenEditPasswordPost() throws Exception{
        UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
        uzytkownikDTO.setGender(Gender.WOMAN);
        uzytkownikDTO.setPassword("Test123!");
        String oldpassword = "Testt123!";
        Mockito.when(uzytkownikService.matchesPassword(oldpassword)).thenReturn(true);
        this.mockMvc.perform(post("/konto/editPassword").with(csrf()).param("oldpassword", oldpassword).flashAttr("uzytkownikDTO", uzytkownikDTO)).andExpect(redirectedUrl("/login?changed=true"));
    }

    @Test
    @WithMockUser
    public void shouldReturnViewWhenDeleteAccountPost() throws Exception{
        this.mockMvc.perform(post("/konto/deleteAccount").with(csrf())).andExpect(redirectedUrl("/login?deleted=true"));
    }

}
