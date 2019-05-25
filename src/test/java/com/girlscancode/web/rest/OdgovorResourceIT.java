package com.girlscancode.web.rest;

import com.girlscancode.ZaPlanetuApp;
import com.girlscancode.domain.Odgovor;
import com.girlscancode.domain.Pojasnjenje;
import com.girlscancode.domain.Pitanje;
import com.girlscancode.repository.OdgovorRepository;
import com.girlscancode.service.OdgovorService;
import com.girlscancode.service.dto.OdgovorDTO;
import com.girlscancode.service.mapper.OdgovorMapper;
import com.girlscancode.web.rest.errors.ExceptionTranslator;
import com.girlscancode.service.dto.OdgovorCriteria;
import com.girlscancode.service.OdgovorQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.girlscancode.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link OdgovorResource} REST controller.
 */
@SpringBootTest(classes = ZaPlanetuApp.class)
public class OdgovorResourceIT {

    private static final String DEFAULT_TEKST = "AAAAAAAAAA";
    private static final String UPDATED_TEKST = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TACAN = false;
    private static final Boolean UPDATED_TACAN = true;

    @Autowired
    private OdgovorRepository odgovorRepository;

    @Autowired
    private OdgovorMapper odgovorMapper;

    @Autowired
    private OdgovorService odgovorService;

    @Autowired
    private OdgovorQueryService odgovorQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restOdgovorMockMvc;

