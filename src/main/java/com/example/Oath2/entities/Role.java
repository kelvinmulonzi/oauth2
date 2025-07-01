package com.example.Oath2.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // e.g., "ROLE_USER", "ROLE_ADMIN"
    @Column(unique = true, nullable = false)
    private String name;

    // Getters and Setters...
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
