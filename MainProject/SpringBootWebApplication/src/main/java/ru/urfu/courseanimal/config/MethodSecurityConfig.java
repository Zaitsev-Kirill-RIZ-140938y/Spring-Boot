package ru.urfu.courseanimal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // явно включаем @PreAuthorize/@PostAuthorize
public class MethodSecurityConfig { }