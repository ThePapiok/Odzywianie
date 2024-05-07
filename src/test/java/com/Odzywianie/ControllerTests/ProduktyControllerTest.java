package com.Odzywianie.ControllerTests;

import com.Odzywianie.contollers.ProduktyContoller;
import com.Odzywianie.models.Produkty;
import com.Odzywianie.modelsDTO.ProduktyDTO;
import com.Odzywianie.modelsDTO.ZapotrzebowanieDTO;
import com.Odzywianie.services.KategorieService;
import com.Odzywianie.services.ProduktyService;
import com.Odzywianie.services.ZapotrzebowanieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProduktyContoller.class)
public class ProduktyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProduktyService produktyService;

    @MockBean
    private KategorieService kategorieService;

    @MockBean
    private ZapotrzebowanieService zapotrzebowanieService;

    @MockBean
    private BindingResult bindingResult;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnViewWhenAdd() throws Exception {
        List<String> names = List.of("owoce", "warzywa");
        Mockito.when(kategorieService.getNamesOfKategorie()).thenReturn(names);
        this.mockMvc.perform(get("/produkty/dodaj")).andExpect(status().isOk()).andExpect(view().name("dodajProdukt")).andExpect(model().attribute("lista", names));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnViewWhenAddPost() throws Exception {

        ProduktyDTO produktyDTO = new ProduktyDTO();
        produktyDTO.setKategoria("owoce");
        produktyDTO.setName("banan");
        produktyDTO.setCalcium(1);
        produktyDTO.setFat(1);
        produktyDTO.setIron(1);
        produktyDTO.setFiber(1);
        produktyDTO.setAscorbicAcid(1);
        produktyDTO.setAverageWeight(1);
        produktyDTO.setCarbohydrates(1);
        produktyDTO.setCholesterol(1);
        produktyDTO.setMagnesium(1);
        produktyDTO.setKcal(1);
        produktyDTO.setPotassium(1);
        produktyDTO.setProtein(1);
        produktyDTO.setSaturatedFat(1);
        produktyDTO.setSodium(1);
        produktyDTO.setSugars(1);
        produktyDTO.setVitaminB6(1);
        produktyDTO.setVitaminB12(1);
        produktyDTO.setVitaminD(1);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        this.mockMvc.perform(post("/produkty/dodaj").flashAttr("produktyDTO", produktyDTO).with(csrf())).andExpect(redirectedUrl("/produkty/lista"));
    }




    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnViewWhenGet() throws Exception {
        List<Produkty> produkties = List.of(new Produkty(0L), new Produkty(1L));
        Page<Produkty> produkty = new Page<Produkty>() {
            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 2;
            }

            @Override
            public <U> Page<U> map(Function<? super Produkty, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 2;
            }

            @Override
            public List<Produkty> getContent() {
                return produkties;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Produkty> iterator() {
                return null;
            }
        };
        List<String> names = List.of("owoce", "warzywa");
        List<String> pagesAfter = List.of("1", "2");
        List<String> pagesBefore = List.of("1", "2");
        List<ProduktyDTO> produktiesDTO = List.of(new ProduktyDTO(0L), new ProduktyDTO(1L));
        Mockito.when(produktyService.getProdukty(Mockito.any(),Mockito.any())).thenReturn(produkty);
        Mockito.when(kategorieService.getNamesOfKategorie()).thenReturn(names);
        Mockito.when(produktyService.getProdukty(produkties)).thenReturn(produktiesDTO);
        this.mockMvc.perform(get("/produkty/lista")).andExpect(model().attribute("lista", produktiesDTO)).andExpect(model().attribute("time", String.valueOf(LocalTime.now()).substring(0,5))).andExpect(model().attributeExists("paramtext", "paramsort", "paramtype", "paramsize", "parampage", "paramvalid", "paramkategoria", "left", "right")).andExpect(status().isOk()).andExpect(view().name("listaProduktow"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnViewWhenGetSpecific() throws Exception {
        ProduktyDTO produktyDTO = new ProduktyDTO(0L);

        Mockito.when(produktyService.getProduktDTOById(0L)).thenReturn(produktyDTO);
        this.mockMvc.perform(get("/produkty/0")).andExpect(status().isOk()).andExpect(view().name("produkt")).andExpect(model().attribute("time", String.valueOf(LocalTime.now()).substring(0,5)));
    }





    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldRedirectWhenPostZapotrzebowanie() throws Exception {
        ZapotrzebowanieDTO zapotrzebowanieDTO = new ZapotrzebowanieDTO();
        zapotrzebowanieDTO.setTime("10:30");
        zapotrzebowanieDTO.setWeight(1);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        this.mockMvc.perform(post("/produkty/0").param("id", "0").param("weight", "1").param("time","10:30").flashAttr("zapotrzebowanieDTO" , zapotrzebowanieDTO).with(csrf())).andExpect(redirectedUrl("/produkty/lista"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldRedirectWhenDelete() throws Exception {

        Mockito.when(produktyService.existsProduktById(0L)).thenReturn(true);
        this.mockMvc.perform(get("/produkty/0/delete").with(csrf())).andExpect(redirectedUrl("/produkty/lista"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnViewWhenEditGet() throws Exception {
        List<String> names = List.of("owoce", "warzywa");
        ProduktyDTO produktyDTO = new ProduktyDTO();
        produktyDTO.setName("Banan");
        produktyDTO.setCalcium(1);
        produktyDTO.setFat(1);
        produktyDTO.setIron(1);
        produktyDTO.setFiber(1);
        produktyDTO.setAscorbicAcid(1);
        produktyDTO.setAverageWeight(1);
        produktyDTO.setCarbohydrates(1);
        produktyDTO.setCholesterol(1);
        produktyDTO.setMagnesium(1);
        produktyDTO.setKcal(1);
        produktyDTO.setPotassium(1);
        produktyDTO.setProtein(1);
        produktyDTO.setSaturatedFat(1);
        produktyDTO.setSodium(1);
        produktyDTO.setSugars(1);
        produktyDTO.setVitaminB6(1);
        produktyDTO.setVitaminB12(1);
        produktyDTO.setVitaminD(1);
        produktyDTO.setKategoria("owoce");
        Mockito.when(produktyService.getProduktDTOById(0L)).thenReturn(produktyDTO);
        Mockito.when(kategorieService.getNamesOfKategorie()).thenReturn(names);
        this.mockMvc.perform(get("/produkty/0/edit").param("id", "0")).andExpect(model().attribute("produkt", produktyDTO)).andExpect(model().attribute("lista", names)).andExpect(status().isOk()).andExpect(view().name("produktedit"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldRedirectWhenEditPost() throws Exception {
        ProduktyDTO produktyDTO = new ProduktyDTO();
        produktyDTO.setName("Banan");
        produktyDTO.setCalcium(1);
        produktyDTO.setFat(1);
        produktyDTO.setIron(1);
        produktyDTO.setFiber(1);
        produktyDTO.setAscorbicAcid(1);
        produktyDTO.setAverageWeight(1);
        produktyDTO.setCarbohydrates(1);
        produktyDTO.setCholesterol(1);
        produktyDTO.setMagnesium(1);
        produktyDTO.setKcal(1);
        produktyDTO.setPotassium(1);
        produktyDTO.setProtein(1);
        produktyDTO.setSaturatedFat(1);
        produktyDTO.setSodium(1);
        produktyDTO.setSugars(1);
        produktyDTO.setVitaminB6(1);
        produktyDTO.setVitaminB12(1);
        produktyDTO.setVitaminD(1);
        produktyDTO.setKategoria("owoce");
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        this.mockMvc.perform(post("/produkty/0/edit").with(csrf()).param("id","0").flashAttr("produktyDTO", produktyDTO)).andExpect(redirectedUrl("/produkty/lista"));
    }











}
