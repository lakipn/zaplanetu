package com.girlscancode.web.rest;

import com.girlscancode.service.SekcijaService;
import com.girlscancode.web.rest.errors.BadRequestAlertException;
import com.girlscancode.service.dto.SekcijaDTO;
import com.girlscancode.service.dto.SekcijaCriteria;
import com.girlscancode.service.SekcijaQueryService;

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
 * REST controller for managing {@link com.girlscancode.domain.Sekcija}.
 */
@RestController
@RequestMapping("/api")
public class SekcijaResource {

    private final Logger log = LoggerFactory.getLogger(SekcijaResource.class);

    private static final String ENTITY_NAME = "sekcija";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SekcijaService sekcijaService;

    private final SekcijaQueryService sekcijaQueryService;

    public SekcijaResource(SekcijaService sekcijaService, SekcijaQueryService sekcijaQueryService) {
        this.sekcijaService = sekcijaService;
        this.sekcijaQueryService = sekcijaQueryService;
    }

    /**
     * {@code POST  /sekcijas} : Create a new sekcija.
     *
     * @param sekcijaDTO the sekcijaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sekcijaDTO, or with status {@code 400 (Bad Request)} if the sekcija has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sekcijas")
    public ResponseEntity<SekcijaDTO> createSekcija(@Valid @RequestBody SekcijaDTO sekcijaDTO) throws URISyntaxException {
        log.debug("REST request to save Sekcija : {}", sekcijaDTO);
        if (sekcijaDTO.getId() != null) {
            throw new BadRequestAlertException("A new sekcija cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SekcijaDTO result = sekcijaService.save(sekcijaDTO);
        return ResponseEntity.created(new URI("/api/sekcijas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sekcijas} : Updates an existing sekcija.
     *
     * @param sekcijaDTO the sekcijaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sekcijaDTO,
     * or with status {@code 400 (Bad Request)} if the sekcijaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sekcijaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sekcijas")
    public ResponseEntity<SekcijaDTO> updateSekcija(@Valid @RequestBody SekcijaDTO sekcijaDTO) throws URISyntaxException {
        log.debug("REST request to update Sekcija : {}", sekcijaDTO);
        if (sekcijaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SekcijaDTO result = sekcijaService.save(sekcijaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sekcijaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sekcijas} : get all the sekcijas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sekcijas in body.
     */
    @GetMapping("/sekcijas")
    public ResponseEntity<List<SekcijaDTO>> getAllSekcijas(SekcijaCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Sekcijas by criteria: {}", criteria);
        Page<SekcijaDTO> page = sekcijaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /sekcijas/count} : count all the sekcijas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/sekcijas/count")
    public ResponseEntity<Long> countSekcijas(SekcijaCriteria criteria) {
        log.debug("REST request to count Sekcijas by criteria: {}", criteria);
        return ResponseEntity.ok().body(sekcijaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sekcijas/:id} : get the "id" sekcija.
     *
     * @param id the id of the sekcijaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sekcijaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sekcijas/{id}")
    public ResponseEntity<SekcijaDTO> getSekcija(@PathVariable Long id) {
        log.debug("REST request to get Sekcija : {}", id);
        Optional<SekcijaDTO> sekcijaDTO = sekcijaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sekcijaDTO);
    }

    /**
     * {@code DELETE  /sekcijas/:id} : delete the "id" sekcija.
     *
     * @param id the id of the sekcijaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sekcijas/{id}")
    public ResponseEntity<Void> deleteSekcija(@PathVariable Long id) {
        log.debug("REST request to delete Sekcija : {}", id);
        sekcijaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
