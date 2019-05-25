package com.girlscancode.service;

import com.girlscancode.domain.Drzava;
import com.girlscancode.repository.DrzavaRepository;
import com.girlscancode.service.dto.DrzavaDTO;
import com.girlscancode.service.mapper.DrzavaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Drzava}.
 */
@Service
@Transactional
public class DrzavaService {

    private final Logger log = LoggerFactory.getLogger(DrzavaService.class);

    private final DrzavaRepository drzavaRepository;

    private final DrzavaMapper drzavaMapper;

    public DrzavaService(DrzavaRepository drzavaRepository, DrzavaMapper drzavaMapper) {
        this.drzavaRepository = drzavaRepository;
        this.drzavaMapper = drzavaMapper;
    }

    /**
     * Save a drzava.
     *
     * @param drzavaDTO the entity to save.
     * @return the persisted entity.
     */
    public DrzavaDTO save(DrzavaDTO drzavaDTO) {
        log.debug("Request to save Drzava : {}", drzavaDTO);
        Drzava drzava = drzavaMapper.toEntity(drzavaDTO);
        drzava = drzavaRepository.save(drzava);
        return drzavaMapper.toDto(drzava);
    }

    /**
     * Get all the drzavas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DrzavaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Drzavas");
        return drzavaRepository.findAll(pageable)
            .map(drzavaMapper::toDto);
    }


    /**
     * Get one drzava by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DrzavaDTO> findOne(Long id) {
        log.debug("Request to get Drzava : {}", id);
        return drzavaRepository.findById(id)
            .map(drzavaMapper::toDto);
    }

    /**
     * Delete the drzava by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Drzava : {}", id);
        drzavaRepository.deleteById(id);
    }
}
