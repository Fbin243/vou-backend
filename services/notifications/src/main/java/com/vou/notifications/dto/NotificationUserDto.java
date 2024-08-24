package com.vou.notifications.dto;

import com.vou.notifications.common.ActiveStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationUserDto {
    private String notification_id;

    private String user_id;

    private boolean is_read;

    private ActiveStatus active_status;
}
