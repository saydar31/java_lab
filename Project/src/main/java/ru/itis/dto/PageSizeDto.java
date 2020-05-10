package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PageSizeDto {
    @Min(value = 0)
    private Integer p;
    @Min(value = 1)
    private Integer s;
}
