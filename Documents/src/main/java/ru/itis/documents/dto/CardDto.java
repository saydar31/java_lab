package ru.itis.documents.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDto {
    private String number;
    private String cvv;
    private Integer dueToMonth;
    private Integer dueToYear;
}
