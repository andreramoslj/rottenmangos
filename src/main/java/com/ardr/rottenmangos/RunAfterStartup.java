package com.ardr.rottenmangos;

import com.ardr.rottenmangos.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public class RunAfterStartup {

    @Autowired
    private ProcessService processoService;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        try {

            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("movielist.csv").getFile());
            MultipartFile multipartFile = null;

            multipartFile = new MockMultipartFile("movielist.csv", "movielist.csv", "csv", new FileInputStream(file));
            processoService.importFiles(multipartFile, "testFile");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}