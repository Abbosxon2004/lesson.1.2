package uz.pdp.online.lesson_1_2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_1_2.Payload.CategoryDto;
import uz.pdp.online.lesson_1_2.Service.CategoryService;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity get(){
        return categoryService.getCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public ResponseEntity addCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity editCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer id){
        return categoryService.editCategory(categoryDto,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id){
        return categoryService.deleteCategory(id);
    }
}
