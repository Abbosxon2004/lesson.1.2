package uz.pdp.online.lesson_1_2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_1_2.Payload.ExampleDto;
import uz.pdp.online.lesson_1_2.Service.ExampleService;

@RestController
@RequestMapping(value = "/example")
public class ExampleController {
    @Autowired
    ExampleService exampleService;

    @GetMapping
    public ResponseEntity get(){
        return exampleService.getExamples();
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        return  exampleService.getExampleById(id);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody ExampleDto exampleDto){
        return exampleService.addExample(exampleDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@RequestBody ExampleDto exampleDto,@PathVariable Integer id){
        return exampleService.editExample(exampleDto,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        return exampleService.deleteExample(id);
    }

}
