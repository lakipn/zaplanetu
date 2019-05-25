package com.girlscancode.web.rest;

import com.girlscancode.ZaPlanetuApp;
import com.girlscancode.domain.Pitanje;
import com.girlscancode.domain.Sekcija;
import com.girlscancode.domain.Odgovor;
import com.girlscancode.repository.PitanjeRepository;
import com.girlscancode.service.PitanjeService;
import com.girlscancode.service.dto.PitanjeDTO;
import com.girlscancode.service.mapper.PitanjeMapper;
import com.girlscancode.web.rest.errors.ExceptionTranslator;
import com.girlscancode.service.dto.PitanjeCriteria;
import com.girlscancode.service.PitanjeQueryService;

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
 * Integration tests for the {@Link PitanjeResource} REST controller.
 */
@SpringBootTest(classes = ZaPlanetuApp.class)
public class PitanjeResourceIT {

    private static final String DEFAULT_TEKST = "AAAAAAAAAA";
    private static final String UPDATED_TEKST = "BBBBBBBBBB";

    private static final Integer DEFAULT_REDNI_BROJ = 1;
    private static final Integer UPDATED_REDNI_BROJ = 2;

    @Autowired
    private PitanjeRepository pitanjeRepository;

    @Autowired
    private PitanjeMapper pitanjeMapper;

    @Autowired
    private PitanjeService pitanjeService;

    @Autowired
    private PitanjeQueryService pitanjeQueryService;

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

    private MockMvc restPitanjeMockMvc;

