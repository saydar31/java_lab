package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.SupportChatDialog;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SupportChatDialogDto {
    private Long id;
    private List<SupportChatMessageDto> messages;
    private boolean containsUnreadMessages;
    private UserDto userDto;

    public static SupportChatDialogDto from(SupportChatDialog supportChatDialog) {
        return SupportChatDialogDto.builder()
                .id(supportChatDialog.getId())
                .messages(supportChatDialog.getMessages().stream().map(SupportChatMessageDto::from).collect(Collectors.toList()))
                .containsUnreadMessages(supportChatDialog.isContainsUnreadMessages())
                .userDto(UserDto.from(supportChatDialog.getClient()))
                .build();
    }
}
