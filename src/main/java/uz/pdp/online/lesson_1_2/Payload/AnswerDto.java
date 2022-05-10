package uz.pdp.online.lesson_1_2.Payload;

import lombok.Data;

@Data
public class AnswerDto {

    private Integer userId;

    private Integer taskId;

    private String text;

    private boolean isCorrect;
}
