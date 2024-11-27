package com.dashtap.DASHTAP.models;

import com.dashtap.DASHTAP.models.Enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleEnum name;

    public Role(RoleEnum name){
        this.name = name;
    }
}

