package com.vou.users.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.vou.users.model.NotificationInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddUsersRequestDto {
    private NotificationInfo notification;
    private List<String> userIds;
}
