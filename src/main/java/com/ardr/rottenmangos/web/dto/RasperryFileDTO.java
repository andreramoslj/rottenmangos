package com.ardr.rottenmangos.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class RasperryFileDTO implements Serializable {

    public RasperryFileDTO() {

    }

    @Getter
    @Setter
    private Long year;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String studios;

    @Getter
    @Setter
    private String producers;

    @Getter
    @Setter
    private String winner;


}
