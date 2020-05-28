package ru.itis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dto.UserDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "project_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password_hash")
    private String passWordHash;
    @Column(name = "is_proofed")
    private boolean proofed;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "age")
    private Integer age;
    @Column(name = "experience")
    private Integer experience;

    @Transient
    private Double ageExperiencePercentage;

    @Transient
    private Integer joblessYears;

    @PostLoad
    public void init() {
        if (age != null && experience != null) {
            ageExperiencePercentage = (1.0 * experience) / age;
            joblessYears = age - experience;
        }
    }
}
