package org.kurgu.moviemanagamentsystemv2.Repository;

import org.kurgu.moviemanagamentsystemv2.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
