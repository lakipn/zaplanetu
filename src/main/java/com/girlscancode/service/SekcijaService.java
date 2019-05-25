package com.girlscancode.service;

import com.girlscancode.domain.Sekcija;
import com.girlscancode.repository.SekcijaRepository;
import com.girlscancode.service.dto.SekcijaDTO;
import com.girlscancode.service.mapper.SekcijaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Sekcija}.
 */
@Service
@Transactional
public class SekcijaService {

    private final Logger log = LoggerFactory.getLogger(SekcijaService.class);

    private final SekcijaRepository sekcijaRepository;

    private final SekcijaMapper sekcijaMapper;

    public SekcijaService(SekcijaRepository sekcijaRepository, SekcijaMapper sekcijaMapper) {
        this.sekcijaRepository = sekcijaRepository;
        this.sekcijaMapper = sekcijaMapper;
    }

    /**
     * Save a sekcija.
     *
     * @param sekcijaDTO the entity to save.
     * @return the persisted entity.
     */
    public SekcijaDTO save(SekcijaDTO sekcijaDTO) {
        log.debug("Request to save Sekcija : {}", sekcijaDTO);
        Sekcija sekcija = sekcijaMapper.toEntity(sekcijaDTO);
        sekcija = sekcijaRepository.save(sekcija);
        return sekcijaMapper.toDto(sekcija);
    }

    /**
     * Get all the sekcijas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SekcijaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sekcijas");
        return sekcijaRepository.findAll(pageable)
            .map(sekcijaMapper::toDto);
    }


    /**
     * Get one sekcija by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SekcijaDTO> findOne(Long id) {
        log.debug("Request to get Sekcija : {}", id);
        return sekcijaRepository.findById(id)
            .map(sekcijaMapper::toDto);
    }

    /**
     * Delete the sekcija by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sekcija : {}", id);
        sekcijaRepository.deleteById(id);
    }
}
