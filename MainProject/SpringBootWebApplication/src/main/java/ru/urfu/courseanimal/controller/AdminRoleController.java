package ru.urfu.courseanimal.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.urfu.courseanimal.entity.Role;
import ru.urfu.courseanimal.repository.RoleRepository;
import ru.urfu.courseanimal.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/roles")
public class AdminRoleController {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public AdminRoleController(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo; this.roleRepo = roleRepo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("roles", java.util.List.of("ROLE_READ_ONLY","ROLE_USER","ROLE_ADMIN"));
        return "admin/roles";
    }

    @PostMapping("/grant")
    public String grant(@RequestParam Integer userId, @RequestParam String roleName) {
        var user = userRepo.findById(userId).orElseThrow();
        var role = roleRepo.findByName(roleName);
        if (role == null) { role = roleRepo.save(new Role(null, roleName, new ArrayList<>())); }
        if (user.getRoles().stream().noneMatch(r -> r.getName().equals(roleName))) {
            user.getRoles().add(role);
        }
        userRepo.save(user);
        return "redirect:/admin/roles?ok";
    }
}