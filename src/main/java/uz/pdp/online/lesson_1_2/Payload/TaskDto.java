package uz.pdp.online.lesson_1_2.Payload;

import lombok.Data;

@Data
public class TaskDto {
    private String name;

    private String text;

    private String solution;

    private Integer hasStars;

    private Integer languageId;
}
