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

import com.girlscancode.domain.Poen;
import com.girlscancode.domain.*; // for static metamodels
import com.girlscancode.repository.PoenRepository;
import com.girlscancode.service.dto.PoenCriteria;
import com.girlscancode.service.dto.PoenDTO;
import com.girlscancode.service.mapper.PoenMapper;

/**
 * Service for executing complex queries for {@link Poen} entities in the database.
 * The main input is a {@link PoenCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PoenDTO} or a {@link Page} of {@link PoenDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PoenQueryService extends QueryService<Poen> {

    private final Logger log = LoggerFactory.getLogger(PoenQueryService.class);

    private final PoenRepository poenRepository;

    private final PoenMapper poenMapper;

    public PoenQueryService(PoenRepository poenRepository, PoenMapper poenMapper) {
        this.poenRepository = poenRepository;
        this.poenMapper = poenMapper;
    }

    /**
     * Return a {@link List} of {@link PoenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PoenDTO> findByCriteria(PoenCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Poen> specification = createSpecification(criteria);
        return poenMapper.toDto(poenRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PoenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PoenDTO> findByCriteria(PoenCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Poen> specification = createSpecification(criteria);
        return poenRepository.findAll(specification, page)
            .map(poenMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PoenCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Poen> specification = createSpecification(criteria);
        return poenRepository.count(specification);
    }

    /**
     * Function to convert PoenCriteria to a {@link Specification}.
     */
    private Specification<Poen> createSpecification(PoenCriteria criteria) {
        Specification<Poen> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Poen_.id));
            }
            if (criteria.getTip() != null) {
                specification = specification.and(buildSpecification(criteria.getTip(), Poen_.tip));
            }
            if (criteria.getKorisnikId() != null) {
                specification = specification.and(buildSpecification(criteria.getKorisnikId(),
                    root -> root.join(Poen_.korisnik, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getDrzavaId() != null) {
                specification = specification.and(buildSpecification(criteria.getDrzavaId(),
                    root -> root.join(Poen_.drzava, JoinType.LEFT).get(Drzava_.id)));
            }
        }
        return specification;
    }
}
