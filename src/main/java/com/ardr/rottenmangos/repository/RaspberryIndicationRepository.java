package com.ardr.rottenmangos.repository;

import com.ardr.rottenmangos.model.RaspberryIndication;
import com.ardr.rottenmangos.web.dto.ProducersYearDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RaspberryIndicationRepository extends JpaRepository<RaspberryIndication, Long> {
    Optional<RaspberryIndication> findById(Long id);

    List<RaspberryIndication> findByTitleIgnoreCaseContaining(String title);

    List<RaspberryIndication> findByWinner(String winner);

    @Query("Select new com.ardr.rottenmangos.web.dto.ProducersYearDTO(rasp.year,rasp.producers ) " +
            "FROM RaspberryIndication AS rasp " +
            "WHERE rasp.winner = 'yes' " +
            "order by year asc")
    List<ProducersYearDTO> getAllWinnerProducers();
}
