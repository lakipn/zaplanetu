package com.girlscancode.service;

import com.girlscancode.domain.Pitanje;
import com.girlscancode.repository.PitanjeRepository;
import com.girlscancode.service.dto.PitanjeDTO;
import com.girlscancode.service.mapper.PitanjeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Pitanje}.
 */
@Service
@Transactional
public class PitanjeService {

    private final Logger log = LoggerFactory.getLogger(PitanjeService.class);

    private final PitanjeRepository pitanjeRepository;

    private final PitanjeMapper pitanjeMapper;

    public PitanjeService(PitanjeRepository pitanjeRepository, PitanjeMapper pitanjeMapper) {
        this.pitanjeRepository = pitanjeRepository;
        this.pitanjeMapper = pitanjeMapper;
    }

    /**
     * Save a pitanje.
     *
     * @param pitanjeDTO the entity to save.
     * @return the persisted entity.
     */
    public PitanjeDTO save(PitanjeDTO pitanjeDTO) {
        log.debug("Request to save Pitanje : {}", pitanjeDTO);
        Pitanje pitanje = pitanjeMapper.toEntity(pitanjeDTO);
        pitanje = pitanjeRepository.save(pitanje);
        return pitanjeMapper.toDto(pitanje);
    }

    /**
     * Get all the pitanjes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PitanjeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pitanjes");
        return pitanjeRepository.findAll(pageable)
            .map(pitanjeMapper::toDto);
    }


    /**
     * Get one pitanje by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PitanjeDTO> findOne(Long id) {
        log.debug("Request to get Pitanje : {}", id);
        return pitanjeRepository.findById(id)
            .map(pitanjeMapper::toDto);
    }

    /**
     * Delete the pitanje by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pitanje : {}", id);
        pitanjeRepository.deleteById(id);
    }
}
