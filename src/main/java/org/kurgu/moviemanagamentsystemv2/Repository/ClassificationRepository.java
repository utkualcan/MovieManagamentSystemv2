package org.kurgu.moviemanagamentsystemv2.Repository;

import org.kurgu.moviemanagamentsystemv2.Model.Category;
import org.kurgu.moviemanagamentsystemv2.Model.Classification;
import org.kurgu.moviemanagamentsystemv2.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassificationRepository extends JpaRepository<Classification, Integer> {
    Classification findByCategoryAndMovie(Category category, Movie movie);
}
