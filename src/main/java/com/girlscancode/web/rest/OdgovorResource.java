package com.girlscancode.web.rest;

import com.girlscancode.service.OdgovorService;
import com.girlscancode.web.rest.errors.BadRequestAlertException;
import com.girlscancode.service.dto.OdgovorDTO;
import com.girlscancode.service.dto.OdgovorCriteria;
import com.girlscancode.service.OdgovorQueryService;

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
 * REST controller for managing {@link com.girlscancode.domain.Odgovor}.
 */
@RestController
@RequestMapping("/api")
public class OdgovorResource {

    private final Logger log = LoggerFactory.getLogger(OdgovorResource.class);

    private static final String ENTITY_NAME = "odgovor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OdgovorService odgovorService;

    private final OdgovorQueryService odgovorQueryService;

    public OdgovorResource(OdgovorService odgovorService, OdgovorQueryService odgovorQueryService) {
        this.odgovorService = odgovorService;
        this.odgovorQueryService = odgovorQueryService;
    }

    /**
     * {@code POST  /odgovors} : Create a new odgovor.
     *
     * @param odgovorDTO the odgovorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new odgovorDTO, or with status {@code 400 (Bad Request)} if the odgovor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/odgovors")
    public ResponseEntity<OdgovorDTO> createOdgovor(@Valid @RequestBody OdgovorDTO odgovorDTO) throws URISyntaxException {
        log.debug("REST request to save Odgovor : {}", odgovorDTO);
        if (odgovorDTO.getId() != null) {
            throw new BadRequestAlertException("A new odgovor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OdgovorDTO result = odgovorService.save(odgovorDTO);
        return ResponseEntity.created(new URI("/api/odgovors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /odgovors} : Updates an existing odgovor.
     *
     * @param odgovorDTO the odgovorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated odgovorDTO,
     * or with status {@code 400 (Bad Request)} if the odgovorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the odgovorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/odgovors")
    public ResponseEntity<OdgovorDTO> updateOdgovor(@Valid @RequestBody OdgovorDTO odgovorDTO) throws URISyntaxException {
        log.debug("REST request to update Odgovor : {}", odgovorDTO);
        if (odgovorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OdgovorDTO result = odgovorService.save(odgovorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, odgovorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /odgovors} : get all the odgovors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of odgovors in body.
     */
    @GetMapping("/odgovors")
    public ResponseEntity<List<OdgovorDTO>> getAllOdgovors(OdgovorCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Odgovors by criteria: {}", criteria);
        Page<OdgovorDTO> page = odgovorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /odgovors/count} : count all the odgovors.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/odgovors/count")
    public ResponseEntity<Long> countOdgovors(OdgovorCriteria criteria) {
        log.debug("REST request to count Odgovors by criteria: {}", criteria);
        return ResponseEntity.ok().body(odgovorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /odgovors/:id} : get the "id" odgovor.
     *
     * @param id the id of the odgovorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the odgovorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/odgovors/{id}")
    public ResponseEntity<OdgovorDTO> getOdgovor(@PathVariable Long id) {
        log.debug("REST request to get Odgovor : {}", id);
        Optional<OdgovorDTO> odgovorDTO = odgovorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(odgovorDTO);
    }

    /**
     * {@code DELETE  /odgovors/:id} : delete the "id" odgovor.
     *
     * @param id the id of the odgovorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/odgovors/{id}")
    public ResponseEntity<Void> deleteOdgovor(@PathVariable Long id) {
        log.debug("REST request to delete Odgovor : {}", id);
        odgovorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
