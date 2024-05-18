package org.kurgu.moviemanagamentsystemv2.Controller;

import org.kurgu.moviemanagamentsystemv2.Model.Movie;
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
    public String addMovie(@ModelAttribute Movie movie) {
        if (hasAdminRole()) {
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
    public String updateMovie(@PathVariable("id") int id, @ModelAttribute Movie movie) {
        if (hasAdminRole()) {
            movie.setId(id);
            movieRepository.save(movie);
            return "redirect:/movies";
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
}
