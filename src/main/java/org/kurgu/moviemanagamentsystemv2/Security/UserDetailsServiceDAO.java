package org.kurgu.moviemanagamentsystemv2.Security;

import org.kurgu.moviemanagamentsystemv2.Repository.UserRepository;
import org.kurgu.moviemanagamentsystemv2.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceDAO implements UserDetailsService {
    @Autowired
    private UserRepository ur;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //System.out.println("OK: " + username);
        User user = ur.getUserByUsername(username);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .accountLocked(user.isLocked())
                .disabled(false)
                .credentialsExpired(false)
                .build();
    }


}
