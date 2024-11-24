package fs19.java.backend.application;

import fs19.java.backend.application.dto.auth.AuthResponseDTO;
import fs19.java.backend.application.dto.auth.LoginRequestDTO;
import fs19.java.backend.application.dto.auth.SignupRequestDTO;
import fs19.java.backend.application.mapper.UserMapper;
import fs19.java.backend.config.JwtValidator;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.AuthRepoImpl;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import fs19.java.backend.presentation.shared.exception.UserAlreadyFoundException;
import fs19.java.backend.presentation.shared.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Responsible to handle auth actions
 */
@Service
public class AuthServiceImpl {

    private final AuthRepoImpl authRepo;
    private PasswordEncoder passwordEncoder;
    private final JwtValidator jwtValidator;

    public AuthServiceImpl(AuthRepoImpl authRepo, PasswordEncoder passwordEncoder, JwtValidator jwtValidator) {
        this.authRepo = authRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtValidator = jwtValidator;
    }

    /**
     * Sign up the user
     * @param signupRequestDTO
     * @return
     */
    public User signup(SignupRequestDTO signupRequestDTO) {
        String email = signupRequestDTO.email();
        Optional<User> existingUser = authRepo.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new UserAlreadyFoundException("Given Email Already exist, Can't create a new User record ");
        }
        User user = new User();
        user.setFirstName(signupRequestDTO.firstName());
        user.setLastName(signupRequestDTO.LastName());
        user.setEmail(signupRequestDTO.email());
        user.setPassword(passwordEncoder.encode(signupRequestDTO.password()));
        user.setCreatedDate(DateAndTime.getDateAndTime());
        return authRepo.saveUser(user);
    }

    /**
     * Authenticate the login access
     * @param request
     * @return
     */
    public AuthResponseDTO authenticate(LoginRequestDTO request) {
        Optional<User> user = authRepo.findByEmail(request.email());
        if (user.isEmpty()) {
            throw new UserNotFoundException("Given Email Not exist: User Not Found");
        }
        String accessToken = jwtValidator.generateToken(user.get());
        return UserMapper.toAuthResponseDTO(user.get(), accessToken);
    }
}
