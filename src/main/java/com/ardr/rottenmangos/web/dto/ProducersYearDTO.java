package com.ardr.rottenmangos.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class ProducersYearDTO implements Serializable {

    public ProducersYearDTO(Long year, String producers) {
        this.year = year;
        this.producers = producers;
    }

    @Getter
    @Setter
    private Long year;

    @Getter
    @Setter
    private String producers;


}
