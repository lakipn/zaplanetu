package com.girlscancode.service;

import com.girlscancode.domain.Poen;
import com.girlscancode.repository.PoenRepository;
import com.girlscancode.service.dto.PoenDTO;
import com.girlscancode.service.mapper.PoenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Poen}.
 */
@Service
@Transactional
public class PoenService {

    private final Logger log = LoggerFactory.getLogger(PoenService.class);

    private final PoenRepository poenRepository;

    private final PoenMapper poenMapper;

    public PoenService(PoenRepository poenRepository, PoenMapper poenMapper) {
        this.poenRepository = poenRepository;
        this.poenMapper = poenMapper;
    }

    /**
     * Save a poen.
     *
     * @param poenDTO the entity to save.
     * @return the persisted entity.
     */
    public PoenDTO save(PoenDTO poenDTO) {
        log.debug("Request to save Poen : {}", poenDTO);
        Poen poen = poenMapper.toEntity(poenDTO);
        poen = poenRepository.save(poen);
        return poenMapper.toDto(poen);
    }

    /**
     * Get all the poens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PoenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Poens");
        return poenRepository.findAll(pageable)
            .map(poenMapper::toDto);
    }


    /**
     * Get one poen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PoenDTO> findOne(Long id) {
        log.debug("Request to get Poen : {}", id);
        return poenRepository.findById(id)
            .map(poenMapper::toDto);
    }

    /**
     * Delete the poen by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Poen : {}", id);
        poenRepository.deleteById(id);
    }
}
