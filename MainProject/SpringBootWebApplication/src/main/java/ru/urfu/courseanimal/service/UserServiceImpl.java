package ru.urfu.courseanimal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.urfu.courseanimal.dto.UserDto;
import ru.urfu.courseanimal.entity.Role;
import ru.urfu.courseanimal.entity.User;
import ru.urfu.courseanimal.repository.RoleRepository;
import ru.urfu.courseanimal.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName((userDto.getFirstName() == null ? "" : userDto.getFirstName()) + " " +
                (userDto.getLastName() == null ? "" : userDto.getLastName()));
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // дефолтная роль — READ_ONLY
        Role role = roleRepository.findByName("ROLE_READ_ONLY");
        if (role == null) {
            role = roleRepository.save(new Role(null, "ROLE_READ_ONLY", new ArrayList<>()));
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        String name = user.getName() == null ? "" : user.getName().trim();
        String[] parts = name.split(" ", 2);
        dto.setFirstName(parts.length > 0 ? parts[0] : "");
        dto.setLastName(parts.length > 1 ? parts[1] : "");
        dto.setEmail(user.getEmail());
        dto.setId(user.getId());
        return dto;
    }
}