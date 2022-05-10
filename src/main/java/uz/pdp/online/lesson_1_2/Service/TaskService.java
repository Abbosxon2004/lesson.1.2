package uz.pdp.online.lesson_1_2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_1_2.Entity.Language;
import uz.pdp.online.lesson_1_2.Entity.Task;
import uz.pdp.online.lesson_1_2.Payload.ApiResponse;
import uz.pdp.online.lesson_1_2.Payload.TaskDto;
import uz.pdp.online.lesson_1_2.Repository.LanguageRepository;
import uz.pdp.online.lesson_1_2.Repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    LanguageRepository languageRepository;

    public ResponseEntity getTasks() {
        List<Task> taskList = taskRepository.findAll();
        return ResponseEntity.ok(taskList);
    }

    public ResponseEntity getTaskById(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Task id not found", false));
        return ResponseEntity.status(HttpStatus.FOUND).body(optionalTask.get());
    }

    public ResponseEntity addTask(TaskDto taskDto) {
        if (taskRepository.existsByName(taskDto.getName()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Task name already exits", false));
        Optional<Language> optionalLanguage = languageRepository.findById(taskDto.getLanguageId());
        if (!optionalLanguage.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Language id not found", false));

        Task task = new Task();
        task.setLanguage(optionalLanguage.get());
        task.setName(taskDto.getName());
        task.setText(taskDto.getText());
        task.setSolution(taskDto.getSolution());
        task.setHasStars(taskDto.getHasStars());

        taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Task successfully saved", true));
    }

    public ResponseEntity editTask(Integer id, TaskDto taskDto) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Task id not found", false));
        if (taskRepository.existsByNameAndIdNot(taskDto.getName(), id))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Task name already exits", false));
        Optional<Language> optionalLanguage = languageRepository.findById(taskDto.getLanguageId());
        if (!optionalLanguage.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Language id not found", false));

        Task task = optionalTask.get();
        task.setLanguage(optionalLanguage.get());
        task.setName(taskDto.getName());
        task.setText(taskDto.getText());
        task.setSolution(taskDto.getSolution());
        task.setHasStars(taskDto.getHasStars());

        taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Task successfully edited", true));
    }


    public ResponseEntity deleteTask(Integer id){
        try {
            taskRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Task deleted",true));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Task id not found",false));

        }
    }
}
