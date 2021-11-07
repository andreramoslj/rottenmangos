package com.ardr.rottenmangos.integration;

import com.ardr.rottenmangos.repository.RaspberryIndicationRepository;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class RaspberryIndicationTests {
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    @MockBean
    RaspberryIndicationRepository raspberryIndicationRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void returnProducerWithMinIntervalsWhenOrderByIsMin() {
        List<ProducersYearDTO> listProducers = new ArrayList<>();
        listProducers.add(new ProducersYearDTO(2000L, "JOHN"));
        listProducers.add(new ProducersYearDTO(2001L, "ROBERT"));
        listProducers.add(new ProducersYearDTO(2002L, "JOHN"));
        listProducers.add(new ProducersYearDTO(2003L, "SUE"));
        listProducers.add(new ProducersYearDTO(2004L, "POE"));
        listProducers.add(new ProducersYearDTO(2005L, "ROBERT, ERNEST"));
        BDDMockito.when(raspberryIndicationRepository.getAllWinnerProducers()).thenReturn(listProducers);
        RaspberryIndicationQueryDTO[] list = restTemplate.getForObject("/v1/raspberry-indications/intervals?orderBy=MIN", RaspberryIndicationQueryDTO[].class);
        String firstNameProducer = list[0].getProducer();
        Assertions.assertThat(firstNameProducer).isEqualTo("JOHN");
    }


    @Test
    public void returnProducerWithMinIntervalsWhenOrderByIsMax() {
        List<ProducersYearDTO> listProducers = new ArrayList<>();
        listProducers.add(new ProducersYearDTO(2000L, "JOHN"));
        listProducers.add(new ProducersYearDTO(2001L, "ROBERT"));
        listProducers.add(new ProducersYearDTO(2002L, "JOHN"));
        listProducers.add(new ProducersYearDTO(2003L, "SUE"));
        listProducers.add(new ProducersYearDTO(2004L, "POE"));
        listProducers.add(new ProducersYearDTO(2005L, "ROBERT, ERNEST"));
        BDDMockito.when(raspberryIndicationRepository.getAllWinnerProducers()).thenReturn(listProducers);
        RaspberryIndicationQueryDTO[] list = restTemplate.getForObject("/v1/raspberry-indications/intervals?orderBy=MAX", RaspberryIndicationQueryDTO[].class);
        String firstNameProducer = list[0].getProducer();
        Assertions.assertThat(firstNameProducer).isEqualTo("ROBERT");
    }
}
