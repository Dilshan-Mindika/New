package com.dashtap.DASHTAP.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Min(5)
    private String name;

    @NotBlank
    @Size(min = 5, max = 50)
    @Email
    private String email;

    @NotBlank
    private Integer nic;

    @NotBlank
    @Min(10)
    private Integer number;

    @NotBlank
    @Min(10)
    private String address;

    @NotBlank
    @Size(min = 5, max = 120)
    @JsonIgnore
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private List<D_Rental> d_Rentals = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private List<V_Rental> v_Rentals = new ArrayList<>();

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password, Role role, Integer nic, Integer number, String name, String address){ {
        this.username = username;
        this.name = name;
        this.email = email;
        this.nic = nic;
        this.number = number;
        this.address = address;
        this.password = password;
        this.role = role;
        }
    }

    public User(@NotBlank @Size(min = 3, max = 20) String username, @NotBlank @Size(min = 5, max = 50) @Email String email, String encode, Role role) {
    }
}

