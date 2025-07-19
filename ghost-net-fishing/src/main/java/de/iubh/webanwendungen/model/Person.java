package de.iubh.webanwendungen.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotBlank(message = "Telefonnummer darf nicht leer sein")
    @Pattern(
        regexp = "^[0-9+\\-\\s()]{6,20}$",
        message = "Bitte gib eine gültige Telefonnummer ein (nur Ziffern, Leerzeichen, +, - oder Klammern)"
    )
    private String phone;

    private boolean anonymous;

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public boolean isAnonymous() { return anonymous; }
    public void setAnonymous(boolean anonymous) { this.anonymous = anonymous; }
}