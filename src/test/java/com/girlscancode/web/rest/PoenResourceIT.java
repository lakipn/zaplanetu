package com.girlscancode.web.rest;

import com.girlscancode.ZaPlanetuApp;
import com.girlscancode.domain.Poen;
import com.girlscancode.domain.User;
import com.girlscancode.domain.Drzava;
import com.girlscancode.repository.PoenRepository;
import com.girlscancode.service.PoenService;
import com.girlscancode.service.dto.PoenDTO;
import com.girlscancode.service.mapper.PoenMapper;
import com.girlscancode.web.rest.errors.ExceptionTranslator;
import com.girlscancode.service.dto.PoenCriteria;
import com.girlscancode.service.PoenQueryService;

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

import com.girlscancode.domain.enumeration.TipPoena;
/**
 * Integration tests for the {@Link PoenResource} REST controller.
 */
@SpringBootTest(classes = ZaPlanetuApp.class)
public class PoenResourceIT {

    private static final TipPoena DEFAULT_TIP = TipPoena.RECIKLAZA;
    private static final TipPoena UPDATED_TIP = TipPoena.SADNJA;

    @Autowired
    private PoenRepository poenRepository;

    @Autowired
    private PoenMapper poenMapper;

    @Autowired
    private PoenService poenService;

    @Autowired
    private PoenQueryService poenQueryService;

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

    private MockMvc restPoenMockMvc;

