package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.UploadFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UploadFileDto {
    private Long id;
    private String originalName;
    private String currentPath;
    private UserDto userDto;

    public static UploadFileDto from(UploadFile uploadFile) {
        return new UploadFileDto(uploadFile.getId(), uploadFile.getOriginalName(), uploadFile.getCurrentPath(), UserDto.from(uploadFile.getUser()));
    }
}
