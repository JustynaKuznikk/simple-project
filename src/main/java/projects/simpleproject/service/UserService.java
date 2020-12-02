package projects.simpleproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projects.simpleproject.converter.UserConverter;
import projects.simpleproject.domain.model.User;
import projects.simpleproject.exception.UserAlreadyExistExcepton;
import projects.simpleproject.domain.repository.UserRepository;
import projects.simpleproject.web.command.RegisterUserCommand;

import java.util.Set;

@Service @Transactional @Slf4j @RequiredArgsConstructor
public class UserService {
    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public Long create(RegisterUserCommand registerUserCommand) {
        log.debug("Dane użytkownika do zapisania: {}", registerUserCommand);
        User userToCreate = userConverter.from(registerUserCommand);
        log.debug("Uzyskany obiekt użytkownika do zapisu: {}", userToCreate);
        if(userRepository.existsByUsername(userToCreate.getUsername())){
            log.debug("Próba rejestracji na istniejącego użytkownika");
            throw new UserAlreadyExistExcepton(String.format("Użytkownik %s już istnieje",
                    userToCreate.getUsername()));
        }
        userToCreate.setActive(Boolean.TRUE);
        userToCreate.setRoles(Set.of("ROLE_USER"));
        userToCreate.setPassword(passwordEncoder.encode(userToCreate.getPassword()));
        userRepository.save(userToCreate);
        log.debug("Zapisany użytkownik: {}", userToCreate);
        return userToCreate.getId();
    }
}
