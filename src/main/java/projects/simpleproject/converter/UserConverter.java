package projects.simpleproject.converter;

import org.springframework.stereotype.Component;
import projects.simpleproject.domain.model.User;
import projects.simpleproject.web.command.RegisterUserCommand;

@Component
public class UserConverter {

    public User from(RegisterUserCommand registerUserCommand) {
        return User.builder()
                .username(registerUserCommand.getUsername())
                .password(registerUserCommand.getPassword())
                .build();

    }
}
