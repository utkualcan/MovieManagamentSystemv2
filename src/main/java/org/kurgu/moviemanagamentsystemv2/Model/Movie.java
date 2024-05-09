package org.kurgu.moviemanagamentsystemv2.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", length = 32)
    private String title;

    @Column(name = "director", length = 16)
    private String director;

    @Column(name = "year")
    private int year;

    //------------------------------

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Classification> classifications;


}
