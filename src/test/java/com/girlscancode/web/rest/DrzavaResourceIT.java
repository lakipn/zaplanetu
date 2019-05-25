package com.girlscancode.web.rest;

import com.girlscancode.ZaPlanetuApp;
import com.girlscancode.domain.Drzava;
import com.girlscancode.repository.DrzavaRepository;
import com.girlscancode.service.DrzavaService;
import com.girlscancode.service.dto.DrzavaDTO;
import com.girlscancode.service.mapper.DrzavaMapper;
import com.girlscancode.web.rest.errors.ExceptionTranslator;
import com.girlscancode.service.dto.DrzavaCriteria;
import com.girlscancode.service.DrzavaQueryService;

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
 * Integration tests for the {@Link DrzavaResource} REST controller.
 */
@SpringBootTest(classes = ZaPlanetuApp.class)
public class DrzavaResourceIT {

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SLIKA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SLIKA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SLIKA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SLIKA_CONTENT_TYPE = "image/png";

    @Autowired
    private DrzavaRepository drzavaRepository;

    @Autowired
    private DrzavaMapper drzavaMapper;

    @Autowired
    private DrzavaService drzavaService;

    @Autowired
    private DrzavaQueryService drzavaQueryService;

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

    private MockMvc restDrzavaMockMvc;

