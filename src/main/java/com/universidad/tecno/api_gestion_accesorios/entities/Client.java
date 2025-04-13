package com.universidad.tecno.api_gestion_accesorios.entities;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private LocalDate fechaNaciemiento;

    private boolean enabled;
    private String preferredTheme;
    private boolean autoThemeByHour;

    
    public Client() {
    }
    
    public Client(Long id, String name, String username, String password, String email, boolean enabled,
            String preferredTheme, boolean autoThemeByHour) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.preferredTheme = preferredTheme;
        this.autoThemeByHour = autoThemeByHour;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getPreferredTheme() {
        return preferredTheme;
    }
    public void setPreferredTheme(String preferredTheme) {
        this.preferredTheme = preferredTheme;
    }
    public boolean isAutoThemeByHour() {
        return autoThemeByHour;
    }
    public void setAutoThemeByHour(boolean autoThemeByHour) {
        this.autoThemeByHour = autoThemeByHour;
    }

    public LocalDate getFechaNaciemiento() {
        return fechaNaciemiento;
    }

    public void setFechaNaciemiento(LocalDate fechaNaciemiento) {
        this.fechaNaciemiento = fechaNaciemiento;
    }

    

}
