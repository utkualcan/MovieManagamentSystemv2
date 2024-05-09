package org.kurgu.moviemanagamentsystemv2.Repository;

import org.kurgu.moviemanagamentsystemv2.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
