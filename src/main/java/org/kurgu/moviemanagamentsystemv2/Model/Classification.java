package org.kurgu.moviemanagamentsystemv2.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="classification")
public class Classification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="date")
    private LocalDate date = LocalDate.now();

    @Column(name ="isdeleted", nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    //---------------------

    @ManyToOne
    @JoinColumn(name="movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;


}