    private Poen poen;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoenResource poenResource = new PoenResource(poenService, poenQueryService);
        this.restPoenMockMvc = MockMvcBuilders.standaloneSetup(poenResource)
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
    public static Poen createEntity(EntityManager em) {
        Poen poen = new Poen()
            .tip(DEFAULT_TIP);
        return poen;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Poen createUpdatedEntity(EntityManager em) {
        Poen poen = new Poen()
            .tip(UPDATED_TIP);
        return poen;
    }

    @BeforeEach
    public void initTest() {
        poen = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoen() throws Exception {
        int databaseSizeBeforeCreate = poenRepository.findAll().size();

        // Create the Poen
        PoenDTO poenDTO = poenMapper.toDto(poen);
        restPoenMockMvc.perform(post("/api/poens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poenDTO)))
            .andExpect(status().isCreated());

        // Validate the Poen in the database
        List<Poen> poenList = poenRepository.findAll();
        assertThat(poenList).hasSize(databaseSizeBeforeCreate + 1);
        Poen testPoen = poenList.get(poenList.size() - 1);
        assertThat(testPoen.getTip()).isEqualTo(DEFAULT_TIP);
    }

    @Test
    @Transactional
    public void createPoenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poenRepository.findAll().size();

        // Create the Poen with an existing ID
        poen.setId(1L);
        PoenDTO poenDTO = poenMapper.toDto(poen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoenMockMvc.perform(post("/api/poens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Poen in the database
        List<Poen> poenList = poenRepository.findAll();
        assertThat(poenList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPoens() throws Exception {
        // Initialize the database
        poenRepository.saveAndFlush(poen);

        // Get all the poenList
        restPoenMockMvc.perform(get("/api/poens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poen.getId().intValue())))
            .andExpect(jsonPath("$.[*].tip").value(hasItem(DEFAULT_TIP.toString())));
    }
    
    @Test
    @Transactional
    public void getPoen() throws Exception {
        // Initialize the database
        poenRepository.saveAndFlush(poen);

        // Get the poen
        restPoenMockMvc.perform(get("/api/poens/{id}", poen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poen.getId().intValue()))
            .andExpect(jsonPath("$.tip").value(DEFAULT_TIP.toString()));
    }

    @Test
    @Transactional
    public void getAllPoensByTipIsEqualToSomething() throws Exception {
        // Initialize the database
        poenRepository.saveAndFlush(poen);

        // Get all the poenList where tip equals to DEFAULT_TIP
        defaultPoenShouldBeFound("tip.equals=" + DEFAULT_TIP);

        // Get all the poenList where tip equals to UPDATED_TIP
        defaultPoenShouldNotBeFound("tip.equals=" + UPDATED_TIP);
    }

    @Test
    @Transactional
    public void getAllPoensByTipIsInShouldWork() throws Exception {
        // Initialize the database
        poenRepository.saveAndFlush(poen);

        // Get all the poenList where tip in DEFAULT_TIP or UPDATED_TIP
        defaultPoenShouldBeFound("tip.in=" + DEFAULT_TIP + "," + UPDATED_TIP);

        // Get all the poenList where tip equals to UPDATED_TIP
        defaultPoenShouldNotBeFound("tip.in=" + UPDATED_TIP);
    }

    @Test
    @Transactional
    public void getAllPoensByTipIsNullOrNotNull() throws Exception {
        // Initialize the database
        poenRepository.saveAndFlush(poen);

        // Get all the poenList where tip is not null
        defaultPoenShouldBeFound("tip.specified=true");

        // Get all the poenList where tip is null
        defaultPoenShouldNotBeFound("tip.specified=false");
    }

    @Test
    @Transactional
    public void getAllPoensByKorisnikIsEqualToSomething() throws Exception {
        // Initialize the database
        User korisnik = UserResourceIT.createEntity(em);
        em.persist(korisnik);
        em.flush();
        poen.setKorisnik(korisnik);
        poenRepository.saveAndFlush(poen);
        Long korisnikId = korisnik.getId();

        // Get all the poenList where korisnik equals to korisnikId
        defaultPoenShouldBeFound("korisnikId.equals=" + korisnikId);

        // Get all the poenList where korisnik equals to korisnikId + 1
        defaultPoenShouldNotBeFound("korisnikId.equals=" + (korisnikId + 1));
    }


    @Test
    @Transactional
    public void getAllPoensByDrzavaIsEqualToSomething() throws Exception {
        // Initialize the database
        Drzava drzava = DrzavaResourceIT.createEntity(em);
        em.persist(drzava);
        em.flush();
        poen.setDrzava(drzava);
        poenRepository.saveAndFlush(poen);
        Long drzavaId = drzava.getId();

        // Get all the poenList where drzava equals to drzavaId
        defaultPoenShouldBeFound("drzavaId.equals=" + drzavaId);

        // Get all the poenList where drzava equals to drzavaId + 1
        defaultPoenShouldNotBeFound("drzavaId.equals=" + (drzavaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPoenShouldBeFound(String filter) throws Exception {
        restPoenMockMvc.perform(get("/api/poens?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poen.getId().intValue())))
            .andExpect(jsonPath("$.[*].tip").value(hasItem(DEFAULT_TIP.toString())));

        // Check, that the count call also returns 1
        restPoenMockMvc.perform(get("/api/poens/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPoenShouldNotBeFound(String filter) throws Exception {
        restPoenMockMvc.perform(get("/api/poens?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPoenMockMvc.perform(get("/api/poens/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPoen() throws Exception {
        // Get the poen
        restPoenMockMvc.perform(get("/api/poens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoen() throws Exception {
        // Initialize the database
        poenRepository.saveAndFlush(poen);

        int databaseSizeBeforeUpdate = poenRepository.findAll().size();

        // Update the poen
        Poen updatedPoen = poenRepository.findById(poen.getId()).get();
        // Disconnect from session so that the updates on updatedPoen are not directly saved in db
        em.detach(updatedPoen);
        updatedPoen
            .tip(UPDATED_TIP);
        PoenDTO poenDTO = poenMapper.toDto(updatedPoen);

        restPoenMockMvc.perform(put("/api/poens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poenDTO)))
            .andExpect(status().isOk());

        // Validate the Poen in the database
        List<Poen> poenList = poenRepository.findAll();
        assertThat(poenList).hasSize(databaseSizeBeforeUpdate);
        Poen testPoen = poenList.get(poenList.size() - 1);
        assertThat(testPoen.getTip()).isEqualTo(UPDATED_TIP);
    }

    @Test
    @Transactional
    public void updateNonExistingPoen() throws Exception {
        int databaseSizeBeforeUpdate = poenRepository.findAll().size();

        // Create the Poen
        PoenDTO poenDTO = poenMapper.toDto(poen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoenMockMvc.perform(put("/api/poens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Poen in the database
        List<Poen> poenList = poenRepository.findAll();
        assertThat(poenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePoen() throws Exception {
        // Initialize the database
        poenRepository.saveAndFlush(poen);

        int databaseSizeBeforeDelete = poenRepository.findAll().size();

        // Delete the poen
        restPoenMockMvc.perform(delete("/api/poens/{id}", poen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Poen> poenList = poenRepository.findAll();
        assertThat(poenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Poen.class);
        Poen poen1 = new Poen();
        poen1.setId(1L);
        Poen poen2 = new Poen();
        poen2.setId(poen1.getId());
        assertThat(poen1).isEqualTo(poen2);
        poen2.setId(2L);
        assertThat(poen1).isNotEqualTo(poen2);
        poen1.setId(null);
        assertThat(poen1).isNotEqualTo(poen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoenDTO.class);
        PoenDTO poenDTO1 = new PoenDTO();
        poenDTO1.setId(1L);
        PoenDTO poenDTO2 = new PoenDTO();
        assertThat(poenDTO1).isNotEqualTo(poenDTO2);
        poenDTO2.setId(poenDTO1.getId());
        assertThat(poenDTO1).isEqualTo(poenDTO2);
        poenDTO2.setId(2L);
        assertThat(poenDTO1).isNotEqualTo(poenDTO2);
        poenDTO1.setId(null);
        assertThat(poenDTO1).isNotEqualTo(poenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(poenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(poenMapper.fromId(null)).isNull();
    }
}
