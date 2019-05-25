package com.girlscancode.web.rest;

import com.girlscancode.service.PoenService;
import com.girlscancode.web.rest.errors.BadRequestAlertException;
import com.girlscancode.service.dto.PoenDTO;
import com.girlscancode.service.dto.PoenCriteria;
import com.girlscancode.service.PoenQueryService;

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

/**
 * REST controller for managing {@link com.girlscancode.domain.Poen}.
 */
@RestController
@RequestMapping("/api")
public class PoenResource {

    private final Logger log = LoggerFactory.getLogger(PoenResource.class);

    private static final String ENTITY_NAME = "poen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PoenService poenService;

    private final PoenQueryService poenQueryService;

    public PoenResource(PoenService poenService, PoenQueryService poenQueryService) {
        this.poenService = poenService;
        this.poenQueryService = poenQueryService;
    }

    /**
     * {@code POST  /poens} : Create a new poen.
     *
     * @param poenDTO the poenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new poenDTO, or with status {@code 400 (Bad Request)} if the poen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/poens")
    public ResponseEntity<PoenDTO> createPoen(@RequestBody PoenDTO poenDTO) throws URISyntaxException {
        log.debug("REST request to save Poen : {}", poenDTO);
        if (poenDTO.getId() != null) {
            throw new BadRequestAlertException("A new poen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoenDTO result = poenService.save(poenDTO);
        return ResponseEntity.created(new URI("/api/poens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /poens} : Updates an existing poen.
     *
     * @param poenDTO the poenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poenDTO,
     * or with status {@code 400 (Bad Request)} if the poenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the poenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/poens")
    public ResponseEntity<PoenDTO> updatePoen(@RequestBody PoenDTO poenDTO) throws URISyntaxException {
        log.debug("REST request to update Poen : {}", poenDTO);
        if (poenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PoenDTO result = poenService.save(poenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, poenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /poens} : get all the poens.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of poens in body.
     */
    @GetMapping("/poens")
    public ResponseEntity<List<PoenDTO>> getAllPoens(PoenCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Poens by criteria: {}", criteria);
        Page<PoenDTO> page = poenQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /poens/count} : count all the poens.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/poens/count")
    public ResponseEntity<Long> countPoens(PoenCriteria criteria) {
        log.debug("REST request to count Poens by criteria: {}", criteria);
        return ResponseEntity.ok().body(poenQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /poens/:id} : get the "id" poen.
     *
     * @param id the id of the poenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the poenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/poens/{id}")
    public ResponseEntity<PoenDTO> getPoen(@PathVariable Long id) {
        log.debug("REST request to get Poen : {}", id);
        Optional<PoenDTO> poenDTO = poenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(poenDTO);
    }

    /**
     * {@code DELETE  /poens/:id} : delete the "id" poen.
     *
     * @param id the id of the poenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/poens/{id}")
    public ResponseEntity<Void> deletePoen(@PathVariable Long id) {
        log.debug("REST request to delete Poen : {}", id);
        poenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
