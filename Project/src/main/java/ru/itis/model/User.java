package ru.itis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.dto.UserDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String passWordHash;
    private boolean proofed;
    private Role role;

    public static User from(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .proofed(userDto.isProofed())
                .role(userDto.getRole())
                .build();
    }
}
