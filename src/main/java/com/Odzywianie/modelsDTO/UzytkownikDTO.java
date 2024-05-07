package com.Odzywianie.modelsDTO;

import com.Odzywianie.Validators.*;
import com.Odzywianie.enums.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UzytkownikDTO {
    @NotBlank(message = "{message.blankUzytkownikName}", groups = CreatingUserGroup.class)
    @Size(min=7 , max = 20, message = "{message.sizeUzytkownikName}", groups = CreatingUserGroup.class)
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$", message = "{message.patternUzytkownikName}", groups = CreatingUserGroup.class)
    private String name;
    @NotBlank(message = "{message.blankUzytkownikPassword}", groups = {CreatingUserGroup.class, ChangePasswordGroup.class})
    @Size(min=8, message = "{message.sizeUzytkownikPassword}", groups = {CreatingUserGroup.class, ChangePasswordGroup.class})
    @PatternPassword( message = "{message.patternPasswordUzytkownik}", groups = {CreatingUserGroup.class, ChangePasswordGroup.class})
    @NotPopularPassword(message = "{message.notPopularPasswordUzytkownik}", groups = {CreatingUserGroup.class, ChangePasswordGroup.class})
    private String password;
    private String role;
    @Min(value=0, message = "{message.minUzytkownikWeight}", groups = {EditingInformationsGroup.class, CreatingUserGroup.class})
    private int weight;
    @Min(value=0, message = "{message.minUzytkownikHeight}", groups = {EditingInformationsGroup.class, CreatingUserGroup.class})
    private int height;
    private Gender gender;
    @Min(value=0, message = "{message.minUzytkownikActivity}", groups = {EditingInformationsGroup.class, CreatingUserGroup.class})
    private int activity;
    @Min(value=0, message = "{message.minUzykownikAge", groups = {EditingInformationsGroup.class, CreatingUserGroup.class})
    private int age;
}
