package com.girlscancode.service;

import com.girlscancode.domain.Pojasnjenje;
import com.girlscancode.repository.PojasnjenjeRepository;
import com.girlscancode.service.dto.PojasnjenjeDTO;
import com.girlscancode.service.mapper.PojasnjenjeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Pojasnjenje}.
 */
@Service
@Transactional
public class PojasnjenjeService {

    private final Logger log = LoggerFactory.getLogger(PojasnjenjeService.class);

    private final PojasnjenjeRepository pojasnjenjeRepository;

    private final PojasnjenjeMapper pojasnjenjeMapper;

    public PojasnjenjeService(PojasnjenjeRepository pojasnjenjeRepository, PojasnjenjeMapper pojasnjenjeMapper) {
        this.pojasnjenjeRepository = pojasnjenjeRepository;
        this.pojasnjenjeMapper = pojasnjenjeMapper;
    }

    /**
     * Save a pojasnjenje.
     *
     * @param pojasnjenjeDTO the entity to save.
     * @return the persisted entity.
     */
    public PojasnjenjeDTO save(PojasnjenjeDTO pojasnjenjeDTO) {
        log.debug("Request to save Pojasnjenje : {}", pojasnjenjeDTO);
        Pojasnjenje pojasnjenje = pojasnjenjeMapper.toEntity(pojasnjenjeDTO);
        pojasnjenje = pojasnjenjeRepository.save(pojasnjenje);
        return pojasnjenjeMapper.toDto(pojasnjenje);
    }

    /**
     * Get all the pojasnjenjes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PojasnjenjeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pojasnjenjes");
        return pojasnjenjeRepository.findAll(pageable)
            .map(pojasnjenjeMapper::toDto);
    }



    /**
    *  Get all the pojasnjenjes where Odgovor is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<PojasnjenjeDTO> findAllWhereOdgovorIsNull() {
        log.debug("Request to get all pojasnjenjes where Odgovor is null");
        return StreamSupport
            .stream(pojasnjenjeRepository.findAll().spliterator(), false)
            .filter(pojasnjenje -> pojasnjenje.getOdgovor() == null)
            .map(pojasnjenjeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one pojasnjenje by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PojasnjenjeDTO> findOne(Long id) {
        log.debug("Request to get Pojasnjenje : {}", id);
        return pojasnjenjeRepository.findById(id)
            .map(pojasnjenjeMapper::toDto);
    }

    /**
     * Delete the pojasnjenje by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pojasnjenje : {}", id);
        pojasnjenjeRepository.deleteById(id);
    }
}
