package com.ardr.rottenmangos.controller;


import com.ardr.rottenmangos.service.RaspberryIndicationService;
import com.ardr.rottenmangos.web.dto.RaspberryIndicationQueryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/raspberry-indications")
@CrossOrigin
@Api(value = "Raspberry Indications controller")
public class RaspberryIndicationController {

    @Autowired
    private RaspberryIndicationService raspberryIndicationService;


    @GetMapping("/intervals")
    @ApiOperation(value = "Get Producers Nominations Ranked by Intervals")
    @ResponseBody
    public ResponseEntity<?> importFiles(@RequestParam(value = "orderBy", required = true) String orderBy) {
        List<RaspberryIndicationQueryDTO> list = raspberryIndicationService.getProducersIntervals(orderBy);
        return ResponseEntity.ok().body(list);
    }


}
