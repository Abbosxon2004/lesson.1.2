package uz.pdp.online.lesson_1_2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_1_2.Payload.AnswerDto;
import uz.pdp.online.lesson_1_2.Service.AnswerService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @GetMapping
    public ResponseEntity get() {
        return answerService.getAnswers();
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Integer id) {
        return answerService.getAnswerById(id);
    }

    @PostMapping
    public ResponseEntity add(@Valid @RequestBody AnswerDto answerDto) {
        return answerService.addAnswer(answerDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@Valid @RequestBody AnswerDto answerDto,@PathVariable Integer id) {
        return answerService.editAnswer(id, answerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        return answerService.deleteAnswer(id);
    }
}
