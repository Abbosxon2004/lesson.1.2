package uz.pdp.online.lesson_1_2.Service;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_1_2.Entity.Language;
import uz.pdp.online.lesson_1_2.Payload.ApiResponse;
import uz.pdp.online.lesson_1_2.Payload.LanguageDto;
import uz.pdp.online.lesson_1_2.Repository.LanguageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {
    @Autowired
    LanguageRepository languageRepository;

    public ResponseEntity getLanguages() {
        List<Language> languageList = languageRepository.findAll();
        return ResponseEntity.ok(languageList);
    }

    public ResponseEntity getLanguageById(Integer id) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (!optionalLanguage.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Language id not found", false));
        return ResponseEntity.status(HttpStatus.FOUND).body(optionalLanguage.get());
    }

    public ResponseEntity addLanguge(LanguageDto languageDto) {
        if (languageRepository.existsByName(languageDto.getName()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Language name already exists", false));
        Language language = new Language();
        language.setName(languageDto.getName());
        languageRepository.save(language);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Language successfully added", true));
    }

    public ResponseEntity editLanguge(Integer id,LanguageDto languageDto) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (!optionalLanguage.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Language id not found",false));
        if (languageRepository.existsByName(languageDto.getName()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Language name already exists", false));
        Language language = optionalLanguage.get();
        language.setName(languageDto.getName());
        languageRepository.save(language);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Language edited", true));
    }

    public ResponseEntity deleteLanguage(Integer id){
        try {
            languageRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Language deleted",true));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Language id not found",false));
        }
    }
}
