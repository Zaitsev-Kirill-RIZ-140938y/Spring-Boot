package ru.urfu.courseanimal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.urfu.courseanimal.entity.Role;
import ru.urfu.courseanimal.entity.User;
import ru.urfu.courseanimal.repository.RoleRepository;
import ru.urfu.courseanimal.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(name = "app.admin.bootstrap", havingValue = "true")
public class AdminBootstrap implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    // Используется только если нужно создать нового админа
    @Value("${app.admin.password:admin}")
    private String adminPassword;

    public AdminBootstrap(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (adminEmail == null || adminEmail.isBlank()) {
            System.out.println("[AdminBootstrap] app.admin.email не указан — пропускаю.");
            return;
        }

        Role ro = ensureRole("ROLE_READ_ONLY");
        Role ru = ensureRole("ROLE_USER");
        Role ra = ensureRole("ROLE_ADMIN");

        User u = userRepo.findByEmail(adminEmail);
        if (u == null) {
            // создаём нового админа
            u = new User();
            u.setName("Admin");
            u.setEmail(adminEmail);
            u.setPassword(encoder.encode(adminPassword));
            u.setRoles(List.of(ra));
            userRepo.save(u);
            System.out.println("[AdminBootstrap] Создан новый админ: " + adminEmail);
        } else {
            boolean hasAdmin = u.getRoles().stream().anyMatch(r -> "ROLE_ADMIN".equals(r.getName()));
            if (!hasAdmin) {
                u.getRoles().add(ra);
                userRepo.save(u);
                System.out.println("[AdminBootstrap] Выдал ROLE_ADMIN пользователю: " + adminEmail);
            } else {
                System.out.println("[AdminBootstrap] У пользователя уже есть ROLE_ADMIN: " + adminEmail);
            }
        }

        // Подсказка, чтобы не забыть отключить сидер
        System.out.println("[AdminBootstrap] Готово. Перелогинься. Потом выключи app.admin.bootstrap=false");
    }

    private Role ensureRole(String name) {
        Role r = roleRepo.findByName(name);
        return (r != null) ? r : roleRepo.save(new Role(null, name, new ArrayList<>()));
    }
}