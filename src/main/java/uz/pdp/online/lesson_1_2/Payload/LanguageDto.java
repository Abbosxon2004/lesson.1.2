package uz.pdp.online.lesson_1_2.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageDto {
    @NotNull(message = "Language name bo`sh bo`lmasligi kerak")
    private String name;
}
