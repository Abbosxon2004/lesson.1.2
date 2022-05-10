package uz.pdp.online.lesson_1_2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_1_2.Entity.Answer;
import uz.pdp.online.lesson_1_2.Entity.Task;
import uz.pdp.online.lesson_1_2.Entity.Users;
import uz.pdp.online.lesson_1_2.Payload.AnswerDto;
import uz.pdp.online.lesson_1_2.Payload.ApiResponse;
import uz.pdp.online.lesson_1_2.Repository.AnswerRepository;
import uz.pdp.online.lesson_1_2.Repository.TaskRepository;
import uz.pdp.online.lesson_1_2.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;

    public ResponseEntity getAnswers() {
        List<Answer> answerList = answerRepository.findAll();
        return ResponseEntity.ok(answerList);
    }

    public ResponseEntity getAnswerById(Integer id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (!optionalAnswer.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Answer id not found", false));
        return ResponseEntity.status(HttpStatus.FOUND).body(optionalAnswer.get());
    }

    public ResponseEntity addAnswer(AnswerDto answerDto) {

        Optional<Users> optionalUser = userRepository.findById(answerDto.getUserId());
        if (!optionalUser.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User id not found", false));

        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        if (!optionalTask.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Task id not found", false));

        Answer answer = new Answer();
        answer.setUser(optionalUser.get());
        answer.setTask(optionalTask.get());
        answer.setText(answerDto.getText());
        answer.setCorrect(answerDto.isCorrect());
        answerRepository.save(answer);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Answer added", true));
    }

    public ResponseEntity editAnswer(Integer id, AnswerDto answerDto) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (!optionalAnswer.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Answer id not found", false));

        Optional<Users> optionalUser = userRepository.findById(answerDto.getUserId());
        if (!optionalUser.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User id not found", false));

        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        if (!optionalTask.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Task id not found", false));

        Answer answer = optionalAnswer.get();
        answer.setUser(optionalUser.get());
        answer.setTask(optionalTask.get());
        answer.setText(answerDto.getText());
        answer.setCorrect(answerDto.isCorrect());
        answerRepository.save(answer);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Answer edited", true));
    }

    public ResponseEntity deleteAnswer(Integer id){
        try {
            answerRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Answer deleted", false));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Answer id not found", false));
        }
    }
}
