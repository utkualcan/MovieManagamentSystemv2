package org.kurgu.moviemanagamentsystemv2.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",length = 16)
    private String name;

    //----------------------------

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Classification> classifications;
}
