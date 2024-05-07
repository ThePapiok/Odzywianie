package com.Odzywianie.ControllerTests;

import com.Odzywianie.contollers.KategorieController;
import com.Odzywianie.modelsDTO.KategorieDTO;
import com.Odzywianie.services.KategorieService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KategorieController.class)
public class KategorieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KategorieService kategorieService;


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnViewWhenGetKategorie() throws Exception {
        List<KategorieDTO> kategorieDTOS = List.of(new KategorieDTO(0l, "owoce"), new KategorieDTO(1l, "warzywa"));
        Mockito.when(kategorieService.getKategorieDTO()).thenReturn(kategorieDTOS);
        this.mockMvc.perform(get("/kategorie")).andExpect(status().isOk())
                .andExpect(view().name("kategorie")).andExpect(model().attribute("lista", kategorieDTOS))
                .andExpect(model().attribute("error", "false")).andExpect(model().attributeExists("deleted"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldRedirectWhenPostKategorie() throws Exception {
        Mockito.when(kategorieService.existsKategoriaByName("owoce")).thenReturn(false);
        this.mockMvc.perform(post("/kategorie").with(csrf())).andExpect(redirectedUrl("/kategorie"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldRedirectWhenDeleteKategorie() throws Exception {
        Mockito.when(kategorieService.existsKategoriaById(0l)).thenReturn(true);
        this.mockMvc.perform(get("/kategorie/0/delete").with(csrf())).andExpect(redirectedUrl("/kategorie?deleted=true"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnViewWhenEditKategorie() throws Exception {
        KategorieDTO kategorieDTO = new KategorieDTO(0l, "owoce");
        Mockito.when(kategorieService.existsKategoriaById(0l)).thenReturn(true);
        Mockito.when(kategorieService.getKategoriaDTOById(0l)).thenReturn(kategorieDTO);
        this.mockMvc.perform(get("/kategorie/0/edit")).andExpect(status().isOk())
                .andExpect(view().name("kategoriaedit")).andExpect(model().attribute("edit", "true")).andExpect(model().attribute("edited", "false"))
                .andExpect(model().attribute("kategoria", kategorieDTO));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnViewWhenEditPostKategorie() throws Exception {
        this.mockMvc.perform(post("/kategorie/0/edit").with(csrf())).andExpect(status().isOk())
                .andExpect(view().name("kategoriaedit")).andExpect(model().attribute("edited", "true")).andExpect(model().attribute("edit", "true")).andExpect(model().attributeExists("kategoria"));
    }




}
