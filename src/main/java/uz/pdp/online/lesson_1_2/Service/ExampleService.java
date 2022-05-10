package uz.pdp.online.lesson_1_2.Service;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_1_2.Entity.Example;
import uz.pdp.online.lesson_1_2.Entity.Task;
import uz.pdp.online.lesson_1_2.Payload.ApiResponse;
import uz.pdp.online.lesson_1_2.Payload.ExampleDto;
import uz.pdp.online.lesson_1_2.Repository.ExampleRepository;
import uz.pdp.online.lesson_1_2.Repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExampleService {
    @Autowired
    ExampleRepository exampleRepository;
    @Autowired
    TaskRepository taskRepository;

    public ResponseEntity getExamples(){
        List<Example> exampleList = exampleRepository.findAll();
        return ResponseEntity.ok(exampleList);
    }

    public ResponseEntity getExampleById(Integer id){
        Optional<Example> optionalExample = exampleRepository.findById(id);
        if (!optionalExample.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Example id not found",false));
        return ResponseEntity.status(HttpStatus.FOUND).body(optionalExample.get());
    }

    public ResponseEntity addExample(ExampleDto exampleDto){
        Optional<Task> optionalTask = taskRepository.findById(exampleDto.getTaskId());
        if (!optionalTask.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Task id not found",false));
        Example example=new Example();
        example.setTask(optionalTask.get());
        example.setText(exampleDto.getText());
        exampleRepository.save(example);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Example added",true));
    }

    public ResponseEntity editExample(ExampleDto exampleDto,Integer id){

        Optional<Example> optionalExample = exampleRepository.findById(id);
        if (!optionalExample.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Example id not found",false));

        Optional<Task> optionalTask = taskRepository.findById(exampleDto.getTaskId());
        if (!optionalTask.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Task id not found",false));

        Example example=optionalExample.get();
        example.setTask(optionalTask.get());
        example.setText(exampleDto.getText());
        exampleRepository.save(example);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Example edited",true));
    }

    public ResponseEntity deleteExample(Integer id){
        try {
            exampleRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Example deleted",true));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Example id not found",false));
        }
    }

}
