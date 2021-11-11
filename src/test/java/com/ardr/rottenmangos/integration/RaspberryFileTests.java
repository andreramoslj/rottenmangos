package com.ardr.rottenmangos.integration;

import com.ardr.rottenmangos.model.enumeration.RaspberryFileStatus;
import com.ardr.rottenmangos.repository.RaspberryFileRepository;
import com.ardr.rottenmangos.repository.RaspberryIndicationRepository;
import com.ardr.rottenmangos.service.ProcessService;
import com.ardr.rottenmangos.web.dto.ProducersYearDTO;
import com.ardr.rottenmangos.web.dto.RaspberryIndicationQueryDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class RaspberryFileTests {
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;


    @Autowired
    private ProcessService processoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RaspberryFileRepository raspberryFileRepository;

    @Autowired
    RaspberryIndicationRepository raspberryIndicationRepository;

    @Autowired
    private ThreadPoolTaskExecutor myTaskExecutor;

    @Test
    public void importFileSuccessfully() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("movielist.csv").getFile());
            MultipartFile multipartFile = new MockMultipartFile("movielist.csv","movielist.csv", "csv",new FileInputStream(file));
            processoService.importFiles(multipartFile,"testFile");
            this.myTaskExecutor.getThreadPoolExecutor().awaitTermination(1, TimeUnit.SECONDS);
            Assertions.assertThat(raspberryFileRepository.findAll().get(0).getName()).isEqualTo("testFile");
            Assertions.assertThat(raspberryFileRepository.findAll().get(0).getStatus()).isEqualTo(RaspberryFileStatus.CONCLUIDO);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}
