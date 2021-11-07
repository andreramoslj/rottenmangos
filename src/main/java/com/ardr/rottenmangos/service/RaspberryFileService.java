package com.ardr.rottenmangos.service;

import com.ardr.rottenmangos.model.RaspberryFile;
import com.ardr.rottenmangos.repository.RaspberryFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaspberryFileService {

    @Autowired
    private RaspberryFileRepository raspberryFileRepository;

    public RaspberryFile salvar(RaspberryFile raspberryFile) {
        return raspberryFileRepository.save(raspberryFile);
    }

    public List<RaspberryFile> getAll() {
        return raspberryFileRepository.findAll(Sort.by("createdAt"));
    }
}

