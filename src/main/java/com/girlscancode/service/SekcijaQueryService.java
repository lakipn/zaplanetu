package com.girlscancode.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.girlscancode.domain.Sekcija;
import com.girlscancode.domain.*; // for static metamodels
import com.girlscancode.repository.SekcijaRepository;
import com.girlscancode.service.dto.SekcijaCriteria;
import com.girlscancode.service.dto.SekcijaDTO;
import com.girlscancode.service.mapper.SekcijaMapper;

/**
 * Service for executing complex queries for {@link Sekcija} entities in the database.
 * The main input is a {@link SekcijaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SekcijaDTO} or a {@link Page} of {@link SekcijaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SekcijaQueryService extends QueryService<Sekcija> {

    private final Logger log = LoggerFactory.getLogger(SekcijaQueryService.class);

    private final SekcijaRepository sekcijaRepository;

    private final SekcijaMapper sekcijaMapper;

    public SekcijaQueryService(SekcijaRepository sekcijaRepository, SekcijaMapper sekcijaMapper) {
        this.sekcijaRepository = sekcijaRepository;
        this.sekcijaMapper = sekcijaMapper;
    }

    /**
     * Return a {@link List} of {@link SekcijaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SekcijaDTO> findByCriteria(SekcijaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Sekcija> specification = createSpecification(criteria);
        return sekcijaMapper.toDto(sekcijaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SekcijaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SekcijaDTO> findByCriteria(SekcijaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Sekcija> specification = createSpecification(criteria);
        return sekcijaRepository.findAll(specification, page)
            .map(sekcijaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SekcijaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Sekcija> specification = createSpecification(criteria);
        return sekcijaRepository.count(specification);
    }

    /**
     * Function to convert SekcijaCriteria to a {@link Specification}.
     */
    private Specification<Sekcija> createSpecification(SekcijaCriteria criteria) {
        Specification<Sekcija> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Sekcija_.id));
            }
            if (criteria.getNaziv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNaziv(), Sekcija_.naziv));
            }
            if (criteria.getPitanjaId() != null) {
                specification = specification.and(buildSpecification(criteria.getPitanjaId(),
                    root -> root.join(Sekcija_.pitanjas, JoinType.LEFT).get(Pitanje_.id)));
            }
        }
        return specification;
    }
}
