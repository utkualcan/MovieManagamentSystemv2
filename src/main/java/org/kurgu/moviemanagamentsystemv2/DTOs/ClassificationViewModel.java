package org.kurgu.moviemanagamentsystemv2.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kurgu.moviemanagamentsystemv2.Model.Category;
import org.kurgu.moviemanagamentsystemv2.Model.Classification;
import org.kurgu.moviemanagamentsystemv2.Model.Movie;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassificationViewModel {

    private Movie movie;
    private Category category;
    private Classification classification;
    private LocalDate date = LocalDate.now();


}