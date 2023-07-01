package com.kams.UserService.auth;

import com.kams.UserService.config.JwtService;
import com.kams.UserService.user.entity.User;
import com.kams.UserService.user.exception.UserWithUsernameAlreadyExist;
import com.kams.UserService.user.exception.UserWithUsernameNotFoundException;
import com.kams.UserService.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    AuthenticationResponse register(RegisterRequest request) {
        userRepository.findByUsername(request.username())
                .ifPresent(user -> {throw new UserWithUsernameAlreadyExist(user.getUsername());});
        User user = User.builder()
                .username(request.username())
                .age(request.age())
                .password(passwordEncoder.encode(request.password()))
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isEnabled(true)
                .isCredentialsNonExpired(true)
                .build();
        user = userRepository.save(user);
        return generateAuthenticationResponseWithTokens(user);
    }

    AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UserWithUsernameNotFoundException(request.username()));
        return generateAuthenticationResponseWithTokens(user);
    }

    private AuthenticationResponse generateAuthenticationResponseWithTokens(User user){
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .userId(user.getId())
                .build();
    }

}
