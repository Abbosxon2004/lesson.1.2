package uz.pdp.online.lesson_1_2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_1_2.Entity.Users;
import uz.pdp.online.lesson_1_2.Payload.ApiResponse;
import uz.pdp.online.lesson_1_2.Payload.UserDto;
import uz.pdp.online.lesson_1_2.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity getUsers() {
        List<Users> usersList = userRepository.findAll();
        return ResponseEntity.ok(usersList);
    }

    public ResponseEntity getUserById(Integer id) {
        Optional<Users> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User id not found", false));
        return ResponseEntity.status(HttpStatus.FOUND).body(optionalUser.get());
    }

    public ResponseEntity addUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("This email already exists", false));

        Users users = new Users();
        users.setEmail(userDto.getEmail());
        users.setPassword(userDto.getPassword());

        userRepository.save(users);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("User saved", true));
    }

    public ResponseEntity editUser(Integer id, UserDto userDto) {
        Optional<Users> optionalUsers = userRepository.findById(id);
        if (!optionalUsers.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User id not found", false));
        if (userRepository.existsByEmailAndIdNot(userDto.getEmail(), id))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("This email already exists", false));

        Users users = optionalUsers.get();
        users.setPassword(userDto.getPassword());
        users.setEmail(userDto.getEmail());
        userRepository.save(users);
        return ResponseEntity.ok(new ApiResponse("User edited", true));
    }

    public ResponseEntity deleteUserById(Integer id){
        try {
            userRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("User deleted", true));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User id not found", true));
        }
    }
}
