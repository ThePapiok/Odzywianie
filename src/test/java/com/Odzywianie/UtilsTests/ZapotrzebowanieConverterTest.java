package com.Odzywianie.UtilsTests;

import com.Odzywianie.models.Produkty;
import com.Odzywianie.models.Zapotrzebowanie;
import com.Odzywianie.modelsDTO.ProduktyDTO;
import com.Odzywianie.modelsDTO.ZapotrzebowanieDTO;
import com.Odzywianie.services.ProduktyService;
import com.Odzywianie.utils.ZapotrzebowanieConverter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.List;

public class ZapotrzebowanieConverterTest {

    private ZapotrzebowanieConverter zapotrzebowanieConverter;
    @Mock
    private ProduktyService produktyService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        zapotrzebowanieConverter = new ZapotrzebowanieConverter(produktyService);
    }

    @Test
    public void shouldTrueWhenCreateZapotrzebowanieDTO(){
        List<Produkty> produkties = List.of(new Produkty(0l), new Produkty(1l));
        List<ProduktyDTO> produktiesDTO = List.of(new ProduktyDTO(0l), new ProduktyDTO(1l));
        Zapotrzebowanie zapotrzebowanie = new Zapotrzebowanie();
        zapotrzebowanie.setId(0L);
        zapotrzebowanie.setTime(LocalTime.of(20, 25));
        zapotrzebowanie.setWeight(30);
        zapotrzebowanie.setZapotrzebowanieProdukty(produkties);
        Mockito.when(produktyService.getProdukty(produkties)).thenReturn(produktiesDTO);
        ZapotrzebowanieDTO zapotrzebowanieDTO = zapotrzebowanieConverter.createZapotrzebowanieDTO(zapotrzebowanie);
        Assertions.assertEquals(zapotrzebowanieDTO.getId(), zapotrzebowanie.getId());
        Assertions.assertEquals(zapotrzebowanieDTO.getWeight(), zapotrzebowanie.getWeight());
        Assertions.assertEquals(zapotrzebowanieDTO.getTime(), String.valueOf(zapotrzebowanie.getTime()).substring(0,5));
        Assertions.assertEquals(zapotrzebowanieDTO.getZapotrzebowanieProdukty().get(0).getId(), zapotrzebowanie.getZapotrzebowanieProdukty().get(0).getId());
        Assertions.assertEquals(zapotrzebowanieDTO.getZapotrzebowanieProdukty().get(1).getId(), zapotrzebowanie.getZapotrzebowanieProdukty().get(1).getId());
    }
}
