package com.ardr.rottenmangos.controller;


import com.ardr.rottenmangos.model.RaspberryFile;
import com.ardr.rottenmangos.service.RaspberryFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/raspberry-files")
@CrossOrigin
@Api(value = "Raspberry Files controller")
public class RaspberryfileController {

    @Autowired
    private RaspberryFileService raspberryFileService;


    @GetMapping()
    @ApiOperation(value = "Get All files")
    @ResponseBody
    public ResponseEntity<?> get() {
        List<RaspberryFile> list = raspberryFileService.getAll();
        return ResponseEntity.ok().body(list);
    }


}
