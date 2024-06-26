package org.kurgu.moviemanagamentsystemv2.Repository;

import org.kurgu.moviemanagamentsystemv2.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT u FROM User u WHERE u.username = :username")
    User getUserByUsername(String username);
}
