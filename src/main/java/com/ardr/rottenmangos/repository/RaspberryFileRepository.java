package com.ardr.rottenmangos.repository;

import com.ardr.rottenmangos.model.RaspberryFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RaspberryFileRepository extends JpaRepository<RaspberryFile, Long> {
    Optional<RaspberryFile> findById(Long id);

    List<RaspberryFile> findByNameIgnoreCaseContaining(String name);
}
