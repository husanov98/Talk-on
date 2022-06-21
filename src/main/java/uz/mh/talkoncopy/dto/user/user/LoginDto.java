package uz.mh.talkoncopy.dto.user.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {
    @NotBlank
    @Pattern(regexp = "[+](998)[0-9]{9}",message = "incorrect phoneNumber")
    private String phoneNumber;

    private String code;
}
