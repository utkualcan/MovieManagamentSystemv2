package org.kurgu.moviemanagamentsystemv2.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="user")
public class User {
    @Id
    @Column(length = 16)
    private String username;

    private String password;

    @Column(length = 16)
    private String name;

    @Column(name="is_locked", columnDefinition = "boolean default false")
    private boolean isLocked;

    @Column(length = 8, nullable = false)
    private String role;
}
