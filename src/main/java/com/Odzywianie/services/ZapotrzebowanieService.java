package com.Odzywianie.services;

import com.Odzywianie.enums.Gender;
import com.Odzywianie.models.Produkty;
import com.Odzywianie.models.Zapotrzebowanie;
import com.Odzywianie.modelsDTO.UzytkownikDTO;
import com.Odzywianie.modelsDTO.ZapotrzebowanieDTO;
import com.Odzywianie.repositories.ZapotrzebowanieRepository;
import com.Odzywianie.utils.ZapotrzebowanieConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ZapotrzebowanieService {

    private final ProduktyService produktyService;
    private final UzytkownikService uzytkownikService;
    private final ZapotrzebowanieRepository zapotrzebowanieRepository;
    private final ZapotrzebowanieConverter zapotrzebowanieConverter;

    public ZapotrzebowanieService(ProduktyService produktyService, UzytkownikService uzytkownikService, ZapotrzebowanieRepository zapotrzebowanieRepository, ZapotrzebowanieConverter zapotrzebowanieConverter) {
        this.produktyService = produktyService;
        this.uzytkownikService = uzytkownikService;
        this.zapotrzebowanieRepository = zapotrzebowanieRepository;
        this.zapotrzebowanieConverter = zapotrzebowanieConverter;
    }

    public void addZapotrzebowanie(float weight, LocalTime time, Long id)
    {
        Zapotrzebowanie zapotrzebowanie = new Zapotrzebowanie();
        zapotrzebowanie.setWeight(weight);
        zapotrzebowanie.setTime(time);
        zapotrzebowanie.getZapotrzebowanieUzytkownik().add(uzytkownikService.getUzytkownikByAuthentication());
        zapotrzebowanie.getZapotrzebowanieProdukty().add(produktyService.getProduktById(id));
        zapotrzebowanie.setDate(LocalDate.now());
        zapotrzebowanieRepository.save(zapotrzebowanie);

    }

    public float calculateKcal()
    {
        UzytkownikDTO uzytkownikDTO = uzytkownikService.getUzytkownikDTOByAuthentication();
        float BMR = (float) (10*uzytkownikDTO.getWeight() + 6.25*uzytkownikDTO.getHeight() - 5*uzytkownikDTO.getAge());
        if(uzytkownikDTO.getGender().equals(Gender.MAN))
        {
            BMR += 5;
        }
        else
        {
            BMR -= 161;
        }
        int activity = uzytkownikDTO.getActivity();
        if(activity == 0)
        {
            BMR *= 1.2;
        }
        else if(activity == 1)
        {
            BMR *= 1.375;
        }
        else if(activity == 2)
        {
            BMR *= 1.55;
        }
        else if(activity == 3)
        {
            BMR *= 1.725;
        }
        else
        {
            BMR *= 1.9;
        }
        return BMR;
    }

    public List<Float> calculateFat(float BMR)
    {
        UzytkownikDTO uzytkownikDTO = uzytkownikService.getUzytkownikDTOByAuthentication();
        List<Float> fat = new ArrayList<>();
        int age = uzytkownikDTO.getAge();
        float min;
        float max;
        if(age >=1 && age <=3)
        {
            min = 0.3F;
            max = 0.4F;
        }
        else if(age >=4 && age <=18)
        {
            min = 0.25F;
            max = 0.35F;
        }
        else
        {
            min = 0.20F;
            max = 0.35F;
        }
        fat.add((BMR*min)/9);
        fat.add((BMR*max)/9);
        return fat;
    }

    public List<Float> calculateSaturatedFat(float BMR)
    {
        List<Float> saturatedFat = new ArrayList<>();
        saturatedFat.add((BMR*0.06F)/9);
        saturatedFat.add((BMR*0.1F)/9);
        return saturatedFat;
    }

    public List<Float> calculateSodium()
    {
        UzytkownikDTO uzytkownikDTO = uzytkownikService.getUzytkownikDTOByAuthentication();
        int age = uzytkownikDTO.getAge();
        List<Float> sodium = new ArrayList<>();
        if(age >= 1 && age <= 3)
        {
            sodium.add(500F);
            sodium.add(1000F);
        }
        else if(age >= 4 && age <= 8)
        {
            sodium.add(800F);
            sodium.add(1200F);
        }
        else if(age >= 9 && age <= 13)
        {
            sodium.add(1000F);
            sodium.add(1500F);
        }
        else if(age >= 14 && age <= 18)
        {
            sodium.add(1200F);
            sodium.add(2300F);
        }
        else
        {
            sodium.add(1500F);
            sodium.add(2300F);
        }
        return sodium;

    }

    public List<Float> calculatePotasium()
    {
        UzytkownikDTO uzytkownikDTO = uzytkownikService.getUzytkownikDTOByAuthentication();
        int age = uzytkownikDTO.getAge();
        List<Float> potasium = new ArrayList<>();
        if(age >= 1 && age <= 3)
        {
            potasium.add(2000F);
            potasium.add(3000F);
        }
        else if(age >= 4 && age <= 8)
        {
            potasium.add(2300F);
            potasium.add(3000F);
        }
        else
        {
            potasium.add(2500F);
            potasium.add(3800F);
        }
        return potasium;
    }

    public List<Float> calculateCarbohydrates(float BMR)
    {
        List<Float> carbohydrates = new ArrayList<>();
        carbohydrates.add((BMR*0.45F)/4);
        carbohydrates.add((BMR*0.65F)/4);
        return carbohydrates;
    }

    public List<Float> calculateFiber(float BMR)
    {
        List<Float> fiber = new ArrayList<>();
        fiber.add((BMR/1000F)*14);
        fiber.add((BMR/1000F)*25);
        return fiber;
    }

    public List<Float> calculateSugars(float BMR)
    {
        List<Float> sugars = new ArrayList<>();
        sugars.add((BMR*0.05F)/4);
        sugars.add((BMR*0.1F)/4);
        return sugars;
    }

    public List<Float> calculateProteins()
    {
        UzytkownikDTO uzytkownikDTO = uzytkownikService.getUzytkownikDTOByAuthentication();
        List<Float> proteins = new ArrayList<>();
        proteins.add(uzytkownikDTO.getWeight()*0.8F);
        proteins.add(uzytkownikDTO.getWeight()*2.2F);
        return proteins;
    }


    public List<Float> calculateAscorbicAcid()
    {
        UzytkownikDTO uzytkownikDTO = uzytkownikService.getUzytkownikDTOByAuthentication();
        int age = uzytkownikDTO.getAge();
        List<Float> ascorbicAcid = new ArrayList<>();
        if(age >= 1 && age <= 3)
        {
            ascorbicAcid.add(15F);
            ascorbicAcid.add(400F);
        }
        else if(age >= 4 && age <= 8)
        {
            ascorbicAcid.add(25F);
            ascorbicAcid.add(650F);
        }
        else if(age >= 9 && age <= 13)
        {
            ascorbicAcid.add(45F);
            ascorbicAcid.add(1200F);
        }
        else if(age >= 14 && age <= 18)
        {
            if(uzytkownikDTO.getGender().equals(Gender.MAN)) {
                ascorbicAcid.add(75F);
                ascorbicAcid.add(1800F);
            }
            else{
                ascorbicAcid.add(65F);
                ascorbicAcid.add(1800F);
            }
        }
        else
        {
            if(uzytkownikDTO.getGender().equals(Gender.MAN)) {
                ascorbicAcid.add(90F);
                ascorbicAcid.add(2000F);
            }
            else{
                ascorbicAcid.add(75F);
                ascorbicAcid.add(2000F);
            }
        }
        return ascorbicAcid;

    }

    public List<Float> calculateIron()
    {
        UzytkownikDTO uzytkownikDTO = uzytkownikService.getUzytkownikDTOByAuthentication();
        int age = uzytkownikDTO.getAge();
        List<Float> iron = new ArrayList<>();
        if(age >= 1 && age <= 3)
        {
            iron.add(7F);
            iron.add(40F);
        }
        else if(age >= 4 && age <= 8)
        {
            iron.add(10F);
            iron.add(40F);
        }
        else if(age >= 9 && age <= 13)
        {
            iron.add(8F);
            iron.add(40F);
        }
        else if(age >= 14 && age <= 18)
        {
            if(uzytkownikDTO.getGender().equals(Gender.MAN)) {
                iron.add(11F);
                iron.add(45F);
            }
            else{
                iron.add(15F);
                iron.add(45F);
            }
        }
        else if(age >= 19 && age <= 50 && uzytkownikDTO.getGender().equals(Gender.WOMAN))
        {
            iron.add(18F);
            iron.add(45F);
        }
        else
        {
            iron.add(8F);
            iron.add(45F);
        }
        return iron;

    }

    public List<Float> calculateVitaminB6()
    {
        UzytkownikDTO uzytkownikDTO = uzytkownikService.getUzytkownikDTOByAuthentication();
        int age = uzytkownikDTO.getAge();
        List<Float> vitaminB6 = new ArrayList<>();
        if(age >= 1 && age <= 3)
        {
            vitaminB6.add(0.5F);
            vitaminB6.add(30F);
        }
        else if(age >= 4 && age <= 8)
        {
            vitaminB6.add(0.6F);
            vitaminB6.add(40F);
        }
        else if(age >= 9 && age <= 13)
        {
            vitaminB6.add(1F);
            vitaminB6.add(60F);
        }
        else if(age >= 14 && age <= 18)
        {
            if(uzytkownikDTO.getGender().equals(Gender.MAN)) {
                vitaminB6.add(1.3F);
                vitaminB6.add(80F);
            }
            else{
                vitaminB6.add(1.2F);
                vitaminB6.add(80F);
            }
        }
        else if(age >= 19 && age <= 50)
        {
            if(uzytkownikDTO.getGender().equals(Gender.MAN)) {
                vitaminB6.add(1.3F);
                vitaminB6.add(100F);
            }
            else{
                vitaminB6.add(1.3F);
                vitaminB6.add(100F);
            }
        }
        else
        {
            if(uzytkownikDTO.getGender().equals(Gender.MAN)) {
                vitaminB6.add(1.7F);
                vitaminB6.add(100F);
            }
            else{
                vitaminB6.add(1.5F);
                vitaminB6.add(100F);
            }
        }
        return vitaminB6;

    }


    public float calculateMagnesium()
    {
        UzytkownikDTO uzytkownikDTO = uzytkownikService.getUzytkownikDTOByAuthentication();
        int age = uzytkownikDTO.getAge();
        if(age >= 1 && age <= 3)
        {
            return 80F;
        }
        else if(age >= 4 && age <= 8)
        {
            return 130F;
        }
        else if(age >= 9 && age <= 13)
        {
            return 240F;
        }
        else if(age >= 14 && age <= 18)
        {
            if(uzytkownikDTO.getGender().equals(Gender.MAN)) {
                return 410F;
            }
            else{
                return 360F;
            }
        }
        else if(age >= 19 && age <= 30)
        {
            if(uzytkownikDTO.getGender().equals(Gender.MAN)) {
                return 400F;
            }
            else{
                return 310F;
            }
        }
        else
        {
            if(uzytkownikDTO.getGender().equals(Gender.MAN)) {
                return 420F;
            }
            else{
                return 320F;
            }
        }

    }


    public List<Float> calculateCalcium()
    {
        UzytkownikDTO uzytkownikDTO = uzytkownikService.getUzytkownikDTOByAuthentication();
        int age = uzytkownikDTO.getAge();
        List<Float> calcium = new ArrayList<>();
        if(age >= 1 && age <= 3)
        {
            calcium.add(500F);
            calcium.add(2500F);
        }
        else if(age >= 4 && age <= 8)
        {
            calcium.add(800F);
            calcium.add(2500F);
        }
        else if(age >= 9 && age <= 13)
        {
            calcium.add(1300F);
            calcium.add(2500F);
        }
        else if(age >= 14 && age <= 18)
        {
            calcium.add(1300F);
            calcium.add(3000F);
        }
        else if(age >= 19 && age <= 50)
        {
            calcium.add(1000F);
            calcium.add(2500F);
        }
        else if(age >= 51 && age <= 70)
        {
            if(uzytkownikDTO.getGender().equals(Gender.MAN)) {
                calcium.add(1000F);
                calcium.add(2000F);
            }
            else{
                calcium.add(1200F);
                calcium.add(2000F);
            }
        }
        else
        {
            calcium.add(1200F);
            calcium.add(2000F);
        }
        return calcium;

    }

    public List<Float> calculateVitaminD()
    {
        UzytkownikDTO uzytkownikDTO = uzytkownikService.getUzytkownikDTOByAuthentication();
        int age = uzytkownikDTO.getAge();
        List<Float> vitaminD = new ArrayList<>();
        if(age >= 1 && age <= 70) {
            vitaminD.add(600F);
            vitaminD.add(4000F);
        }
        else
        {
            vitaminD.add(800F);
            vitaminD.add(4000F);
        }
        return vitaminD;

    }

    public float calculateVitaminB12()
    {
        UzytkownikDTO uzytkownikDTO = uzytkownikService.getUzytkownikDTOByAuthentication();
        int age = uzytkownikDTO.getAge();
        if(age >= 1 && age <= 3)
        {
            return 0.9F;
        }
        else if(age >= 4 && age <= 8)
        {
            return 1.2F;
        }
        else if(age >= 9 && age <= 13)
        {
            return 1.8F;
        }
        else
        {
            return 2.4F;
        }
    }

    public List<Float> calculateCholesterol()
    {
        List<Float> cholesterol = new ArrayList<>();
        cholesterol.add(0F);
        cholesterol.add(300F);
        return cholesterol;

    }

    public List<Float> getNutreins()
    {
        List<Zapotrzebowanie> zapotrzebowanies = zapotrzebowanieRepository.findAllByZapotrzebowanieUzytkownik(uzytkownikService.getUzytkownikByAuthentication());
        float kcal = 0;
        float fat = 0;
        float saturatedFat = 0;
        float cholesterol = 0;
        float sodium = 0;
        float potassium = 0;
        float carbohydrates = 0;
        float fiber = 0;
        float sugars = 0;
        float protein = 0;
        float ascorbicAcid = 0;
        float iron = 0;
        float vitaminB6 = 0;
        float magnesium = 0;
        float calcium = 0;
        float vitaminD = 0;
        float vitaminB12 = 0;
        for(Zapotrzebowanie zapotrzebowanie : zapotrzebowanies)
        {
            List<Produkty> produkties = zapotrzebowanie.getZapotrzebowanieProdukty();
            for(Produkty produkty : produkties) {
                kcal += (produkty.getKcal()*((float) zapotrzebowanie.getWeight() /100));
                fat += (produkty.getFat()*((float) zapotrzebowanie.getWeight() /100));
                saturatedFat += (produkty.getSaturatedFat()*((float) zapotrzebowanie.getWeight() /100));
                cholesterol += (produkty.getCholesterol()*((float) zapotrzebowanie.getWeight() /100));
                sodium += (produkty.getSodium()*((float) zapotrzebowanie.getWeight() /100));
                potassium += (produkty.getPotassium()*((float) zapotrzebowanie.getWeight() /100));
                carbohydrates += (produkty.getCarbohydrates()*((float) zapotrzebowanie.getWeight() /100));
                fiber += (produkty.getFiber()*((float) zapotrzebowanie.getWeight() /100));
                sugars += (produkty.getSugars()*((float) zapotrzebowanie.getWeight() /100));
                protein += (produkty.getProtein()*((float) zapotrzebowanie.getWeight() /100));
                ascorbicAcid += (produkty.getAscorbicAcid()*((float) zapotrzebowanie.getWeight() /100));
                iron += (produkty.getIron()*((float) zapotrzebowanie.getWeight() /100));
                vitaminB6 += (produkty.getVitaminB6()*((float) zapotrzebowanie.getWeight() /100));
                magnesium += (produkty.getMagnesium()*((float) zapotrzebowanie.getWeight() /100));
                calcium += (produkty.getCalcium()*((float) zapotrzebowanie.getWeight() /100));
                vitaminD += (produkty.getVitaminD()*((float) zapotrzebowanie.getWeight() /100));
                vitaminB12 += (produkty.getVitaminB12()*((float) zapotrzebowanie.getWeight() /100));
            }
        }
        List<Float> nutreins = new ArrayList<>();
        nutreins.add(kcal);
        nutreins.add(fat);
        nutreins.add(saturatedFat);
        nutreins.add(cholesterol);
        nutreins.add(sodium);
        nutreins.add(potassium);
        nutreins.add(carbohydrates);
        nutreins.add(fiber);
        nutreins.add(sugars);
        nutreins.add(protein);
        nutreins.add(ascorbicAcid);
        nutreins.add(iron);
        nutreins.add(vitaminB6);
        nutreins.add(magnesium);
        nutreins.add(calcium);
        nutreins.add(vitaminD);
        nutreins.add(vitaminB12);
        return  nutreins;
    }


    public List<ZapotrzebowanieDTO> getZapotrzebowanieDTO(){
        return zapotrzebowanieConverter.createZapotrzebowanieDTO(zapotrzebowanieRepository.findAllByZapotrzebowanieUzytkownikOrderByTimeDesc(uzytkownikService.getUzytkownikByAuthentication()));
    }

    public boolean existsZapotrzebowanieById(Long id)
    {
        return zapotrzebowanieRepository.existsById(id);
    }
    public void deleteZapotrzebowanie(Long id)
    {
        zapotrzebowanieRepository.deleteById(id);
    }


    public void editZapotrzebowanie(Long id, float weight, LocalTime time)
    {
        Zapotrzebowanie zapotrzebowanie = zapotrzebowanieRepository.findById(id).get();
        zapotrzebowanie.setWeight(weight);
        zapotrzebowanie.setTime(time);
        zapotrzebowanieRepository.save(zapotrzebowanie);

    }

    public Long getIdProduktByIdZapotrzebowanie(Long id)
    {
        return zapotrzebowanieRepository.findProduktyIdsByZapotrzebowanieId(id);
    }




}
