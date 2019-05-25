package com.girlscancode.web.rest;

import com.girlscancode.ZaPlanetuApp;
import com.girlscancode.domain.Pojasnjenje;
import com.girlscancode.domain.Odgovor;
import com.girlscancode.repository.PojasnjenjeRepository;
import com.girlscancode.service.PojasnjenjeService;
import com.girlscancode.service.dto.PojasnjenjeDTO;
import com.girlscancode.service.mapper.PojasnjenjeMapper;
import com.girlscancode.web.rest.errors.ExceptionTranslator;
import com.girlscancode.service.dto.PojasnjenjeCriteria;
import com.girlscancode.service.PojasnjenjeQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.girlscancode.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PojasnjenjeResource} REST controller.
 */
@SpringBootTest(classes = ZaPlanetuApp.class)
public class PojasnjenjeResourceIT {

    private static final String DEFAULT_TEKST = "AAAAAAAAAA";
    private static final String UPDATED_TEKST = "BBBBBBBBBB";

    @Autowired
    private PojasnjenjeRepository pojasnjenjeRepository;

    @Autowired
    private PojasnjenjeMapper pojasnjenjeMapper;

    @Autowired
    private PojasnjenjeService pojasnjenjeService;

    @Autowired
    private PojasnjenjeQueryService pojasnjenjeQueryService;

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

    private MockMvc restPojasnjenjeMockMvc;

