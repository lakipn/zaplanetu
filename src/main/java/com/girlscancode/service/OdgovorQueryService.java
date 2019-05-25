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

import com.girlscancode.domain.Odgovor;
import com.girlscancode.domain.*; // for static metamodels
import com.girlscancode.repository.OdgovorRepository;
import com.girlscancode.service.dto.OdgovorCriteria;
import com.girlscancode.service.dto.OdgovorDTO;
import com.girlscancode.service.mapper.OdgovorMapper;

/**
 * Service for executing complex queries for {@link Odgovor} entities in the database.
 * The main input is a {@link OdgovorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OdgovorDTO} or a {@link Page} of {@link OdgovorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OdgovorQueryService extends QueryService<Odgovor> {

    private final Logger log = LoggerFactory.getLogger(OdgovorQueryService.class);

    private final OdgovorRepository odgovorRepository;

    private final OdgovorMapper odgovorMapper;

    public OdgovorQueryService(OdgovorRepository odgovorRepository, OdgovorMapper odgovorMapper) {
        this.odgovorRepository = odgovorRepository;
        this.odgovorMapper = odgovorMapper;
    }

    /**
     * Return a {@link List} of {@link OdgovorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OdgovorDTO> findByCriteria(OdgovorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Odgovor> specification = createSpecification(criteria);
        return odgovorMapper.toDto(odgovorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OdgovorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OdgovorDTO> findByCriteria(OdgovorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Odgovor> specification = createSpecification(criteria);
        return odgovorRepository.findAll(specification, page)
            .map(odgovorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OdgovorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Odgovor> specification = createSpecification(criteria);
        return odgovorRepository.count(specification);
    }

    /**
     * Function to convert OdgovorCriteria to a {@link Specification}.
     */
    private Specification<Odgovor> createSpecification(OdgovorCriteria criteria) {
        Specification<Odgovor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Odgovor_.id));
            }
            if (criteria.getTekst() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTekst(), Odgovor_.tekst));
            }
            if (criteria.getTacan() != null) {
                specification = specification.and(buildSpecification(criteria.getTacan(), Odgovor_.tacan));
            }
            if (criteria.getPojasnjenjeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPojasnjenjeId(),
                    root -> root.join(Odgovor_.pojasnjenje, JoinType.LEFT).get(Pojasnjenje_.id)));
            }
            if (criteria.getPitanjeId() != null) {
                specification = specification.and(buildSpecification(criteria.getPitanjeId(),
                    root -> root.join(Odgovor_.pitanje, JoinType.LEFT).get(Pitanje_.id)));
            }
        }
        return specification;
    }
}
