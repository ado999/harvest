package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.exeption.InvalidTokenException;
import pl.azebrow.harvest.model.PasswordRecoveryToken;
import pl.azebrow.harvest.model.User;
import pl.azebrow.harvest.repository.PasswordRecoveryTokenRepository;
import pl.azebrow.harvest.repository.UserRepository;
import pl.azebrow.harvest.request.PasswordChangeRequest;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordRecoveryService {

    private final PasswordRecoveryTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createPasswordRecoveryToken(String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isEmpty()) {
            return;
        }
        User user = optional.get();
        PasswordRecoveryToken token = new PasswordRecoveryToken(user);
        tokenRepository.save(token);
    }

    public void recoverPassword(String tokenString, PasswordChangeRequest request) {
        PasswordRecoveryToken token = tokenRepository
                .findByToken(tokenString)
                .orElseThrow(
                        () -> new InvalidTokenException("Token not found!"));
        if (token.getExpiryDate() == null || token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Token expired");
        }
        User user = token.getUser();
        if (!user.getEmail().equals(request.getEmail())) {
            throw new InvalidTokenException("Email not valid!");
        }
        //todo password validation
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        tokenRepository.delete(token);
    }
}