    private Pitanje pitanje;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PitanjeResource pitanjeResource = new PitanjeResource(pitanjeService, pitanjeQueryService);
        this.restPitanjeMockMvc = MockMvcBuilders.standaloneSetup(pitanjeResource)
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
    public static Pitanje createEntity(EntityManager em) {
        Pitanje pitanje = new Pitanje()
            .tekst(DEFAULT_TEKST)
            .redniBroj(DEFAULT_REDNI_BROJ);
        return pitanje;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pitanje createUpdatedEntity(EntityManager em) {
        Pitanje pitanje = new Pitanje()
            .tekst(UPDATED_TEKST)
            .redniBroj(UPDATED_REDNI_BROJ);
        return pitanje;
    }

    @BeforeEach
    public void initTest() {
        pitanje = createEntity(em);
    }

    @Test
    @Transactional
    public void createPitanje() throws Exception {
        int databaseSizeBeforeCreate = pitanjeRepository.findAll().size();

        // Create the Pitanje
        PitanjeDTO pitanjeDTO = pitanjeMapper.toDto(pitanje);
        restPitanjeMockMvc.perform(post("/api/pitanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pitanjeDTO)))
            .andExpect(status().isCreated());

        // Validate the Pitanje in the database
        List<Pitanje> pitanjeList = pitanjeRepository.findAll();
        assertThat(pitanjeList).hasSize(databaseSizeBeforeCreate + 1);
        Pitanje testPitanje = pitanjeList.get(pitanjeList.size() - 1);
        assertThat(testPitanje.getTekst()).isEqualTo(DEFAULT_TEKST);
        assertThat(testPitanje.getRedniBroj()).isEqualTo(DEFAULT_REDNI_BROJ);
    }

    @Test
    @Transactional
    public void createPitanjeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pitanjeRepository.findAll().size();

        // Create the Pitanje with an existing ID
        pitanje.setId(1L);
        PitanjeDTO pitanjeDTO = pitanjeMapper.toDto(pitanje);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPitanjeMockMvc.perform(post("/api/pitanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pitanjeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pitanje in the database
        List<Pitanje> pitanjeList = pitanjeRepository.findAll();
        assertThat(pitanjeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPitanjes() throws Exception {
        // Initialize the database
        pitanjeRepository.saveAndFlush(pitanje);

        // Get all the pitanjeList
        restPitanjeMockMvc.perform(get("/api/pitanjes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pitanje.getId().intValue())))
            .andExpect(jsonPath("$.[*].tekst").value(hasItem(DEFAULT_TEKST.toString())))
            .andExpect(jsonPath("$.[*].redniBroj").value(hasItem(DEFAULT_REDNI_BROJ)));
    }
    
    @Test
    @Transactional
    public void getPitanje() throws Exception {
        // Initialize the database
        pitanjeRepository.saveAndFlush(pitanje);

        // Get the pitanje
        restPitanjeMockMvc.perform(get("/api/pitanjes/{id}", pitanje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pitanje.getId().intValue()))
            .andExpect(jsonPath("$.tekst").value(DEFAULT_TEKST.toString()))
            .andExpect(jsonPath("$.redniBroj").value(DEFAULT_REDNI_BROJ));
    }

    @Test
    @Transactional
    public void getAllPitanjesByTekstIsEqualToSomething() throws Exception {
        // Initialize the database
        pitanjeRepository.saveAndFlush(pitanje);

        // Get all the pitanjeList where tekst equals to DEFAULT_TEKST
        defaultPitanjeShouldBeFound("tekst.equals=" + DEFAULT_TEKST);

        // Get all the pitanjeList where tekst equals to UPDATED_TEKST
        defaultPitanjeShouldNotBeFound("tekst.equals=" + UPDATED_TEKST);
    }

    @Test
    @Transactional
    public void getAllPitanjesByTekstIsInShouldWork() throws Exception {
        // Initialize the database
        pitanjeRepository.saveAndFlush(pitanje);

        // Get all the pitanjeList where tekst in DEFAULT_TEKST or UPDATED_TEKST
        defaultPitanjeShouldBeFound("tekst.in=" + DEFAULT_TEKST + "," + UPDATED_TEKST);

        // Get all the pitanjeList where tekst equals to UPDATED_TEKST
        defaultPitanjeShouldNotBeFound("tekst.in=" + UPDATED_TEKST);
    }

    @Test
    @Transactional
    public void getAllPitanjesByTekstIsNullOrNotNull() throws Exception {
        // Initialize the database
        pitanjeRepository.saveAndFlush(pitanje);

        // Get all the pitanjeList where tekst is not null
        defaultPitanjeShouldBeFound("tekst.specified=true");

        // Get all the pitanjeList where tekst is null
        defaultPitanjeShouldNotBeFound("tekst.specified=false");
    }

    @Test
    @Transactional
    public void getAllPitanjesByRedniBrojIsEqualToSomething() throws Exception {
        // Initialize the database
        pitanjeRepository.saveAndFlush(pitanje);

        // Get all the pitanjeList where redniBroj equals to DEFAULT_REDNI_BROJ
        defaultPitanjeShouldBeFound("redniBroj.equals=" + DEFAULT_REDNI_BROJ);

        // Get all the pitanjeList where redniBroj equals to UPDATED_REDNI_BROJ
        defaultPitanjeShouldNotBeFound("redniBroj.equals=" + UPDATED_REDNI_BROJ);
    }

    @Test
    @Transactional
    public void getAllPitanjesByRedniBrojIsInShouldWork() throws Exception {
        // Initialize the database
        pitanjeRepository.saveAndFlush(pitanje);

        // Get all the pitanjeList where redniBroj in DEFAULT_REDNI_BROJ or UPDATED_REDNI_BROJ
        defaultPitanjeShouldBeFound("redniBroj.in=" + DEFAULT_REDNI_BROJ + "," + UPDATED_REDNI_BROJ);

        // Get all the pitanjeList where redniBroj equals to UPDATED_REDNI_BROJ
        defaultPitanjeShouldNotBeFound("redniBroj.in=" + UPDATED_REDNI_BROJ);
    }

    @Test
    @Transactional
    public void getAllPitanjesByRedniBrojIsNullOrNotNull() throws Exception {
        // Initialize the database
        pitanjeRepository.saveAndFlush(pitanje);

        // Get all the pitanjeList where redniBroj is not null
        defaultPitanjeShouldBeFound("redniBroj.specified=true");

        // Get all the pitanjeList where redniBroj is null
        defaultPitanjeShouldNotBeFound("redniBroj.specified=false");
    }

    @Test
    @Transactional
    public void getAllPitanjesByRedniBrojIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pitanjeRepository.saveAndFlush(pitanje);

        // Get all the pitanjeList where redniBroj greater than or equals to DEFAULT_REDNI_BROJ
        defaultPitanjeShouldBeFound("redniBroj.greaterOrEqualThan=" + DEFAULT_REDNI_BROJ);

        // Get all the pitanjeList where redniBroj greater than or equals to UPDATED_REDNI_BROJ
        defaultPitanjeShouldNotBeFound("redniBroj.greaterOrEqualThan=" + UPDATED_REDNI_BROJ);
    }

    @Test
    @Transactional
    public void getAllPitanjesByRedniBrojIsLessThanSomething() throws Exception {
        // Initialize the database
        pitanjeRepository.saveAndFlush(pitanje);

        // Get all the pitanjeList where redniBroj less than or equals to DEFAULT_REDNI_BROJ
        defaultPitanjeShouldNotBeFound("redniBroj.lessThan=" + DEFAULT_REDNI_BROJ);

        // Get all the pitanjeList where redniBroj less than or equals to UPDATED_REDNI_BROJ
        defaultPitanjeShouldBeFound("redniBroj.lessThan=" + UPDATED_REDNI_BROJ);
    }


    @Test
    @Transactional
    public void getAllPitanjesBySekcijaIsEqualToSomething() throws Exception {
        // Initialize the database
        Sekcija sekcija = SekcijaResourceIT.createEntity(em);
        em.persist(sekcija);
        em.flush();
        pitanje.setSekcija(sekcija);
        pitanjeRepository.saveAndFlush(pitanje);
        Long sekcijaId = sekcija.getId();

        // Get all the pitanjeList where sekcija equals to sekcijaId
        defaultPitanjeShouldBeFound("sekcijaId.equals=" + sekcijaId);

        // Get all the pitanjeList where sekcija equals to sekcijaId + 1
        defaultPitanjeShouldNotBeFound("sekcijaId.equals=" + (sekcijaId + 1));
    }


    @Test
    @Transactional
    public void getAllPitanjesByOdgovoriIsEqualToSomething() throws Exception {
        // Initialize the database
        Odgovor odgovori = OdgovorResourceIT.createEntity(em);
        em.persist(odgovori);
        em.flush();
        pitanje.addOdgovori(odgovori);
        pitanjeRepository.saveAndFlush(pitanje);
        Long odgovoriId = odgovori.getId();

        // Get all the pitanjeList where odgovori equals to odgovoriId
        defaultPitanjeShouldBeFound("odgovoriId.equals=" + odgovoriId);

        // Get all the pitanjeList where odgovori equals to odgovoriId + 1
        defaultPitanjeShouldNotBeFound("odgovoriId.equals=" + (odgovoriId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPitanjeShouldBeFound(String filter) throws Exception {
        restPitanjeMockMvc.perform(get("/api/pitanjes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pitanje.getId().intValue())))
            .andExpect(jsonPath("$.[*].tekst").value(hasItem(DEFAULT_TEKST)))
            .andExpect(jsonPath("$.[*].redniBroj").value(hasItem(DEFAULT_REDNI_BROJ)));

        // Check, that the count call also returns 1
        restPitanjeMockMvc.perform(get("/api/pitanjes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPitanjeShouldNotBeFound(String filter) throws Exception {
        restPitanjeMockMvc.perform(get("/api/pitanjes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPitanjeMockMvc.perform(get("/api/pitanjes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPitanje() throws Exception {
        // Get the pitanje
        restPitanjeMockMvc.perform(get("/api/pitanjes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePitanje() throws Exception {
        // Initialize the database
        pitanjeRepository.saveAndFlush(pitanje);

        int databaseSizeBeforeUpdate = pitanjeRepository.findAll().size();

        // Update the pitanje
        Pitanje updatedPitanje = pitanjeRepository.findById(pitanje.getId()).get();
        // Disconnect from session so that the updates on updatedPitanje are not directly saved in db
        em.detach(updatedPitanje);
        updatedPitanje
            .tekst(UPDATED_TEKST)
            .redniBroj(UPDATED_REDNI_BROJ);
        PitanjeDTO pitanjeDTO = pitanjeMapper.toDto(updatedPitanje);

        restPitanjeMockMvc.perform(put("/api/pitanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pitanjeDTO)))
            .andExpect(status().isOk());

        // Validate the Pitanje in the database
        List<Pitanje> pitanjeList = pitanjeRepository.findAll();
        assertThat(pitanjeList).hasSize(databaseSizeBeforeUpdate);
        Pitanje testPitanje = pitanjeList.get(pitanjeList.size() - 1);
        assertThat(testPitanje.getTekst()).isEqualTo(UPDATED_TEKST);
        assertThat(testPitanje.getRedniBroj()).isEqualTo(UPDATED_REDNI_BROJ);
    }

    @Test
    @Transactional
    public void updateNonExistingPitanje() throws Exception {
        int databaseSizeBeforeUpdate = pitanjeRepository.findAll().size();

        // Create the Pitanje
        PitanjeDTO pitanjeDTO = pitanjeMapper.toDto(pitanje);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPitanjeMockMvc.perform(put("/api/pitanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pitanjeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pitanje in the database
        List<Pitanje> pitanjeList = pitanjeRepository.findAll();
        assertThat(pitanjeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePitanje() throws Exception {
        // Initialize the database
        pitanjeRepository.saveAndFlush(pitanje);

        int databaseSizeBeforeDelete = pitanjeRepository.findAll().size();

        // Delete the pitanje
        restPitanjeMockMvc.perform(delete("/api/pitanjes/{id}", pitanje.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Pitanje> pitanjeList = pitanjeRepository.findAll();
        assertThat(pitanjeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pitanje.class);
        Pitanje pitanje1 = new Pitanje();
        pitanje1.setId(1L);
        Pitanje pitanje2 = new Pitanje();
        pitanje2.setId(pitanje1.getId());
        assertThat(pitanje1).isEqualTo(pitanje2);
        pitanje2.setId(2L);
        assertThat(pitanje1).isNotEqualTo(pitanje2);
        pitanje1.setId(null);
        assertThat(pitanje1).isNotEqualTo(pitanje2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PitanjeDTO.class);
        PitanjeDTO pitanjeDTO1 = new PitanjeDTO();
        pitanjeDTO1.setId(1L);
        PitanjeDTO pitanjeDTO2 = new PitanjeDTO();
        assertThat(pitanjeDTO1).isNotEqualTo(pitanjeDTO2);
        pitanjeDTO2.setId(pitanjeDTO1.getId());
        assertThat(pitanjeDTO1).isEqualTo(pitanjeDTO2);
        pitanjeDTO2.setId(2L);
        assertThat(pitanjeDTO1).isNotEqualTo(pitanjeDTO2);
        pitanjeDTO1.setId(null);
        assertThat(pitanjeDTO1).isNotEqualTo(pitanjeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pitanjeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pitanjeMapper.fromId(null)).isNull();
    }
}
