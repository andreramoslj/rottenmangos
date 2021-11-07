package com.ardr.rottenmangos.controller;


import com.ardr.rottenmangos.payload.ApiResponse;
import com.ardr.rottenmangos.service.ProcessService;
import com.ardr.rottenmangos.web.dto.RasperryFileDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/process")
@CrossOrigin
@Api(value = "Process controller")
public class ProcessController {

    @Autowired
    private ProcessService processoService;


    @PostMapping("/import")
    @ApiOperation(value = "Import Files")
    @ResponseBody
    public ResponseEntity<?> importFiles(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName) {
        this.importFilesAsyc(file, fileName);
        return ResponseEntity.ok()
                .body(new ApiResponse(true, "Import process started"));
    }


    @Async("threadPoolTaskExecutor")
    public void importFilesAsyc(MultipartFile file, String fileName) {
        List<RasperryFileDTO> list = processoService.importFiles(file, fileName);
    }


}
