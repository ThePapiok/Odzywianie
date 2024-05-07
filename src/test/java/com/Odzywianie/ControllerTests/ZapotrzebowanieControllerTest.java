package com.Odzywianie.ControllerTests;

import com.Odzywianie.configs.SecurityConfig;
import com.Odzywianie.contollers.ZapotrzebowanieController;
import com.Odzywianie.models.Zapotrzebowanie;
import com.Odzywianie.modelsDTO.ZapotrzebowanieDTO;
import com.Odzywianie.services.CustomUserDetailsService;
import com.Odzywianie.services.ProduktyService;
import com.Odzywianie.services.ZapotrzebowanieService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalTime;
import java.util.List;

@WebMvcTest(ZapotrzebowanieController.class)
@Import(SecurityConfig.class)
public class ZapotrzebowanieControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ZapotrzebowanieService zapotrzebowanieService;
    @MockBean
    private ProduktyService produktyService;
    @MockBean
    private CustomUserDetailsService userDetailsService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private BindingResult bindingResult;

    @Test
    @WithMockUser
    public void shouldReturnViewWhenZapotrzebowanieGet() throws Exception
    {
        List<ZapotrzebowanieDTO> zapotrzebowanieDTOS = List.of(new ZapotrzebowanieDTO(), new ZapotrzebowanieDTO());
        float kcal = 2000;
        List<Float> fat = List.of(5f, 10f);
        List<Float> saturatedFat = List.of(5f, 10f);
        List<Float> cholesterol = List.of(5f, 10f);
        List<Float> sodium = List.of(5f, 10f);
        List<Float> potasium = List.of(5f, 10f);
        List<Float> carbohydrates = List.of(5f, 10f);
        List<Float> fiber = List.of(5f, 10f);
        List<Float> sugars = List.of(5f, 10f);
        List<Float> protein = List.of(5f, 10f);
        List<Float> ascorbicAcid = List.of(5f, 10f);
        List<Float> iron = List.of(5f, 10f);
        List<Float> vitaminB6 = List.of(5f, 10f);
        float magnesium = 10;
        List<Float> calcium = List.of(5f, 10f);
        List<Float> vitaminD = List.of(5f, 10f);
        float vitaminB12 = 10;
        List<Float> nutrein = List.of(0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f, 12f, 13f, 14f, 15f, 16f);
        Mockito.when(zapotrzebowanieService.calculateKcal()).thenReturn(kcal);
        Mockito.when(zapotrzebowanieService.calculateFat(kcal)).thenReturn(fat);
        Mockito.when(zapotrzebowanieService.calculateSaturatedFat(kcal)).thenReturn(saturatedFat);
        Mockito.when(zapotrzebowanieService.calculateCholesterol()).thenReturn(cholesterol);
        Mockito.when(zapotrzebowanieService.calculateSodium()).thenReturn(sodium);
        Mockito.when(zapotrzebowanieService.calculatePotasium()).thenReturn(potasium);
        Mockito.when(zapotrzebowanieService.calculateCarbohydrates(kcal)).thenReturn(carbohydrates);
        Mockito.when(zapotrzebowanieService.calculateFiber(kcal)).thenReturn(fiber);
        Mockito.when(zapotrzebowanieService.calculateSugars(kcal)).thenReturn(sugars);
        Mockito.when(zapotrzebowanieService.calculateProteins()).thenReturn(protein);
        Mockito.when(zapotrzebowanieService.calculateAscorbicAcid()).thenReturn(ascorbicAcid);
        Mockito.when(zapotrzebowanieService.calculateIron()).thenReturn(iron);
        Mockito.when(zapotrzebowanieService.calculateVitaminB6()).thenReturn(vitaminB6);
        Mockito.when(zapotrzebowanieService.calculateMagnesium()).thenReturn(magnesium);
        Mockito.when(zapotrzebowanieService.calculateCalcium()).thenReturn(calcium);
        Mockito.when(zapotrzebowanieService.calculateVitaminD()).thenReturn(vitaminD);
        Mockito.when(zapotrzebowanieService.calculateVitaminB12()).thenReturn(vitaminB12);
        Mockito.when(zapotrzebowanieService.getNutreins()).thenReturn(nutrein);
        Mockito.when(zapotrzebowanieService.getZapotrzebowanieDTO()).thenReturn(zapotrzebowanieDTOS);
        this.mockMvc.perform(get("/zapotrzebowanie").param("valid", "false")).andExpect(model().attribute("minkcal", kcal))
                        .andExpect(model().attribute("kcal", nutrein.get(0))).andExpect(model().attribute("minfat", fat.get(0)))
                        .andExpect(model().attribute("maxfat", fat.get(1))).andExpect(model().attribute("fat", nutrein.get(1)))
                        .andExpect(model().attribute("minsaturatedfat", saturatedFat.get(0))).andExpect(model().attribute("maxsaturatedfat", saturatedFat.get(1)))
                        .andExpect(model().attribute("saturatedfat", nutrein.get(2))).andExpect(model().attribute("mincholesterol", cholesterol.get(0)))
                        .andExpect(model().attribute("maxcholesterol", cholesterol.get(1))).andExpect(model().attribute("cholesterol", nutrein.get(3)))
                        .andExpect(model().attribute("minsodium", sodium.get(0))).andExpect(model().attribute("maxsodium", sodium.get(1)))
                        .andExpect(model().attribute("sodium", nutrein.get(4))).andExpect(model().attribute("minpotasium", potasium.get(0)))
                        .andExpect(model().attribute("maxpotasium", potasium.get(1))).andExpect(model().attribute("potasium", nutrein.get(5)))
                        .andExpect(model().attribute("mincarbohydrates", carbohydrates.get(0))).andExpect(model().attribute("maxcarbohydrates", carbohydrates.get(1)))
                        .andExpect(model().attribute("carbohydrates", nutrein.get(6))).andExpect(model().attribute("minfiber", fiber.get(0)))
                        .andExpect(model().attribute("maxfiber", fiber.get(1))).andExpect(model().attribute("fiber", nutrein.get(7)))
                        .andExpect(model().attribute("minsugars", sugars.get(0))).andExpect(model().attribute("maxsugars", sugars.get(1)))
                        .andExpect(model().attribute("sugars", nutrein.get(8))).andExpect(model().attribute("minprotein", protein.get(0)))
                        .andExpect(model().attribute("maxprotein", protein.get(1))).andExpect(model().attribute("protein", nutrein.get(9)))
                        .andExpect(model().attribute("minascorbicacid", ascorbicAcid.get(0))).andExpect(model().attribute("maxascorbicacid", ascorbicAcid.get(1)))
                        .andExpect(model().attribute("ascorbicacid", nutrein.get(10))).andExpect(model().attribute("miniron", iron.get(0)))
                        .andExpect(model().attribute("maxiron", iron.get(1))).andExpect(model().attribute("iron", nutrein.get(11)))
                        .andExpect(model().attribute("minvitaminb6", vitaminB6.get(0))).andExpect(model().attribute("maxvitaminb6", vitaminB6.get(1)))
                        .andExpect(model().attribute("vitaminb6", nutrein.get(12))).andExpect(model().attribute("minmagnesium", magnesium))
                        .andExpect(model().attribute("magnesium", nutrein.get(13))).andExpect(model().attribute("mincalcium", calcium.get(0)))
                        .andExpect(model().attribute("maxcalcium", calcium.get(1))).andExpect(model().attribute("calcium", nutrein.get(14)))
                        .andExpect(model().attribute("minvitamind", vitaminD.get(0))).andExpect(model().attribute("maxvitamind", vitaminD.get(1)))
                        .andExpect(model().attribute("vitamind", nutrein.get(15))).andExpect(model().attribute("minvitaminb12", vitaminB12))
                        .andExpect(model().attribute("vitaminb12", nutrein.get(16))).andExpect(model().attribute("lista", zapotrzebowanieDTOS))
                        .andExpect(model().attribute("paramvalid", "false")).andExpect(status().isOk()).andExpect(view().name("zapotrzebowanie"));



    }

    @Test
    @WithMockUser
    public void shouldRedirectWhenDelete() throws Exception{
        Mockito.when(zapotrzebowanieService.existsZapotrzebowanieById(0l)).thenReturn(true);
        this.mockMvc.perform(get("/zapotrzebowanie/0/delete")).andExpect(redirectedUrl("/zapotrzebowanie"));
    }

    @Test
    @WithMockUser
    public void shouldRedirectWhenEdit() throws Exception{
        ZapotrzebowanieDTO zapotrzebowanieDTO = new ZapotrzebowanieDTO();
        zapotrzebowanieDTO.setWeight(30);
        zapotrzebowanieDTO.setTime("10:20");
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        this.mockMvc.perform(post("/zapotrzebowanie/0").with(csrf()).param("weight", "30").param("time", "10:20").flashAttr("zapotrzebowanieDTO", zapotrzebowanieDTO)).andExpect(redirectedUrl("/zapotrzebowanie"));

    }

    @Test
    @WithMockUser
    public void shouldRedirect() throws Exception{
        Mockito.when(zapotrzebowanieService.getIdProduktByIdZapotrzebowanie(0l)).thenReturn(0l);
        this.mockMvc.perform(get("/zapotrzebowanie/0")).andExpect(redirectedUrl("/produkty/0"));
    }

}
