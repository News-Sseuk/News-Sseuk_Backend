package backend.newssseuk.domain.user.service;

import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.user.web.request.CustomUserDetails;
import backend.newssseuk.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            return createUserDetails(user);
        }

        return null;
    }

    private CustomUserDetails createUserDetails(User user) {
        return CustomUserDetails.builder()
                .username(user.getName())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
