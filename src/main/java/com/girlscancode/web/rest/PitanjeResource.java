package com.girlscancode.web.rest;

import com.girlscancode.service.PitanjeService;
import com.girlscancode.web.rest.errors.BadRequestAlertException;
import com.girlscancode.service.dto.PitanjeDTO;
import com.girlscancode.service.dto.PitanjeCriteria;
import com.girlscancode.service.PitanjeQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.girlscancode.domain.Pitanje}.
 */
@RestController
@RequestMapping("/api")
public class PitanjeResource {

    private final Logger log = LoggerFactory.getLogger(PitanjeResource.class);

    private static final String ENTITY_NAME = "pitanje";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PitanjeService pitanjeService;

    private final PitanjeQueryService pitanjeQueryService;

    public PitanjeResource(PitanjeService pitanjeService, PitanjeQueryService pitanjeQueryService) {
        this.pitanjeService = pitanjeService;
        this.pitanjeQueryService = pitanjeQueryService;
    }

    /**
     * {@code POST  /pitanjes} : Create a new pitanje.
     *
     * @param pitanjeDTO the pitanjeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pitanjeDTO, or with status {@code 400 (Bad Request)} if the pitanje has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pitanjes")
    public ResponseEntity<PitanjeDTO> createPitanje(@Valid @RequestBody PitanjeDTO pitanjeDTO) throws URISyntaxException {
        log.debug("REST request to save Pitanje : {}", pitanjeDTO);
        if (pitanjeDTO.getId() != null) {
            throw new BadRequestAlertException("A new pitanje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PitanjeDTO result = pitanjeService.save(pitanjeDTO);
        return ResponseEntity.created(new URI("/api/pitanjes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pitanjes} : Updates an existing pitanje.
     *
     * @param pitanjeDTO the pitanjeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pitanjeDTO,
     * or with status {@code 400 (Bad Request)} if the pitanjeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pitanjeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pitanjes")
    public ResponseEntity<PitanjeDTO> updatePitanje(@Valid @RequestBody PitanjeDTO pitanjeDTO) throws URISyntaxException {
        log.debug("REST request to update Pitanje : {}", pitanjeDTO);
        if (pitanjeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PitanjeDTO result = pitanjeService.save(pitanjeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pitanjeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pitanjes} : get all the pitanjes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pitanjes in body.
     */
    @GetMapping("/pitanjes")
    public ResponseEntity<List<PitanjeDTO>> getAllPitanjes(PitanjeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Pitanjes by criteria: {}", criteria);
        Page<PitanjeDTO> page = pitanjeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /pitanjes/count} : count all the pitanjes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/pitanjes/count")
    public ResponseEntity<Long> countPitanjes(PitanjeCriteria criteria) {
        log.debug("REST request to count Pitanjes by criteria: {}", criteria);
        return ResponseEntity.ok().body(pitanjeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pitanjes/:id} : get the "id" pitanje.
     *
     * @param id the id of the pitanjeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pitanjeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pitanjes/{id}")
    public ResponseEntity<PitanjeDTO> getPitanje(@PathVariable Long id) {
        log.debug("REST request to get Pitanje : {}", id);
        Optional<PitanjeDTO> pitanjeDTO = pitanjeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pitanjeDTO);
    }

    /**
     * {@code DELETE  /pitanjes/:id} : delete the "id" pitanje.
     *
     * @param id the id of the pitanjeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pitanjes/{id}")
    public ResponseEntity<Void> deletePitanje(@PathVariable Long id) {
        log.debug("REST request to delete Pitanje : {}", id);
        pitanjeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
