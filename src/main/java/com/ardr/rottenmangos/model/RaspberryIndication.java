package com.ardr.rottenmangos.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/*
SwaggerConfig.java: André Ramos - 07/11/12
Save the registrys of the Files
*/

@Entity
@Table(name = "RASPBERRY_INDICATION")
public class RaspberryIndication {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_raspberry_file", nullable = false)
    @Getter
    @Setter
    private RaspberryFile raspberryFile;

    @NotNull
    @Getter
    @Setter
    private Long year;

    @NotNull
    @Getter
    @Setter
    private String title;

    @NotNull
    @Getter
    @Setter
    private String studios;

    @NotNull
    @Getter
    @Setter
    private String producers;

    @Getter
    @Setter
    private String winner;



}
