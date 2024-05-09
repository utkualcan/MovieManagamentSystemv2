package org.kurgu.moviemanagamentsystemv2.Controller;

import org.kurgu.moviemanagamentsystemv2.Model.Movie;
import org.kurgu.moviemanagamentsystemv2.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public MovieController(MovieRepository movieRepository) {this.movieRepository=movieRepository;}

    @GetMapping("/movies")
    public String getMovies(Model model){
        Iterable<Movie> movies = movieRepository.findAll();
        model.addAttribute("movies",movies);
        return "/movies/index";
    }
    @GetMapping("/movies/add")
    public String addMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "/movies/addmovie";
    }

    @PostMapping("/movies/add")
    public String addMovie(@ModelAttribute Movie movie) {
        movieRepository.save(movie);
        return "redirect:/movies";
    }

    @GetMapping("/movies/update/{id}")
    public String updateMovieForm(@PathVariable("id") int id, Model model) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            model.addAttribute("movie", optionalMovie.get());
            return "/movies/updatemovie";
        } else {
            return "redirect:/movies";
        }
    }

    @PostMapping("/movies/update/{id}")
    public String updateMovie(@PathVariable("id") int id, @ModelAttribute Movie movie) {
        movie.setId(id);
        movieRepository.save(movie);
        return "redirect:/movies";
    }

    @GetMapping("/movies/delete/{id}")
    public String deleteMovie(@PathVariable("id") int id) {
        movieRepository.deleteById(id);
        return "redirect:/movies";
    }
}
