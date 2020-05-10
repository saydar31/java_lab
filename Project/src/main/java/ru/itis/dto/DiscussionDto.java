package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiscussionDto {
    @NotEmpty
    private String name;
    @Pattern(regexp = "(.+;)+")
    private String tags;
}
