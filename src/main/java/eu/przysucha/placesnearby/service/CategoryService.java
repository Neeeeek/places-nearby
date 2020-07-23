package eu.przysucha.placesnearby.service;

import eu.przysucha.placesnearby.aspect.CategoryAddAspectAnnotation;
import eu.przysucha.placesnearby.aspect.CategoryVisibilityChangeAspectAnnotation;
import eu.przysucha.placesnearby.model.Category;
import eu.przysucha.placesnearby.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories () {
        return categoryRepository.findAll();
    }

    @CategoryAddAspectAnnotation
    public void addCategory (Category category) {

        categoryRepository.save(category);

    }

    @CategoryVisibilityChangeAspectAnnotation
    public void changeVisibility(long id) {
        Optional<Category> category = categoryRepository.findById(id);
        category.get().setVisibility(!category.get().getVisibility());
        categoryRepository.save(category.get());
    }
}
