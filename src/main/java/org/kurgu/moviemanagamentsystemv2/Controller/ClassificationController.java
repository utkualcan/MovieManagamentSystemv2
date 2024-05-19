package org.kurgu.moviemanagamentsystemv2.Controller;

import org.kurgu.moviemanagamentsystemv2.DTOs.ClassificationViewModel;
import org.kurgu.moviemanagamentsystemv2.Model.Category;
import org.kurgu.moviemanagamentsystemv2.Model.Classification;
import org.kurgu.moviemanagamentsystemv2.Model.Movie;
import org.kurgu.moviemanagamentsystemv2.Repository.CategoryRepository;
import org.kurgu.moviemanagamentsystemv2.Repository.ClassificationRepository;
import org.kurgu.moviemanagamentsystemv2.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ClassificationController {
    @Autowired
    ClassificationRepository classificationRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/classification")
    public String getClassification(Model model) {
        Iterable<Classification> classifications = classificationRepository.findAll();
        List<ClassificationViewModel> classificationViews = new ArrayList<>();
        for (Classification c : classifications) {
            if (!c.isDeleted()) {
                ClassificationViewModel cv = new ClassificationViewModel();
                cv.setCategory(categoryRepository.findById(c.getCategory().getId()).get());
                cv.setMovie(movieRepository.findById(c.getMovie().getId()).get());
                cv.setClassification(c);
                cv.setDate(c.getDate());
                classificationViews.add(cv);
            }
        }
        model.addAttribute("serhat", classificationViews);
        return "/classification/index";
    }

    @GetMapping("/classification/update/{id}")
    public String updateClassificationForm(@PathVariable("id") int id, Model model) {
        if (hasAdminRole()) {
            Optional<Classification> optionalClassification = classificationRepository.findById(id);
            Iterable<Movie> movies = movieRepository.findAll();
            Iterable<Category> categories = categoryRepository.findAll();
            if (optionalClassification.isPresent()) {
                model.addAttribute("serhat", optionalClassification.get());
                model.addAttribute("movies", movies);
                model.addAttribute("category", categories);
                return "/classification/updateclassification";
            } else {
                return "redirect:/classification";
            }
        } else {
            return "redirect:/access-denied";
        }
    }

    @PostMapping("/classification/update/{id}")
    public String updateClassification(@PathVariable("id") int id, @ModelAttribute Classification classification) {
        if (hasAdminRole()) {
            Optional<Classification> optionalClassification = classificationRepository.findById(id);
            if (optionalClassification.isPresent()) {
                Classification existingClassification = optionalClassification.get();
                existingClassification.setCategory(classification.getCategory());
                existingClassification.setMovie(classification.getMovie());
                existingClassification.setDate(classification.getDate());
                existingClassification.setDeleted(false);
                classificationRepository.save(existingClassification);
                return "redirect:/classification";
            }
            return "redirect:/classification"; // Redirect if classification does not exist
        } else {
            return "redirect:/access-denied";
        }
    }


    @GetMapping("classification/classificatecategory/{id}")
    public String classificateCategory(@PathVariable("id") int id, Model model) {
        if (hasAdminRole()) {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            Iterable<Movie> movies = movieRepository.findAll();
            Classification classification = new Classification();
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                classification.setCategory(category);
                model.addAttribute("category", category);
                model.addAttribute("movies", movies);
                model.addAttribute("classificate", classification);
                return "/classification/classificatecategory";
            } else {
                return "redirect:/classification";
            }
        } else {
            return "redirect:/access-denied";
        }
    }

    @PostMapping("classification/classificatecategory/{id}")
    public String classificateCategory(@PathVariable("id") int id, @ModelAttribute Classification classificate) {
        if (hasAdminRole()) {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (optionalCategory.isPresent()) {
                classificate.setCategory(optionalCategory.get());
                Classification existingClassification = classificationRepository.findByCategoryAndMovie(classificate.getCategory(), classificate.getMovie());
                if (existingClassification != null) {
                    if (existingClassification.isDeleted()) {
                        existingClassification.setDeleted(false);
                        classificationRepository.save(existingClassification);
                    }
                    return "redirect:/classification";
                }
                classificationRepository.save(classificate);
            }
            return "redirect:/classification";
        } else {
            return "redirect:/access-denied";
        }
    }



    @GetMapping("classification/classificatemovie/{id}")
    public String classificateMovie(@PathVariable("id") int id, Model model) {
        if (hasAdminRole()) {
            Optional<Movie> optionalMovie = movieRepository.findById(id);
            Iterable<Category> categories = categoryRepository.findAll();
            Classification classification = new Classification();
            if (optionalMovie.isPresent()) {
                Movie movie = optionalMovie.get();
                classification.setMovie(movie);
                model.addAttribute("movie", movie);
                model.addAttribute("categories", categories);
                model.addAttribute("classificate", classification);
                return "/classification/classificatemovie";
            } else {
                return "redirect:/classification";
            }
        } else {
            return "redirect:/access-denied";
        }
    }

    @PostMapping("classification/classificatemovie/{id}")
    public String classificateMovie(@PathVariable("id") int id, @ModelAttribute Classification classificate) {
        if (hasAdminRole()) {
            Optional<Movie> optionalMovie = movieRepository.findById(id);
            if (optionalMovie.isPresent()) {
                classificate.setMovie(optionalMovie.get());
                Classification existingClassification = classificationRepository.findByCategoryAndMovie(classificate.getCategory(), classificate.getMovie());
                if (existingClassification != null) {
                    if (existingClassification.isDeleted()) {
                        existingClassification.setDeleted(false);
                        classificationRepository.save(existingClassification);
                    }
                    return "redirect:/classification";
                }
                classificationRepository.save(classificate);
            }
            return "redirect:/classification";
        } else {
            return "redirect:/access-denied";
        }
    }



    @GetMapping("/classification/delete/{id}")
    public String deleteClassification(@PathVariable("id") int id) {
        if (hasAdminRole()) {
            Optional<Classification> optionalClassification = classificationRepository.findById(id);
            if (optionalClassification.isPresent()) {
                Classification classification = optionalClassification.get();
                classification.setDeleted(true);
                classificationRepository.save(classification);
            }
            return "redirect:/classification";
        } else {
            return "redirect:/access-denied";
        }
    }


    private boolean hasAdminRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
