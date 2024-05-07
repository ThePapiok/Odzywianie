package com.Odzywianie.utils;

import com.Odzywianie.models.Kategorie;
import com.Odzywianie.modelsDTO.KategorieDTO;
import com.Odzywianie.modelsDTO.ProduktyDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class KategorieConverter {

    public KategorieDTO createKategorieDTO(Kategorie kategorie)
    {
        KategorieDTO kategorieDTO = new KategorieDTO();
        kategorieDTO.setId(kategorie.getId());
        kategorieDTO.setName(kategorie.getName());
        kategorieDTO.setImage(Base64.getEncoder().encodeToString((kategorie.getImage())));
        return kategorieDTO;
    }

    public Kategorie createKategorie(KategorieDTO kategorieDTO) throws IOException {
        Kategorie kategorie = new Kategorie();
        kategorie.setName(kategorieDTO.getName());
        kategorie.setId(kategorieDTO.getId());
        kategorie.setImage(kategorieDTO.getFile().getBytes());
        return kategorie;
    }

    public List<KategorieDTO> createKategorieDTO(List<Kategorie> kategories)
    {
        List<KategorieDTO> kategorieDTOS = new ArrayList<>();
        kategories.forEach(e -> kategorieDTOS.add(createKategorieDTO(e)));
        return kategorieDTOS;
    }

    public List<Kategorie> createKategorie(List<KategorieDTO> kategorieDTOS)
    {
        List<Kategorie> kategorie = new ArrayList<>();
        kategorieDTOS.forEach(e -> {
            try {
                kategorie.add(createKategorie(e));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return kategorie;
    }
}
