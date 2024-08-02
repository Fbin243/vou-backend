package com.vou.events.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventDto {
    private String id;
    
    private String name;

    private String image;

    private int numberOfVoucher;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}