    private Odgovor odgovor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OdgovorResource odgovorResource = new OdgovorResource(odgovorService, odgovorQueryService);
        this.restOdgovorMockMvc = MockMvcBuilders.standaloneSetup(odgovorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Odgovor createEntity(EntityManager em) {
        Odgovor odgovor = new Odgovor()
            .tekst(DEFAULT_TEKST)
            .tacan(DEFAULT_TACAN);
        return odgovor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Odgovor createUpdatedEntity(EntityManager em) {
        Odgovor odgovor = new Odgovor()
            .tekst(UPDATED_TEKST)
            .tacan(UPDATED_TACAN);
        return odgovor;
    }

    @BeforeEach
    public void initTest() {
        odgovor = createEntity(em);
    }

    @Test
    @Transactional
    public void createOdgovor() throws Exception {
        int databaseSizeBeforeCreate = odgovorRepository.findAll().size();

        // Create the Odgovor
        OdgovorDTO odgovorDTO = odgovorMapper.toDto(odgovor);
        restOdgovorMockMvc.perform(post("/api/odgovors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(odgovorDTO)))
            .andExpect(status().isCreated());

        // Validate the Odgovor in the database
        List<Odgovor> odgovorList = odgovorRepository.findAll();
        assertThat(odgovorList).hasSize(databaseSizeBeforeCreate + 1);
        Odgovor testOdgovor = odgovorList.get(odgovorList.size() - 1);
        assertThat(testOdgovor.getTekst()).isEqualTo(DEFAULT_TEKST);
        assertThat(testOdgovor.isTacan()).isEqualTo(DEFAULT_TACAN);
    }

    @Test
    @Transactional
    public void createOdgovorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = odgovorRepository.findAll().size();

        // Create the Odgovor with an existing ID
        odgovor.setId(1L);
        OdgovorDTO odgovorDTO = odgovorMapper.toDto(odgovor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOdgovorMockMvc.perform(post("/api/odgovors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(odgovorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Odgovor in the database
        List<Odgovor> odgovorList = odgovorRepository.findAll();
        assertThat(odgovorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOdgovors() throws Exception {
        // Initialize the database
        odgovorRepository.saveAndFlush(odgovor);

        // Get all the odgovorList
        restOdgovorMockMvc.perform(get("/api/odgovors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(odgovor.getId().intValue())))
            .andExpect(jsonPath("$.[*].tekst").value(hasItem(DEFAULT_TEKST.toString())))
            .andExpect(jsonPath("$.[*].tacan").value(hasItem(DEFAULT_TACAN.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getOdgovor() throws Exception {
        // Initialize the database
        odgovorRepository.saveAndFlush(odgovor);

        // Get the odgovor
        restOdgovorMockMvc.perform(get("/api/odgovors/{id}", odgovor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(odgovor.getId().intValue()))
            .andExpect(jsonPath("$.tekst").value(DEFAULT_TEKST.toString()))
            .andExpect(jsonPath("$.tacan").value(DEFAULT_TACAN.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllOdgovorsByTekstIsEqualToSomething() throws Exception {
        // Initialize the database
        odgovorRepository.saveAndFlush(odgovor);

        // Get all the odgovorList where tekst equals to DEFAULT_TEKST
        defaultOdgovorShouldBeFound("tekst.equals=" + DEFAULT_TEKST);

        // Get all the odgovorList where tekst equals to UPDATED_TEKST
        defaultOdgovorShouldNotBeFound("tekst.equals=" + UPDATED_TEKST);
    }

    @Test
    @Transactional
    public void getAllOdgovorsByTekstIsInShouldWork() throws Exception {
        // Initialize the database
        odgovorRepository.saveAndFlush(odgovor);

        // Get all the odgovorList where tekst in DEFAULT_TEKST or UPDATED_TEKST
        defaultOdgovorShouldBeFound("tekst.in=" + DEFAULT_TEKST + "," + UPDATED_TEKST);

        // Get all the odgovorList where tekst equals to UPDATED_TEKST
        defaultOdgovorShouldNotBeFound("tekst.in=" + UPDATED_TEKST);
    }

    @Test
    @Transactional
    public void getAllOdgovorsByTekstIsNullOrNotNull() throws Exception {
        // Initialize the database
        odgovorRepository.saveAndFlush(odgovor);

        // Get all the odgovorList where tekst is not null
        defaultOdgovorShouldBeFound("tekst.specified=true");

        // Get all the odgovorList where tekst is null
        defaultOdgovorShouldNotBeFound("tekst.specified=false");
    }

    @Test
    @Transactional
    public void getAllOdgovorsByTacanIsEqualToSomething() throws Exception {
        // Initialize the database
        odgovorRepository.saveAndFlush(odgovor);

        // Get all the odgovorList where tacan equals to DEFAULT_TACAN
        defaultOdgovorShouldBeFound("tacan.equals=" + DEFAULT_TACAN);

        // Get all the odgovorList where tacan equals to UPDATED_TACAN
        defaultOdgovorShouldNotBeFound("tacan.equals=" + UPDATED_TACAN);
    }

    @Test
    @Transactional
    public void getAllOdgovorsByTacanIsInShouldWork() throws Exception {
        // Initialize the database
        odgovorRepository.saveAndFlush(odgovor);

        // Get all the odgovorList where tacan in DEFAULT_TACAN or UPDATED_TACAN
        defaultOdgovorShouldBeFound("tacan.in=" + DEFAULT_TACAN + "," + UPDATED_TACAN);

        // Get all the odgovorList where tacan equals to UPDATED_TACAN
        defaultOdgovorShouldNotBeFound("tacan.in=" + UPDATED_TACAN);
    }

    @Test
    @Transactional
    public void getAllOdgovorsByTacanIsNullOrNotNull() throws Exception {
        // Initialize the database
        odgovorRepository.saveAndFlush(odgovor);

        // Get all the odgovorList where tacan is not null
        defaultOdgovorShouldBeFound("tacan.specified=true");

        // Get all the odgovorList where tacan is null
        defaultOdgovorShouldNotBeFound("tacan.specified=false");
    }

    @Test
    @Transactional
    public void getAllOdgovorsByPojasnjenjeIsEqualToSomething() throws Exception {
        // Initialize the database
        Pojasnjenje pojasnjenje = PojasnjenjeResourceIT.createEntity(em);
        em.persist(pojasnjenje);
        em.flush();
        odgovor.setPojasnjenje(pojasnjenje);
        odgovorRepository.saveAndFlush(odgovor);
        Long pojasnjenjeId = pojasnjenje.getId();

        // Get all the odgovorList where pojasnjenje equals to pojasnjenjeId
        defaultOdgovorShouldBeFound("pojasnjenjeId.equals=" + pojasnjenjeId);

        // Get all the odgovorList where pojasnjenje equals to pojasnjenjeId + 1
        defaultOdgovorShouldNotBeFound("pojasnjenjeId.equals=" + (pojasnjenjeId + 1));
    }


    @Test
    @Transactional
    public void getAllOdgovorsByPitanjeIsEqualToSomething() throws Exception {
        // Initialize the database
        Pitanje pitanje = PitanjeResourceIT.createEntity(em);
        em.persist(pitanje);
        em.flush();
        odgovor.setPitanje(pitanje);
        odgovorRepository.saveAndFlush(odgovor);
        Long pitanjeId = pitanje.getId();

        // Get all the odgovorList where pitanje equals to pitanjeId
        defaultOdgovorShouldBeFound("pitanjeId.equals=" + pitanjeId);

        // Get all the odgovorList where pitanje equals to pitanjeId + 1
        defaultOdgovorShouldNotBeFound("pitanjeId.equals=" + (pitanjeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOdgovorShouldBeFound(String filter) throws Exception {
        restOdgovorMockMvc.perform(get("/api/odgovors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(odgovor.getId().intValue())))
            .andExpect(jsonPath("$.[*].tekst").value(hasItem(DEFAULT_TEKST)))
            .andExpect(jsonPath("$.[*].tacan").value(hasItem(DEFAULT_TACAN.booleanValue())));

        // Check, that the count call also returns 1
        restOdgovorMockMvc.perform(get("/api/odgovors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOdgovorShouldNotBeFound(String filter) throws Exception {
        restOdgovorMockMvc.perform(get("/api/odgovors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOdgovorMockMvc.perform(get("/api/odgovors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOdgovor() throws Exception {
        // Get the odgovor
        restOdgovorMockMvc.perform(get("/api/odgovors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOdgovor() throws Exception {
        // Initialize the database
        odgovorRepository.saveAndFlush(odgovor);

        int databaseSizeBeforeUpdate = odgovorRepository.findAll().size();

        // Update the odgovor
        Odgovor updatedOdgovor = odgovorRepository.findById(odgovor.getId()).get();
        // Disconnect from session so that the updates on updatedOdgovor are not directly saved in db
        em.detach(updatedOdgovor);
        updatedOdgovor
            .tekst(UPDATED_TEKST)
            .tacan(UPDATED_TACAN);
        OdgovorDTO odgovorDTO = odgovorMapper.toDto(updatedOdgovor);

        restOdgovorMockMvc.perform(put("/api/odgovors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(odgovorDTO)))
            .andExpect(status().isOk());

        // Validate the Odgovor in the database
        List<Odgovor> odgovorList = odgovorRepository.findAll();
        assertThat(odgovorList).hasSize(databaseSizeBeforeUpdate);
        Odgovor testOdgovor = odgovorList.get(odgovorList.size() - 1);
        assertThat(testOdgovor.getTekst()).isEqualTo(UPDATED_TEKST);
        assertThat(testOdgovor.isTacan()).isEqualTo(UPDATED_TACAN);
    }

    @Test
    @Transactional
    public void updateNonExistingOdgovor() throws Exception {
        int databaseSizeBeforeUpdate = odgovorRepository.findAll().size();

        // Create the Odgovor
        OdgovorDTO odgovorDTO = odgovorMapper.toDto(odgovor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOdgovorMockMvc.perform(put("/api/odgovors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(odgovorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Odgovor in the database
        List<Odgovor> odgovorList = odgovorRepository.findAll();
        assertThat(odgovorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOdgovor() throws Exception {
        // Initialize the database
        odgovorRepository.saveAndFlush(odgovor);

        int databaseSizeBeforeDelete = odgovorRepository.findAll().size();

        // Delete the odgovor
        restOdgovorMockMvc.perform(delete("/api/odgovors/{id}", odgovor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Odgovor> odgovorList = odgovorRepository.findAll();
        assertThat(odgovorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Odgovor.class);
        Odgovor odgovor1 = new Odgovor();
        odgovor1.setId(1L);
        Odgovor odgovor2 = new Odgovor();
        odgovor2.setId(odgovor1.getId());
        assertThat(odgovor1).isEqualTo(odgovor2);
        odgovor2.setId(2L);
        assertThat(odgovor1).isNotEqualTo(odgovor2);
        odgovor1.setId(null);
        assertThat(odgovor1).isNotEqualTo(odgovor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OdgovorDTO.class);
        OdgovorDTO odgovorDTO1 = new OdgovorDTO();
        odgovorDTO1.setId(1L);
        OdgovorDTO odgovorDTO2 = new OdgovorDTO();
        assertThat(odgovorDTO1).isNotEqualTo(odgovorDTO2);
        odgovorDTO2.setId(odgovorDTO1.getId());
        assertThat(odgovorDTO1).isEqualTo(odgovorDTO2);
        odgovorDTO2.setId(2L);
        assertThat(odgovorDTO1).isNotEqualTo(odgovorDTO2);
        odgovorDTO1.setId(null);
        assertThat(odgovorDTO1).isNotEqualTo(odgovorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(odgovorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(odgovorMapper.fromId(null)).isNull();
    }
}
