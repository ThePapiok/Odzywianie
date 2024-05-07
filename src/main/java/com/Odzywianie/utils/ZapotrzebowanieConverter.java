package com.Odzywianie.utils;

import com.Odzywianie.models.Zapotrzebowanie;
import com.Odzywianie.modelsDTO.ProduktyDTO;
import com.Odzywianie.modelsDTO.ZapotrzebowanieDTO;
import com.Odzywianie.services.ProduktyService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ZapotrzebowanieConverter {

    private final ProduktyService produktyService;

    public ZapotrzebowanieConverter(ProduktyService produktyService) {
        this.produktyService = produktyService;
    }

    public ZapotrzebowanieDTO createZapotrzebowanieDTO(Zapotrzebowanie zapotrzebowanie)
    {
        ZapotrzebowanieDTO zapotrzebowanieDTO = new ZapotrzebowanieDTO();
        zapotrzebowanieDTO.setId(zapotrzebowanie.getId());
        zapotrzebowanieDTO.setTime(String.valueOf(zapotrzebowanie.getTime()).substring(0,5));
        zapotrzebowanieDTO.setWeight(zapotrzebowanie.getWeight());
        zapotrzebowanieDTO.setZapotrzebowanieProdukty(produktyService.getProdukty(zapotrzebowanie.getZapotrzebowanieProdukty()));
        return zapotrzebowanieDTO;
    }

    public List<ZapotrzebowanieDTO> createZapotrzebowanieDTO(List<Zapotrzebowanie> zapotrzebowanies)
    {
        List<ZapotrzebowanieDTO> zapotrzebowanieDTOS = new ArrayList<>();
        zapotrzebowanies.forEach(e -> zapotrzebowanieDTOS.add(createZapotrzebowanieDTO(e)));
        return zapotrzebowanieDTOS;
    }
}
