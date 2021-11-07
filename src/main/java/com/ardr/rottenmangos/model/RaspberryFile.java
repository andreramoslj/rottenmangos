package com.ardr.rottenmangos.model;

import com.ardr.rottenmangos.model.enumeration.RaspberryFileStatus;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
SwaggerConfig.java: André Ramos - 07/11/12
Control of File Reading
*/

@Entity
@Table(name = "RASPBERRY_FILE")
public class RaspberryFile {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Getter
    @Setter
    String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "char(10)")
    @Getter
    @Setter
    RaspberryFileStatus status;

    @Column(name = "records_count")
    @NotNull
    @Getter
    @Setter
    Long recordsCount;


    @NotNull
    @Getter
    @Setter
    String note;


    @Column(name = "created_at")
    @NotNull
    @Getter
    @Setter
    LocalDateTime createdAt;

}
