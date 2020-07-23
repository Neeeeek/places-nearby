package eu.przysucha.placesnearby.controller;

import eu.przysucha.placesnearby.model.Category;
import eu.przysucha.placesnearby.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin")
public class AdminController {

    CategoryService categoryService;

    @Autowired
    public AdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/categories/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addCategory(@RequestBody Category category, @RequestHeader Map<String, String> headers) {

        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });
        category.setVisibility(false);
        categoryService.addCategory(category);


        return new ResponseEntity(HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/categories/{id}")
    public ResponseEntity<?> changeVisibility(@PathVariable("id")  long id) {


        System.out.println("id do publish:" + id);
        //Optional<Quiz>  quiz = quizService.getQuiz(id);

        categoryService.changeVisibility(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }


}
