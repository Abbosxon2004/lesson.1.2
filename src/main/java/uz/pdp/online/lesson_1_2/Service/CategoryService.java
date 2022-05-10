package uz.pdp.online.lesson_1_2.Service;

import jdk.javadoc.doclet.Reporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_1_2.Entity.Category;
import uz.pdp.online.lesson_1_2.Entity.Language;
import uz.pdp.online.lesson_1_2.Payload.ApiResponse;
import uz.pdp.online.lesson_1_2.Payload.CategoryDto;
import uz.pdp.online.lesson_1_2.Repository.CategoryRepository;
import uz.pdp.online.lesson_1_2.Repository.LanguageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    LanguageRepository languageRepository;

    public ResponseEntity getCategories(){
        List<Category> categoryList = categoryRepository.findAll();
        return ResponseEntity.ok(categoryList);
    }

    public ResponseEntity getCategoryById(Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category id not found",false));
        return ResponseEntity.ok(optionalCategory.get());
    }

    public ResponseEntity addCategory(CategoryDto categoryDto){
        if (categoryRepository.existsByName(categoryDto.getName()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Category name already exists",false));
        Optional<Language> optionalLanguage = languageRepository.findById(categoryDto.getLanguageId());
        if (!optionalLanguage.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Language id not found",false));

        Category category=new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setFinished(categoryDto.isFinished());
        category.setLanguage(optionalLanguage.get());
        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Category successfully added",true));
    }

    public ResponseEntity editCategory(CategoryDto categoryDto,Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Category id not found",false));

        if (categoryRepository.existsByNameAndIdNot(categoryDto.getName(),id))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Category name already exists",false));
        Optional<Language> optionalLanguage = languageRepository.findById(categoryDto.getLanguageId());
        if (!optionalLanguage.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Language id not found",false));

        Category category=optionalCategory.get();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setFinished(categoryDto.isFinished());
        category.setLanguage(optionalLanguage.get());
        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Category successfully edited",true));
    }

    public ResponseEntity deleteCategory(Integer id){
        try {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse("Category deleted",true));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse());
        }
    }
}
