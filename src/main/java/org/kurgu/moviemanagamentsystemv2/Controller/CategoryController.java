package org.kurgu.moviemanagamentsystemv2.Controller;

import org.kurgu.moviemanagamentsystemv2.Model.Category;
import org.kurgu.moviemanagamentsystemv2.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {this.categoryRepository = categoryRepository;}

    @GetMapping("/category")
    public String getIndex(Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("berkay", categories);
        return "category/index";
    }

    @GetMapping("/category/add")
    public String addCategory(Model model) {
        model.addAttribute("berkay", new Category());
        return "category/addcategory";
    }

    @PostMapping("/category/add")
    public String addCategory(@ModelAttribute("berkay") Category category) {
        categoryRepository.save(category);
        return "redirect:/category";
    }
    @GetMapping("/category/update/{id}")
    public String updateCategoryForm(@PathVariable("id") int id, Model model) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            model.addAttribute("berkay", optionalCategory.get());
            return "category/updatecategory";
        } else {
            return "redirect:/category";
        }
    }

    @PostMapping("/category/update/{id}")
    public String updateCategory(@PathVariable("id") int id, @ModelAttribute Category category) {
        category.setId(id);
        categoryRepository.save(category);
        return "redirect:/category";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        categoryRepository.deleteById(id);
        return "redirect:/category";
    }

}
