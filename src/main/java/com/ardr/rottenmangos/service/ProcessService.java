package com.ardr.rottenmangos.service;

import com.ardr.rottenmangos.model.RaspberryFile;
import com.ardr.rottenmangos.model.RaspberryIndication;
import com.ardr.rottenmangos.model.enumeration.RaspberryFileStatus;
import com.ardr.rottenmangos.web.dto.RasperryFileDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessService.class);

    @Getter
    int errorLine = 0;

    @Getter
    int errorColumn = 0;

    @Autowired
    RaspberryFileService raspberryFileService;

    @Autowired
    RaspberryIndicationService raspberryIndicationService;


    @Async("threadPoolTaskExecutor")
    public List<RasperryFileDTO> importFiles(MultipartFile file, String fileName) {
        logger.info("Starting import process. File name: " + fileName);
        List<RasperryFileDTO> list = null;
        RaspberryFile raspberryFile = null;
        try {
            if (!FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("CSV")) {
                throw new Exception("Error in reading file. Extension other than CSV.");
            }

            logger.info("Creating temp file...");
            File moviesFile = convertMultipartFileToFile(file);
            logger.info("Converting CSV to Object...");
            list = loadObjectList(RasperryFileDTO.class, moviesFile);
            logger.info("Saving file...");
            raspberryFile = createRaspberryFile(fileName, Long.valueOf(list.size()));

            if (CollectionUtils.isEmpty(list)) {
                throw new Exception("Error reading file. Check that the format is correct. Position: (Row: " + errorLine + ". Col: " + errorColumn + ")");
            } else {

                List<RaspberryIndication> listRaspberryIndications = new ArrayList<RaspberryIndication>();
                logger.info("Iniciando processo de leitura");
                for (RasperryFileDTO dto : list) {
                    logger.info("montando dto " + dto.getTitle());
                    if (!StringUtils.isEmpty(dto.getTitle())) {

                        ModelMapper modelMapper = new ModelMapper();
                        RaspberryIndication raspberryIndication = modelMapper.map(dto, RaspberryIndication.class);
                        raspberryIndication.setRaspberryFile(raspberryFile);

                        listRaspberryIndications.add(raspberryIndication);
                    }
                }

                logger.info("Saving Indications");
                raspberryIndicationService.saveAll(listRaspberryIndications);
                logger.info("IMPORTED SUCCESSFULLY!");
                updateRaspberryFileStatus(raspberryFile, RaspberryFileStatus.CONCLUIDO, "Arquivos importados com sucesso");
            }
        } catch (Exception ex) {
            logger.error("ERROR IMPORTING FILE: " + ex.getMessage());
            logger.error("--- ");
            ex.printStackTrace();
            if (raspberryFile != null) {
                updateRaspberryFileStatus(raspberryFile, RaspberryFileStatus.ERRO, "ERROR: "+ex.getMessage());
            }
        }

        return list;
    }

    @Async("threadPoolTaskExecutor")
    private <T> List<T> loadObjectList(Class<T> type, File moviesFile) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
            CsvMapper mapper = new CsvMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            MappingIterator<T> readValues =
                    mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true).reader(type).with(bootstrapSchema).readValues(moviesFile);
            return readValues.readAll();
        } catch (JsonParseException e) {
            logger.error("erro ao converter para JSON: " + e.getMessage());
            errorLine = e.getLocation().getLineNr();
            errorColumn = e.getLocation().getColumnNr();
            return Collections.emptyList();
        } catch (Exception e) {
            logger.error("erro ao converter para Object 2: " + e.getMessage());
            return Collections.emptyList();
        }

    }

    @Async("threadPoolTaskExecutor")
    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = File.createTempFile("arquivoimportacao", "csv");
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @Async("threadPoolTaskExecutor")
    private RaspberryFile createRaspberryFile(String fileName, Long recordsCount) {
        RaspberryFile raspberryFile = new RaspberryFile();
        raspberryFile.setName(fileName);
        raspberryFile.setNote("Inicialized process");
        raspberryFile.setStatus(RaspberryFileStatus.PENDENTE);
        raspberryFile.setCreatedAt(LocalDateTime.now());
        raspberryFile.setRecordsCount(recordsCount);

        raspberryFileService.salvar(raspberryFile);
        return raspberryFile;
    }

    @Async("threadPoolTaskExecutor")
    private void updateRaspberryFileStatus(RaspberryFile raspberryFile, RaspberryFileStatus status, String note) {
        raspberryFile.setNote(note);
        raspberryFile.setStatus(status);
        raspberryFileService.salvar(raspberryFile);
    }

}