    private Drzava drzava;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DrzavaResource drzavaResource = new DrzavaResource(drzavaService, drzavaQueryService);
        this.restDrzavaMockMvc = MockMvcBuilders.standaloneSetup(drzavaResource)
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
    public static Drzava createEntity(EntityManager em) {
        Drzava drzava = new Drzava()
            .naziv(DEFAULT_NAZIV)
            .slika(DEFAULT_SLIKA)
            .slikaContentType(DEFAULT_SLIKA_CONTENT_TYPE);
        return drzava;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drzava createUpdatedEntity(EntityManager em) {
        Drzava drzava = new Drzava()
            .naziv(UPDATED_NAZIV)
            .slika(UPDATED_SLIKA)
            .slikaContentType(UPDATED_SLIKA_CONTENT_TYPE);
        return drzava;
    }

    @BeforeEach
    public void initTest() {
        drzava = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrzava() throws Exception {
        int databaseSizeBeforeCreate = drzavaRepository.findAll().size();

        // Create the Drzava
        DrzavaDTO drzavaDTO = drzavaMapper.toDto(drzava);
        restDrzavaMockMvc.perform(post("/api/drzavas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drzavaDTO)))
            .andExpect(status().isCreated());

        // Validate the Drzava in the database
        List<Drzava> drzavaList = drzavaRepository.findAll();
        assertThat(drzavaList).hasSize(databaseSizeBeforeCreate + 1);
        Drzava testDrzava = drzavaList.get(drzavaList.size() - 1);
        assertThat(testDrzava.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testDrzava.getSlika()).isEqualTo(DEFAULT_SLIKA);
        assertThat(testDrzava.getSlikaContentType()).isEqualTo(DEFAULT_SLIKA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createDrzavaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drzavaRepository.findAll().size();

        // Create the Drzava with an existing ID
        drzava.setId(1L);
        DrzavaDTO drzavaDTO = drzavaMapper.toDto(drzava);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrzavaMockMvc.perform(post("/api/drzavas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drzavaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Drzava in the database
        List<Drzava> drzavaList = drzavaRepository.findAll();
        assertThat(drzavaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDrzavas() throws Exception {
        // Initialize the database
        drzavaRepository.saveAndFlush(drzava);

        // Get all the drzavaList
        restDrzavaMockMvc.perform(get("/api/drzavas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drzava.getId().intValue())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
            .andExpect(jsonPath("$.[*].slikaContentType").value(hasItem(DEFAULT_SLIKA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].slika").value(hasItem(Base64Utils.encodeToString(DEFAULT_SLIKA))));
    }
    
    @Test
    @Transactional
    public void getDrzava() throws Exception {
        // Initialize the database
        drzavaRepository.saveAndFlush(drzava);

        // Get the drzava
        restDrzavaMockMvc.perform(get("/api/drzavas/{id}", drzava.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drzava.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.slikaContentType").value(DEFAULT_SLIKA_CONTENT_TYPE))
            .andExpect(jsonPath("$.slika").value(Base64Utils.encodeToString(DEFAULT_SLIKA)));
    }

    @Test
    @Transactional
    public void getAllDrzavasByNazivIsEqualToSomething() throws Exception {
        // Initialize the database
        drzavaRepository.saveAndFlush(drzava);

        // Get all the drzavaList where naziv equals to DEFAULT_NAZIV
        defaultDrzavaShouldBeFound("naziv.equals=" + DEFAULT_NAZIV);

        // Get all the drzavaList where naziv equals to UPDATED_NAZIV
        defaultDrzavaShouldNotBeFound("naziv.equals=" + UPDATED_NAZIV);
    }

    @Test
    @Transactional
    public void getAllDrzavasByNazivIsInShouldWork() throws Exception {
        // Initialize the database
        drzavaRepository.saveAndFlush(drzava);

        // Get all the drzavaList where naziv in DEFAULT_NAZIV or UPDATED_NAZIV
        defaultDrzavaShouldBeFound("naziv.in=" + DEFAULT_NAZIV + "," + UPDATED_NAZIV);

        // Get all the drzavaList where naziv equals to UPDATED_NAZIV
        defaultDrzavaShouldNotBeFound("naziv.in=" + UPDATED_NAZIV);
    }

    @Test
    @Transactional
    public void getAllDrzavasByNazivIsNullOrNotNull() throws Exception {
        // Initialize the database
        drzavaRepository.saveAndFlush(drzava);

        // Get all the drzavaList where naziv is not null
        defaultDrzavaShouldBeFound("naziv.specified=true");

        // Get all the drzavaList where naziv is null
        defaultDrzavaShouldNotBeFound("naziv.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDrzavaShouldBeFound(String filter) throws Exception {
        restDrzavaMockMvc.perform(get("/api/drzavas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drzava.getId().intValue())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV)))
            .andExpect(jsonPath("$.[*].slikaContentType").value(hasItem(DEFAULT_SLIKA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].slika").value(hasItem(Base64Utils.encodeToString(DEFAULT_SLIKA))));

        // Check, that the count call also returns 1
        restDrzavaMockMvc.perform(get("/api/drzavas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDrzavaShouldNotBeFound(String filter) throws Exception {
        restDrzavaMockMvc.perform(get("/api/drzavas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDrzavaMockMvc.perform(get("/api/drzavas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDrzava() throws Exception {
        // Get the drzava
        restDrzavaMockMvc.perform(get("/api/drzavas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrzava() throws Exception {
        // Initialize the database
        drzavaRepository.saveAndFlush(drzava);

        int databaseSizeBeforeUpdate = drzavaRepository.findAll().size();

        // Update the drzava
        Drzava updatedDrzava = drzavaRepository.findById(drzava.getId()).get();
        // Disconnect from session so that the updates on updatedDrzava are not directly saved in db
        em.detach(updatedDrzava);
        updatedDrzava
            .naziv(UPDATED_NAZIV)
            .slika(UPDATED_SLIKA)
            .slikaContentType(UPDATED_SLIKA_CONTENT_TYPE);
        DrzavaDTO drzavaDTO = drzavaMapper.toDto(updatedDrzava);

        restDrzavaMockMvc.perform(put("/api/drzavas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drzavaDTO)))
            .andExpect(status().isOk());

        // Validate the Drzava in the database
        List<Drzava> drzavaList = drzavaRepository.findAll();
        assertThat(drzavaList).hasSize(databaseSizeBeforeUpdate);
        Drzava testDrzava = drzavaList.get(drzavaList.size() - 1);
        assertThat(testDrzava.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testDrzava.getSlika()).isEqualTo(UPDATED_SLIKA);
        assertThat(testDrzava.getSlikaContentType()).isEqualTo(UPDATED_SLIKA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDrzava() throws Exception {
        int databaseSizeBeforeUpdate = drzavaRepository.findAll().size();

        // Create the Drzava
        DrzavaDTO drzavaDTO = drzavaMapper.toDto(drzava);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrzavaMockMvc.perform(put("/api/drzavas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drzavaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Drzava in the database
        List<Drzava> drzavaList = drzavaRepository.findAll();
        assertThat(drzavaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDrzava() throws Exception {
        // Initialize the database
        drzavaRepository.saveAndFlush(drzava);

        int databaseSizeBeforeDelete = drzavaRepository.findAll().size();

        // Delete the drzava
        restDrzavaMockMvc.perform(delete("/api/drzavas/{id}", drzava.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Drzava> drzavaList = drzavaRepository.findAll();
        assertThat(drzavaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Drzava.class);
        Drzava drzava1 = new Drzava();
        drzava1.setId(1L);
        Drzava drzava2 = new Drzava();
        drzava2.setId(drzava1.getId());
        assertThat(drzava1).isEqualTo(drzava2);
        drzava2.setId(2L);
        assertThat(drzava1).isNotEqualTo(drzava2);
        drzava1.setId(null);
        assertThat(drzava1).isNotEqualTo(drzava2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrzavaDTO.class);
        DrzavaDTO drzavaDTO1 = new DrzavaDTO();
        drzavaDTO1.setId(1L);
        DrzavaDTO drzavaDTO2 = new DrzavaDTO();
        assertThat(drzavaDTO1).isNotEqualTo(drzavaDTO2);
        drzavaDTO2.setId(drzavaDTO1.getId());
        assertThat(drzavaDTO1).isEqualTo(drzavaDTO2);
        drzavaDTO2.setId(2L);
        assertThat(drzavaDTO1).isNotEqualTo(drzavaDTO2);
        drzavaDTO1.setId(null);
        assertThat(drzavaDTO1).isNotEqualTo(drzavaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(drzavaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(drzavaMapper.fromId(null)).isNull();
    }
}
