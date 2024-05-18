package org.kurgu.moviemanagamentsystemv2.Controller;

import org.kurgu.moviemanagamentsystemv2.Model.Category;
import org.kurgu.moviemanagamentsystemv2.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/category")
    public String getIndex(Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("berkay", categories);
        return "category/index";
    }

    @GetMapping("/category/add")
    public String addCategory(Model model) {
        if (hasAdminRole()) {
            model.addAttribute("berkay", new Category());
            return "category/addcategory";
        } else {
            return "redirect:/access-denied";
        }
    }

    @PostMapping("/category/add")
    public String addCategory(@ModelAttribute("berkay") Category category) {
        if (hasAdminRole()) {
            categoryRepository.save(category);
            return "redirect:/category";
        } else {
            return "redirect:/access-denied";
        }
    }

    @GetMapping("/category/update/{id}")
    public String updateCategoryForm(@PathVariable("id") int id, Model model) {
        if (hasAdminRole()) {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (optionalCategory.isPresent()) {
                model.addAttribute("berkay", optionalCategory.get());
                return "category/updatecategory";
            } else {
                return "redirect:/category";
            }
        } else {
            return "redirect:/access-denied";
        }
    }

    @PostMapping("/category/update/{id}")
    public String updateCategory(@PathVariable("id") int id, @ModelAttribute Category category) {
        if (hasAdminRole()) {
            category.setId(id);
            categoryRepository.save(category);
            return "redirect:/category";
        } else {
            return "redirect:/access-denied";
        }
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        if (hasAdminRole()) {
            categoryRepository.deleteById(id);
            return "redirect:/category";
        } else {
            return "redirect:/access-denied";
        }
    }

    private boolean hasAdminRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
