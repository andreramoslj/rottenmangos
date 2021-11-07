package com.ardr.rottenmangos.web.dto;

import lombok.Getter;
import lombok.Setter;


public class RaspberryIndicationQueryDTO {

    @Getter
    @Setter
    private String producer;

    @Getter
    @Setter
    private Long interval;

    @Getter
    @Setter
    private Long previousWin;

    @Getter
    @Setter
    private Long followingWin;

}
