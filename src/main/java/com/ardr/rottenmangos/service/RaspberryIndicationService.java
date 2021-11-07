package com.ardr.rottenmangos.service;

import com.ardr.rottenmangos.model.RaspberryIndication;
import com.ardr.rottenmangos.repository.RaspberryIndicationRepository;
import com.ardr.rottenmangos.web.dto.ProducersYearDTO;
import com.ardr.rottenmangos.web.dto.RaspberryIndicationQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RaspberryIndicationService {

    @Autowired
    private RaspberryIndicationRepository raspberryIndicationRepository;

    public RaspberryIndication salvar(RaspberryIndication raspberryIndication) {
        return raspberryIndicationRepository.save(raspberryIndication);
    }

    @Async("threadPoolTaskExecutor")
    @Transactional
    public List<RaspberryIndication> saveAll(List<RaspberryIndication> listRaspberryIndications) {
        List<RaspberryIndication> result = raspberryIndicationRepository.saveAll(listRaspberryIndications);
        return result;
    }

    public List<RaspberryIndicationQueryDTO> getProducersIntervals(String orderBy) {
        List<RaspberryIndicationQueryDTO> dtoList = new ArrayList<RaspberryIndicationQueryDTO>();

        List<ProducersYearDTO> allIndicationsList = raspberryIndicationRepository.getAllWinnerProducers();
        if (allIndicationsList.isEmpty()) {
            return dtoList;
        }

        //Separates the producers that are together (Ex: 1 DTO with 'JOHN, PAUL AND MARK' becomes 3 DTO's
        allIndicationsList = orderListByUniqueProducers(allIndicationsList);


        //Group list by Producer's Name
        Map<String, List<ProducersYearDTO>> result = allIndicationsList.stream()
                .collect(Collectors.groupingBy(p -> p.getProducers(), Collectors.toList()));


        for (Map.Entry<String, List<ProducersYearDTO>> producerYearIndication : result.entrySet()) {
            RaspberryIndicationQueryDTO dtoProducer = null;

//          /LOOP IN THE PRODUCER'S LIST. Returns the minimun/maximum interval for each producers.
            for (ProducersYearDTO producersYearDTO : producerYearIndication.getValue()) {
                if ((producerYearIndication.getValue().indexOf(producersYearDTO) + 1) != producerYearIndication.getValue().size()) {
                    Long yearDTO = producersYearDTO.getYear();
                    Long followingYear = producerYearIndication.getValue().get(producerYearIndication.getValue().indexOf(producersYearDTO) + 1).getYear();
                    Long interval = followingYear - yearDTO;

                    boolean update = orderBy.equals("MIN")
                            ? (dtoProducer == null || dtoProducer.getInterval() > interval)
                            : (dtoProducer == null || dtoProducer.getInterval() < interval);
                    if (update) {
                        dtoProducer = new RaspberryIndicationQueryDTO();
                        dtoProducer.setProducer(producersYearDTO.getProducers());
                        dtoProducer.setPreviousWin(yearDTO);
                        dtoProducer.setFollowingWin(followingYear);
                        dtoProducer.setInterval(interval);
                    }
                }
            }
            if (dtoProducer != null) {
                dtoList.add(dtoProducer);
            }
        }

        Comparator<RaspberryIndicationQueryDTO> comparator = Comparator.comparingLong(RaspberryIndicationQueryDTO::getInterval);

        if (!orderBy.equals("MIN")) {
            comparator = comparator.reversed();
        }

        List<RaspberryIndicationQueryDTO> sortedList = dtoList.stream()
                .sorted(comparator)
                .collect(Collectors.toList());


        return sortedList;
    }

    private List<ProducersYearDTO> orderListByUniqueProducers(List<ProducersYearDTO> allIndicationsList) {
        List<ProducersYearDTO> newIndicationList = new ArrayList<ProducersYearDTO>();

        for (ProducersYearDTO dto : allIndicationsList) {
            String producersName = dto.getProducers();
            producersName = producersName.replaceAll(" and ", ",");
            String[] arrayNames = producersName.split(",");
            for (String name : arrayNames) {
                ProducersYearDTO newDto = new ProducersYearDTO(dto.getYear(), name);
                newIndicationList.add(newDto);
            }
        }

        return newIndicationList;
    }
}