    private Pojasnjenje pojasnjenje;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PojasnjenjeResource pojasnjenjeResource = new PojasnjenjeResource(pojasnjenjeService, pojasnjenjeQueryService);
        this.restPojasnjenjeMockMvc = MockMvcBuilders.standaloneSetup(pojasnjenjeResource)
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
    public static Pojasnjenje createEntity(EntityManager em) {
        Pojasnjenje pojasnjenje = new Pojasnjenje()
            .tekst(DEFAULT_TEKST);
        return pojasnjenje;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pojasnjenje createUpdatedEntity(EntityManager em) {
        Pojasnjenje pojasnjenje = new Pojasnjenje()
            .tekst(UPDATED_TEKST);
        return pojasnjenje;
    }

    @BeforeEach
    public void initTest() {
        pojasnjenje = createEntity(em);
    }

    @Test
    @Transactional
    public void createPojasnjenje() throws Exception {
        int databaseSizeBeforeCreate = pojasnjenjeRepository.findAll().size();

        // Create the Pojasnjenje
        PojasnjenjeDTO pojasnjenjeDTO = pojasnjenjeMapper.toDto(pojasnjenje);
        restPojasnjenjeMockMvc.perform(post("/api/pojasnjenjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pojasnjenjeDTO)))
            .andExpect(status().isCreated());

        // Validate the Pojasnjenje in the database
        List<Pojasnjenje> pojasnjenjeList = pojasnjenjeRepository.findAll();
        assertThat(pojasnjenjeList).hasSize(databaseSizeBeforeCreate + 1);
        Pojasnjenje testPojasnjenje = pojasnjenjeList.get(pojasnjenjeList.size() - 1);
        assertThat(testPojasnjenje.getTekst()).isEqualTo(DEFAULT_TEKST);
    }

    @Test
    @Transactional
    public void createPojasnjenjeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pojasnjenjeRepository.findAll().size();

        // Create the Pojasnjenje with an existing ID
        pojasnjenje.setId(1L);
        PojasnjenjeDTO pojasnjenjeDTO = pojasnjenjeMapper.toDto(pojasnjenje);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPojasnjenjeMockMvc.perform(post("/api/pojasnjenjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pojasnjenjeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pojasnjenje in the database
        List<Pojasnjenje> pojasnjenjeList = pojasnjenjeRepository.findAll();
        assertThat(pojasnjenjeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPojasnjenjes() throws Exception {
        // Initialize the database
        pojasnjenjeRepository.saveAndFlush(pojasnjenje);

        // Get all the pojasnjenjeList
        restPojasnjenjeMockMvc.perform(get("/api/pojasnjenjes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pojasnjenje.getId().intValue())))
            .andExpect(jsonPath("$.[*].tekst").value(hasItem(DEFAULT_TEKST.toString())));
    }
    
    @Test
    @Transactional
    public void getPojasnjenje() throws Exception {
        // Initialize the database
        pojasnjenjeRepository.saveAndFlush(pojasnjenje);

        // Get the pojasnjenje
        restPojasnjenjeMockMvc.perform(get("/api/pojasnjenjes/{id}", pojasnjenje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pojasnjenje.getId().intValue()))
            .andExpect(jsonPath("$.tekst").value(DEFAULT_TEKST.toString()));
    }

    @Test
    @Transactional
    public void getAllPojasnjenjesByOdgovorIsEqualToSomething() throws Exception {
        // Initialize the database
        Odgovor odgovor = OdgovorResourceIT.createEntity(em);
        em.persist(odgovor);
        em.flush();
        pojasnjenje.setOdgovor(odgovor);
        odgovor.setPojasnjenje(pojasnjenje);
        pojasnjenjeRepository.saveAndFlush(pojasnjenje);
        Long odgovorId = odgovor.getId();

        // Get all the pojasnjenjeList where odgovor equals to odgovorId
        defaultPojasnjenjeShouldBeFound("odgovorId.equals=" + odgovorId);

        // Get all the pojasnjenjeList where odgovor equals to odgovorId + 1
        defaultPojasnjenjeShouldNotBeFound("odgovorId.equals=" + (odgovorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPojasnjenjeShouldBeFound(String filter) throws Exception {
        restPojasnjenjeMockMvc.perform(get("/api/pojasnjenjes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pojasnjenje.getId().intValue())))
            .andExpect(jsonPath("$.[*].tekst").value(hasItem(DEFAULT_TEKST.toString())));

        // Check, that the count call also returns 1
        restPojasnjenjeMockMvc.perform(get("/api/pojasnjenjes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPojasnjenjeShouldNotBeFound(String filter) throws Exception {
        restPojasnjenjeMockMvc.perform(get("/api/pojasnjenjes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPojasnjenjeMockMvc.perform(get("/api/pojasnjenjes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPojasnjenje() throws Exception {
        // Get the pojasnjenje
        restPojasnjenjeMockMvc.perform(get("/api/pojasnjenjes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePojasnjenje() throws Exception {
        // Initialize the database
        pojasnjenjeRepository.saveAndFlush(pojasnjenje);

        int databaseSizeBeforeUpdate = pojasnjenjeRepository.findAll().size();

        // Update the pojasnjenje
        Pojasnjenje updatedPojasnjenje = pojasnjenjeRepository.findById(pojasnjenje.getId()).get();
        // Disconnect from session so that the updates on updatedPojasnjenje are not directly saved in db
        em.detach(updatedPojasnjenje);
        updatedPojasnjenje
            .tekst(UPDATED_TEKST);
        PojasnjenjeDTO pojasnjenjeDTO = pojasnjenjeMapper.toDto(updatedPojasnjenje);

        restPojasnjenjeMockMvc.perform(put("/api/pojasnjenjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pojasnjenjeDTO)))
            .andExpect(status().isOk());

        // Validate the Pojasnjenje in the database
        List<Pojasnjenje> pojasnjenjeList = pojasnjenjeRepository.findAll();
        assertThat(pojasnjenjeList).hasSize(databaseSizeBeforeUpdate);
        Pojasnjenje testPojasnjenje = pojasnjenjeList.get(pojasnjenjeList.size() - 1);
        assertThat(testPojasnjenje.getTekst()).isEqualTo(UPDATED_TEKST);
    }

    @Test
    @Transactional
    public void updateNonExistingPojasnjenje() throws Exception {
        int databaseSizeBeforeUpdate = pojasnjenjeRepository.findAll().size();

        // Create the Pojasnjenje
        PojasnjenjeDTO pojasnjenjeDTO = pojasnjenjeMapper.toDto(pojasnjenje);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPojasnjenjeMockMvc.perform(put("/api/pojasnjenjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pojasnjenjeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pojasnjenje in the database
        List<Pojasnjenje> pojasnjenjeList = pojasnjenjeRepository.findAll();
        assertThat(pojasnjenjeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePojasnjenje() throws Exception {
        // Initialize the database
        pojasnjenjeRepository.saveAndFlush(pojasnjenje);

        int databaseSizeBeforeDelete = pojasnjenjeRepository.findAll().size();

        // Delete the pojasnjenje
        restPojasnjenjeMockMvc.perform(delete("/api/pojasnjenjes/{id}", pojasnjenje.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Pojasnjenje> pojasnjenjeList = pojasnjenjeRepository.findAll();
        assertThat(pojasnjenjeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pojasnjenje.class);
        Pojasnjenje pojasnjenje1 = new Pojasnjenje();
        pojasnjenje1.setId(1L);
        Pojasnjenje pojasnjenje2 = new Pojasnjenje();
        pojasnjenje2.setId(pojasnjenje1.getId());
        assertThat(pojasnjenje1).isEqualTo(pojasnjenje2);
        pojasnjenje2.setId(2L);
        assertThat(pojasnjenje1).isNotEqualTo(pojasnjenje2);
        pojasnjenje1.setId(null);
        assertThat(pojasnjenje1).isNotEqualTo(pojasnjenje2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PojasnjenjeDTO.class);
        PojasnjenjeDTO pojasnjenjeDTO1 = new PojasnjenjeDTO();
        pojasnjenjeDTO1.setId(1L);
        PojasnjenjeDTO pojasnjenjeDTO2 = new PojasnjenjeDTO();
        assertThat(pojasnjenjeDTO1).isNotEqualTo(pojasnjenjeDTO2);
        pojasnjenjeDTO2.setId(pojasnjenjeDTO1.getId());
        assertThat(pojasnjenjeDTO1).isEqualTo(pojasnjenjeDTO2);
        pojasnjenjeDTO2.setId(2L);
        assertThat(pojasnjenjeDTO1).isNotEqualTo(pojasnjenjeDTO2);
        pojasnjenjeDTO1.setId(null);
        assertThat(pojasnjenjeDTO1).isNotEqualTo(pojasnjenjeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pojasnjenjeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pojasnjenjeMapper.fromId(null)).isNull();
    }
}
