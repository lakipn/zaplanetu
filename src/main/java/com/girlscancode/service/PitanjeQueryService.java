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

import com.girlscancode.domain.Pitanje;
import com.girlscancode.domain.*; // for static metamodels
import com.girlscancode.repository.PitanjeRepository;
import com.girlscancode.service.dto.PitanjeCriteria;
import com.girlscancode.service.dto.PitanjeDTO;
import com.girlscancode.service.mapper.PitanjeMapper;

/**
 * Service for executing complex queries for {@link Pitanje} entities in the database.
 * The main input is a {@link PitanjeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PitanjeDTO} or a {@link Page} of {@link PitanjeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PitanjeQueryService extends QueryService<Pitanje> {

    private final Logger log = LoggerFactory.getLogger(PitanjeQueryService.class);

    private final PitanjeRepository pitanjeRepository;

    private final PitanjeMapper pitanjeMapper;

    public PitanjeQueryService(PitanjeRepository pitanjeRepository, PitanjeMapper pitanjeMapper) {
        this.pitanjeRepository = pitanjeRepository;
        this.pitanjeMapper = pitanjeMapper;
    }

    /**
     * Return a {@link List} of {@link PitanjeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PitanjeDTO> findByCriteria(PitanjeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Pitanje> specification = createSpecification(criteria);
        return pitanjeMapper.toDto(pitanjeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PitanjeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PitanjeDTO> findByCriteria(PitanjeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pitanje> specification = createSpecification(criteria);
        return pitanjeRepository.findAll(specification, page)
            .map(pitanjeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PitanjeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Pitanje> specification = createSpecification(criteria);
        return pitanjeRepository.count(specification);
    }

    /**
     * Function to convert PitanjeCriteria to a {@link Specification}.
     */
    private Specification<Pitanje> createSpecification(PitanjeCriteria criteria) {
        Specification<Pitanje> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Pitanje_.id));
            }
            if (criteria.getTekst() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTekst(), Pitanje_.tekst));
            }
            if (criteria.getRedniBroj() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRedniBroj(), Pitanje_.redniBroj));
            }
            if (criteria.getSekcijaId() != null) {
                specification = specification.and(buildSpecification(criteria.getSekcijaId(),
                    root -> root.join(Pitanje_.sekcija, JoinType.LEFT).get(Sekcija_.id)));
            }
            if (criteria.getOdgovoriId() != null) {
                specification = specification.and(buildSpecification(criteria.getOdgovoriId(),
                    root -> root.join(Pitanje_.odgovoris, JoinType.LEFT).get(Odgovor_.id)));
            }
        }
        return specification;
    }
}
