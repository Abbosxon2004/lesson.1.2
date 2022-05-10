package uz.pdp.online.lesson_1_2.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDto {

    private String name;

    private String description;

    private boolean isFinished;

    private Integer languageId;
}
