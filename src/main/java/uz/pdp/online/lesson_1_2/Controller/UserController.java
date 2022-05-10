package uz.pdp.online.lesson_1_2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_1_2.Payload.UserDto;
import uz.pdp.online.lesson_1_2.Service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity addUser(@Valid @RequestBody UserDto userDto){
        return userService.addUser(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity editUser(@Valid @PathVariable Integer id, @RequestBody UserDto userDto){
        return userService.editUser(id,userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        return userService.deleteUserById(id);
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
