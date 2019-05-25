package com.girlscancode.web.rest;

import com.girlscancode.service.DrzavaService;
import com.girlscancode.web.rest.errors.BadRequestAlertException;
import com.girlscancode.service.dto.DrzavaDTO;
import com.girlscancode.service.dto.DrzavaCriteria;
import com.girlscancode.service.DrzavaQueryService;

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
 * REST controller for managing {@link com.girlscancode.domain.Drzava}.
 */
@RestController
@RequestMapping("/api")
public class DrzavaResource {

    private final Logger log = LoggerFactory.getLogger(DrzavaResource.class);

    private static final String ENTITY_NAME = "drzava";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DrzavaService drzavaService;

    private final DrzavaQueryService drzavaQueryService;

    public DrzavaResource(DrzavaService drzavaService, DrzavaQueryService drzavaQueryService) {
        this.drzavaService = drzavaService;
        this.drzavaQueryService = drzavaQueryService;
    }

    /**
     * {@code POST  /drzavas} : Create a new drzava.
     *
     * @param drzavaDTO the drzavaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new drzavaDTO, or with status {@code 400 (Bad Request)} if the drzava has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drzavas")
    public ResponseEntity<DrzavaDTO> createDrzava(@Valid @RequestBody DrzavaDTO drzavaDTO) throws URISyntaxException {
        log.debug("REST request to save Drzava : {}", drzavaDTO);
        if (drzavaDTO.getId() != null) {
            throw new BadRequestAlertException("A new drzava cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrzavaDTO result = drzavaService.save(drzavaDTO);
        return ResponseEntity.created(new URI("/api/drzavas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drzavas} : Updates an existing drzava.
     *
     * @param drzavaDTO the drzavaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drzavaDTO,
     * or with status {@code 400 (Bad Request)} if the drzavaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the drzavaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drzavas")
    public ResponseEntity<DrzavaDTO> updateDrzava(@Valid @RequestBody DrzavaDTO drzavaDTO) throws URISyntaxException {
        log.debug("REST request to update Drzava : {}", drzavaDTO);
        if (drzavaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DrzavaDTO result = drzavaService.save(drzavaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, drzavaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /drzavas} : get all the drzavas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drzavas in body.
     */
    @GetMapping("/drzavas")
    public ResponseEntity<List<DrzavaDTO>> getAllDrzavas(DrzavaCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Drzavas by criteria: {}", criteria);
        Page<DrzavaDTO> page = drzavaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /drzavas/count} : count all the drzavas.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/drzavas/count")
    public ResponseEntity<Long> countDrzavas(DrzavaCriteria criteria) {
        log.debug("REST request to count Drzavas by criteria: {}", criteria);
        return ResponseEntity.ok().body(drzavaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /drzavas/:id} : get the "id" drzava.
     *
     * @param id the id of the drzavaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drzavaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drzavas/{id}")
    public ResponseEntity<DrzavaDTO> getDrzava(@PathVariable Long id) {
        log.debug("REST request to get Drzava : {}", id);
        Optional<DrzavaDTO> drzavaDTO = drzavaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(drzavaDTO);
    }

    /**
     * {@code DELETE  /drzavas/:id} : delete the "id" drzava.
     *
     * @param id the id of the drzavaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drzavas/{id}")
    public ResponseEntity<Void> deleteDrzava(@PathVariable Long id) {
        log.debug("REST request to delete Drzava : {}", id);
        drzavaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
