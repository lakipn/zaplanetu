package com.girlscancode.service;

import com.girlscancode.domain.Odgovor;
import com.girlscancode.repository.OdgovorRepository;
import com.girlscancode.service.dto.OdgovorDTO;
import com.girlscancode.service.mapper.OdgovorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Odgovor}.
 */
@Service
@Transactional
public class OdgovorService {

    private final Logger log = LoggerFactory.getLogger(OdgovorService.class);

    private final OdgovorRepository odgovorRepository;

    private final OdgovorMapper odgovorMapper;

    public OdgovorService(OdgovorRepository odgovorRepository, OdgovorMapper odgovorMapper) {
        this.odgovorRepository = odgovorRepository;
        this.odgovorMapper = odgovorMapper;
    }

    /**
     * Save a odgovor.
     *
     * @param odgovorDTO the entity to save.
     * @return the persisted entity.
     */
    public OdgovorDTO save(OdgovorDTO odgovorDTO) {
        log.debug("Request to save Odgovor : {}", odgovorDTO);
        Odgovor odgovor = odgovorMapper.toEntity(odgovorDTO);
        odgovor = odgovorRepository.save(odgovor);
        return odgovorMapper.toDto(odgovor);
    }

    /**
     * Get all the odgovors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OdgovorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Odgovors");
        return odgovorRepository.findAll(pageable)
            .map(odgovorMapper::toDto);
    }


    /**
     * Get one odgovor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OdgovorDTO> findOne(Long id) {
        log.debug("Request to get Odgovor : {}", id);
        return odgovorRepository.findById(id)
            .map(odgovorMapper::toDto);
    }

    /**
     * Delete the odgovor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Odgovor : {}", id);
        odgovorRepository.deleteById(id);
    }
}
