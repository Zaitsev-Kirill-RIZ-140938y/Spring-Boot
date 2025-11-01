package ru.urfu.courseanimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.urfu.courseanimal.entity.Role;
import ru.urfu.courseanimal.entity.User;
import ru.urfu.courseanimal.repository.RoleRepository;
import ru.urfu.courseanimal.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CourseAnimalApplication {

    @Bean
    CommandLineRunner seed(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder enc) {
        return args -> {
            var ro   = roleRepo.findByName("ROLE_READ_ONLY");
            if (ro == null)   ro = roleRepo.save(new Role(null, "ROLE_READ_ONLY", new java.util.ArrayList<>()));
            var ruser= roleRepo.findByName("ROLE_USER");
            if (ruser == null) ruser = roleRepo.save(new Role(null, "ROLE_USER", new java.util.ArrayList<>()));
            var radmin= roleRepo.findByName("ROLE_ADMIN");
            if (radmin == null) radmin = roleRepo.save(new Role(null, "ROLE_ADMIN", new java.util.ArrayList<>()));

            if (userRepo.findByEmail("admin@example.com") == null) {
                var admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@example.com");
                admin.setPassword(enc.encode("admin"));
                admin.setRoles(java.util.List.of(radmin));
                userRepo.save(admin);
            }
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(CourseAnimalApplication.class, args);
    }

}
