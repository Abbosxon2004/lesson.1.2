package uz.pdp.online.lesson_1_2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_1_2.Payload.TaskDto;
import uz.pdp.online.lesson_1_2.Service.TaskService;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping
    public ResponseEntity get() {
        return taskService.getTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Integer id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody TaskDto taskDto) {
        return taskService.addTask(taskDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable Integer id, @RequestBody TaskDto taskDto) {
        return taskService.editTask(id, taskDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        return taskService.deleteTask(id);
    }
}
