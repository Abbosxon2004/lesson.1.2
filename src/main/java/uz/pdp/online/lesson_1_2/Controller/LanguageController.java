package uz.pdp.online.lesson_1_2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_1_2.Payload.LanguageDto;
import uz.pdp.online.lesson_1_2.Service.LanguageService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/language")
public class LanguageController {
    @Autowired
    LanguageService languageService;

    @GetMapping
    public ResponseEntity getLanguages() {
        return languageService.getLanguages();
    }

    @GetMapping("/{id}")
    public ResponseEntity getLanguageById(@PathVariable Integer id) {
        return languageService.getLanguageById(id);
    }

    @PostMapping
    public ResponseEntity addLanguage(@RequestBody LanguageDto languageDto){
        return languageService.addLanguge(languageDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity editLanguage(@PathVariable Integer id,@RequestBody LanguageDto languageDto){
        return languageService.editLanguge(id,languageDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLanguage(@PathVariable Integer id){
        return languageService.deleteLanguage(id);
    }

    // xatolik messagelarni chiqarish uchun
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
