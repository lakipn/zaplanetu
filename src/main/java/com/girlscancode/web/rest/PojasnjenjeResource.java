package com.girlscancode.web.rest;

import com.girlscancode.service.PojasnjenjeService;
import com.girlscancode.web.rest.errors.BadRequestAlertException;
import com.girlscancode.service.dto.PojasnjenjeDTO;
import com.girlscancode.service.dto.PojasnjenjeCriteria;
import com.girlscancode.service.PojasnjenjeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.girlscancode.domain.Pojasnjenje}.
 */
@RestController
@RequestMapping("/api")
public class PojasnjenjeResource {

    private final Logger log = LoggerFactory.getLogger(PojasnjenjeResource.class);

    private static final String ENTITY_NAME = "pojasnjenje";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PojasnjenjeService pojasnjenjeService;

    private final PojasnjenjeQueryService pojasnjenjeQueryService;

    public PojasnjenjeResource(PojasnjenjeService pojasnjenjeService, PojasnjenjeQueryService pojasnjenjeQueryService) {
        this.pojasnjenjeService = pojasnjenjeService;
        this.pojasnjenjeQueryService = pojasnjenjeQueryService;
    }

    /**
     * {@code POST  /pojasnjenjes} : Create a new pojasnjenje.
     *
     * @param pojasnjenjeDTO the pojasnjenjeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pojasnjenjeDTO, or with status {@code 400 (Bad Request)} if the pojasnjenje has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pojasnjenjes")
    public ResponseEntity<PojasnjenjeDTO> createPojasnjenje(@RequestBody PojasnjenjeDTO pojasnjenjeDTO) throws URISyntaxException {
        log.debug("REST request to save Pojasnjenje : {}", pojasnjenjeDTO);
        if (pojasnjenjeDTO.getId() != null) {
            throw new BadRequestAlertException("A new pojasnjenje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PojasnjenjeDTO result = pojasnjenjeService.save(pojasnjenjeDTO);
        return ResponseEntity.created(new URI("/api/pojasnjenjes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pojasnjenjes} : Updates an existing pojasnjenje.
     *
     * @param pojasnjenjeDTO the pojasnjenjeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pojasnjenjeDTO,
     * or with status {@code 400 (Bad Request)} if the pojasnjenjeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pojasnjenjeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pojasnjenjes")
    public ResponseEntity<PojasnjenjeDTO> updatePojasnjenje(@RequestBody PojasnjenjeDTO pojasnjenjeDTO) throws URISyntaxException {
        log.debug("REST request to update Pojasnjenje : {}", pojasnjenjeDTO);
        if (pojasnjenjeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PojasnjenjeDTO result = pojasnjenjeService.save(pojasnjenjeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pojasnjenjeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pojasnjenjes} : get all the pojasnjenjes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pojasnjenjes in body.
     */
    @GetMapping("/pojasnjenjes")
    public ResponseEntity<List<PojasnjenjeDTO>> getAllPojasnjenjes(PojasnjenjeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Pojasnjenjes by criteria: {}", criteria);
        Page<PojasnjenjeDTO> page = pojasnjenjeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /pojasnjenjes/count} : count all the pojasnjenjes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/pojasnjenjes/count")
    public ResponseEntity<Long> countPojasnjenjes(PojasnjenjeCriteria criteria) {
        log.debug("REST request to count Pojasnjenjes by criteria: {}", criteria);
        return ResponseEntity.ok().body(pojasnjenjeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pojasnjenjes/:id} : get the "id" pojasnjenje.
     *
     * @param id the id of the pojasnjenjeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pojasnjenjeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pojasnjenjes/{id}")
    public ResponseEntity<PojasnjenjeDTO> getPojasnjenje(@PathVariable Long id) {
        log.debug("REST request to get Pojasnjenje : {}", id);
        Optional<PojasnjenjeDTO> pojasnjenjeDTO = pojasnjenjeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pojasnjenjeDTO);
    }

    /**
     * {@code DELETE  /pojasnjenjes/:id} : delete the "id" pojasnjenje.
     *
     * @param id the id of the pojasnjenjeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pojasnjenjes/{id}")
    public ResponseEntity<Void> deletePojasnjenje(@PathVariable Long id) {
        log.debug("REST request to delete Pojasnjenje : {}", id);
        pojasnjenjeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
