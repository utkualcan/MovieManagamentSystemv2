package org.kurgu.moviemanagamentsystemv2.Controller;

import org.kurgu.moviemanagamentsystemv2.Model.Movie;
import org.kurgu.moviemanagamentsystemv2.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Controller
public class MovieController {
    @Autowired
    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movies")
    public String getMovies(Model model) {
        Iterable<Movie> movies = movieRepository.findAll();
        model.addAttribute("movies", movies);
        return "/movies/index";
    }

    @GetMapping("/movies/add")
    public String addMovieForm(Model model) {
        if (hasAdminRole()) {
            model.addAttribute("movie", new Movie());
            return "/movies/addmovie";
        } else {
            return "redirect:/access-denied";
        }
    }

    @PostMapping("/movies/add")
    public String addMovie(@ModelAttribute Movie movie, @RequestParam(value = "image", required = false) MultipartFile file) {
        if (hasAdminRole()) {
            saveMovieWithImage(movie, file);
            movieRepository.save(movie);
            return "redirect:/movies";
        } else {
            return "redirect:/access-denied";
        }
    }

    @GetMapping("/movies/update/{id}")
    public String updateMovieForm(@PathVariable("id") int id, Model model) {
        if (hasAdminRole()) {
            Optional<Movie> optionalMovie = movieRepository.findById(id);
            if (optionalMovie.isPresent()) {
                model.addAttribute("movie", optionalMovie.get());
                return "/movies/updatemovie";
            } else {
                return "redirect:/movies";
            }
        } else {
            return "redirect:/access-denied";
        }
    }

    @PostMapping("/movies/update/{id}")
    public String updateMovie(@PathVariable("id") int id, @ModelAttribute Movie movie, @RequestParam(value = "image", required = false) MultipartFile file) {
        if (hasAdminRole()) {
            Optional<Movie> existingMovieOpt = movieRepository.findById(id);
            if (existingMovieOpt.isPresent()) {
                Movie existingMovie = existingMovieOpt.get();
                if (file != null && !file.isEmpty()) {
                    saveMovieWithImage(movie, file);
                } else {
                    movie.setImgURL(existingMovie.getImgURL()); // Keep the existing image URL if no new image is provided
                }
                movie.setId(id);
                movieRepository.save(movie);
                return "redirect:/movies";
            } else {
                return "redirect:/movies";
            }
        } else {
            return "redirect:/access-denied";
        }
    }

    @GetMapping("/movies/delete/{id}")
    public String deleteMovie(@PathVariable("id") int id) {
        if (hasAdminRole()) {
            movieRepository.deleteById(id);
            return "redirect:/movies";
        } else {
            return "redirect:/access-denied";
        }
    }

    private boolean hasAdminRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    private void saveMovieWithImage(Movie movie, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            movie.setImgURL(fileName);
            String uploadDir = "src/main/resources/static/images/" + fileName;
            Path uploadPath = Paths.get(uploadDir);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, uploadPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                System.out.println("File saving error! " + ex.toString());
            }
        } else {
            String defaultImage = "nophoto.png";
            movie.setImgURL(defaultImage);
            String defaultUploadDir = "src/main/resources/static/images/" + defaultImage;
            Path defaultUploadPath = Paths.get(defaultUploadDir);

            try (InputStream defaultImageStream = getClass().getClassLoader().getResourceAsStream("static/images/" + defaultImage)) {
                if (defaultImageStream != null) {
                    Files.copy(defaultImageStream, defaultUploadPath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    System.out.println("Default image not found!");
                }
            } catch (IOException ex) {
                System.out.println("Default file saving error! " + ex.toString());
            }
        }
    }
}
