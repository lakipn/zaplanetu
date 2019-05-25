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

import com.girlscancode.domain.Pojasnjenje;
import com.girlscancode.domain.*; // for static metamodels
import com.girlscancode.repository.PojasnjenjeRepository;
import com.girlscancode.service.dto.PojasnjenjeCriteria;
import com.girlscancode.service.dto.PojasnjenjeDTO;
import com.girlscancode.service.mapper.PojasnjenjeMapper;

/**
 * Service for executing complex queries for {@link Pojasnjenje} entities in the database.
 * The main input is a {@link PojasnjenjeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PojasnjenjeDTO} or a {@link Page} of {@link PojasnjenjeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PojasnjenjeQueryService extends QueryService<Pojasnjenje> {

    private final Logger log = LoggerFactory.getLogger(PojasnjenjeQueryService.class);

    private final PojasnjenjeRepository pojasnjenjeRepository;

    private final PojasnjenjeMapper pojasnjenjeMapper;

    public PojasnjenjeQueryService(PojasnjenjeRepository pojasnjenjeRepository, PojasnjenjeMapper pojasnjenjeMapper) {
        this.pojasnjenjeRepository = pojasnjenjeRepository;
        this.pojasnjenjeMapper = pojasnjenjeMapper;
    }

    /**
     * Return a {@link List} of {@link PojasnjenjeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PojasnjenjeDTO> findByCriteria(PojasnjenjeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Pojasnjenje> specification = createSpecification(criteria);
        return pojasnjenjeMapper.toDto(pojasnjenjeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PojasnjenjeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PojasnjenjeDTO> findByCriteria(PojasnjenjeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pojasnjenje> specification = createSpecification(criteria);
        return pojasnjenjeRepository.findAll(specification, page)
            .map(pojasnjenjeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PojasnjenjeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Pojasnjenje> specification = createSpecification(criteria);
        return pojasnjenjeRepository.count(specification);
    }

    /**
     * Function to convert PojasnjenjeCriteria to a {@link Specification}.
     */
    private Specification<Pojasnjenje> createSpecification(PojasnjenjeCriteria criteria) {
        Specification<Pojasnjenje> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Pojasnjenje_.id));
            }
            if (criteria.getOdgovorId() != null) {
                specification = specification.and(buildSpecification(criteria.getOdgovorId(),
                    root -> root.join(Pojasnjenje_.odgovor, JoinType.LEFT).get(Odgovor_.id)));
            }
        }
        return specification;
    }
}
