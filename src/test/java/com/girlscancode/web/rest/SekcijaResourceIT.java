package com.girlscancode.web.rest;

import com.girlscancode.ZaPlanetuApp;
import com.girlscancode.domain.Sekcija;
import com.girlscancode.domain.Pitanje;
import com.girlscancode.repository.SekcijaRepository;
import com.girlscancode.service.SekcijaService;
import com.girlscancode.service.dto.SekcijaDTO;
import com.girlscancode.service.mapper.SekcijaMapper;
import com.girlscancode.web.rest.errors.ExceptionTranslator;
import com.girlscancode.service.dto.SekcijaCriteria;
import com.girlscancode.service.SekcijaQueryService;

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
 * Integration tests for the {@Link SekcijaResource} REST controller.
 */
@SpringBootTest(classes = ZaPlanetuApp.class)
public class SekcijaResourceIT {

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SLIKA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SLIKA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SLIKA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SLIKA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_POJASNJENJE = "AAAAAAAAAA";
    private static final String UPDATED_POJASNJENJE = "BBBBBBBBBB";

    @Autowired
    private SekcijaRepository sekcijaRepository;

    @Autowired
    private SekcijaMapper sekcijaMapper;

    @Autowired
    private SekcijaService sekcijaService;

    @Autowired
    private SekcijaQueryService sekcijaQueryService;

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

    private MockMvc restSekcijaMockMvc;

    private Sekcija sekcija;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SekcijaResource sekcijaResource = new SekcijaResource(sekcijaService, sekcijaQueryService);
        this.restSekcijaMockMvc = MockMvcBuilders.standaloneSetup(sekcijaResource)
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
    public static Sekcija createEntity(EntityManager em) {
        Sekcija sekcija = new Sekcija()
            .naziv(DEFAULT_NAZIV)
            .slika(DEFAULT_SLIKA)
            .slikaContentType(DEFAULT_SLIKA_CONTENT_TYPE)
            .pojasnjenje(DEFAULT_POJASNJENJE);
        return sekcija;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sekcija createUpdatedEntity(EntityManager em) {
        Sekcija sekcija = new Sekcija()
            .naziv(UPDATED_NAZIV)
            .slika(UPDATED_SLIKA)
            .slikaContentType(UPDATED_SLIKA_CONTENT_TYPE)
            .pojasnjenje(UPDATED_POJASNJENJE);
        return sekcija;
    }

    @BeforeEach
    public void initTest() {
        sekcija = createEntity(em);
    }

    @Test
    @Transactional
    public void createSekcija() throws Exception {
        int databaseSizeBeforeCreate = sekcijaRepository.findAll().size();

        // Create the Sekcija
        SekcijaDTO sekcijaDTO = sekcijaMapper.toDto(sekcija);
        restSekcijaMockMvc.perform(post("/api/sekcijas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sekcijaDTO)))
            .andExpect(status().isCreated());

        // Validate the Sekcija in the database
        List<Sekcija> sekcijaList = sekcijaRepository.findAll();
        assertThat(sekcijaList).hasSize(databaseSizeBeforeCreate + 1);
        Sekcija testSekcija = sekcijaList.get(sekcijaList.size() - 1);
        assertThat(testSekcija.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testSekcija.getSlika()).isEqualTo(DEFAULT_SLIKA);
        assertThat(testSekcija.getSlikaContentType()).isEqualTo(DEFAULT_SLIKA_CONTENT_TYPE);
        assertThat(testSekcija.getPojasnjenje()).isEqualTo(DEFAULT_POJASNJENJE);
    }

    @Test
    @Transactional
    public void createSekcijaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sekcijaRepository.findAll().size();

        // Create the Sekcija with an existing ID
        sekcija.setId(1L);
        SekcijaDTO sekcijaDTO = sekcijaMapper.toDto(sekcija);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSekcijaMockMvc.perform(post("/api/sekcijas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sekcijaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sekcija in the database
        List<Sekcija> sekcijaList = sekcijaRepository.findAll();
        assertThat(sekcijaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSekcijas() throws Exception {
        // Initialize the database
        sekcijaRepository.saveAndFlush(sekcija);

        // Get all the sekcijaList
        restSekcijaMockMvc.perform(get("/api/sekcijas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sekcija.getId().intValue())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
            .andExpect(jsonPath("$.[*].slikaContentType").value(hasItem(DEFAULT_SLIKA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].slika").value(hasItem(Base64Utils.encodeToString(DEFAULT_SLIKA))))
            .andExpect(jsonPath("$.[*].pojasnjenje").value(hasItem(DEFAULT_POJASNJENJE.toString())));
    }
    
    @Test
    @Transactional
    public void getSekcija() throws Exception {
        // Initialize the database
        sekcijaRepository.saveAndFlush(sekcija);

        // Get the sekcija
        restSekcijaMockMvc.perform(get("/api/sekcijas/{id}", sekcija.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sekcija.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.slikaContentType").value(DEFAULT_SLIKA_CONTENT_TYPE))
            .andExpect(jsonPath("$.slika").value(Base64Utils.encodeToString(DEFAULT_SLIKA)))
            .andExpect(jsonPath("$.pojasnjenje").value(DEFAULT_POJASNJENJE.toString()));
    }

    @Test
    @Transactional
    public void getAllSekcijasByNazivIsEqualToSomething() throws Exception {
        // Initialize the database
        sekcijaRepository.saveAndFlush(sekcija);

        // Get all the sekcijaList where naziv equals to DEFAULT_NAZIV
        defaultSekcijaShouldBeFound("naziv.equals=" + DEFAULT_NAZIV);

        // Get all the sekcijaList where naziv equals to UPDATED_NAZIV
        defaultSekcijaShouldNotBeFound("naziv.equals=" + UPDATED_NAZIV);
    }

    @Test
    @Transactional
    public void getAllSekcijasByNazivIsInShouldWork() throws Exception {
        // Initialize the database
        sekcijaRepository.saveAndFlush(sekcija);

        // Get all the sekcijaList where naziv in DEFAULT_NAZIV or UPDATED_NAZIV
        defaultSekcijaShouldBeFound("naziv.in=" + DEFAULT_NAZIV + "," + UPDATED_NAZIV);

        // Get all the sekcijaList where naziv equals to UPDATED_NAZIV
        defaultSekcijaShouldNotBeFound("naziv.in=" + UPDATED_NAZIV);
    }

    @Test
    @Transactional
    public void getAllSekcijasByNazivIsNullOrNotNull() throws Exception {
        // Initialize the database
        sekcijaRepository.saveAndFlush(sekcija);

        // Get all the sekcijaList where naziv is not null
        defaultSekcijaShouldBeFound("naziv.specified=true");

        // Get all the sekcijaList where naziv is null
        defaultSekcijaShouldNotBeFound("naziv.specified=false");
    }

    @Test
    @Transactional
    public void getAllSekcijasByPitanjaIsEqualToSomething() throws Exception {
        // Initialize the database
        Pitanje pitanja = PitanjeResourceIT.createEntity(em);
        em.persist(pitanja);
        em.flush();
        sekcija.addPitanja(pitanja);
        sekcijaRepository.saveAndFlush(sekcija);
        Long pitanjaId = pitanja.getId();

        // Get all the sekcijaList where pitanja equals to pitanjaId
        defaultSekcijaShouldBeFound("pitanjaId.equals=" + pitanjaId);

        // Get all the sekcijaList where pitanja equals to pitanjaId + 1
        defaultSekcijaShouldNotBeFound("pitanjaId.equals=" + (pitanjaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSekcijaShouldBeFound(String filter) throws Exception {
        restSekcijaMockMvc.perform(get("/api/sekcijas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sekcija.getId().intValue())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV)))
            .andExpect(jsonPath("$.[*].slikaContentType").value(hasItem(DEFAULT_SLIKA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].slika").value(hasItem(Base64Utils.encodeToString(DEFAULT_SLIKA))))
            .andExpect(jsonPath("$.[*].pojasnjenje").value(hasItem(DEFAULT_POJASNJENJE.toString())));

        // Check, that the count call also returns 1
        restSekcijaMockMvc.perform(get("/api/sekcijas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSekcijaShouldNotBeFound(String filter) throws Exception {
        restSekcijaMockMvc.perform(get("/api/sekcijas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSekcijaMockMvc.perform(get("/api/sekcijas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSekcija() throws Exception {
        // Get the sekcija
        restSekcijaMockMvc.perform(get("/api/sekcijas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSekcija() throws Exception {
        // Initialize the database
        sekcijaRepository.saveAndFlush(sekcija);

        int databaseSizeBeforeUpdate = sekcijaRepository.findAll().size();

        // Update the sekcija
        Sekcija updatedSekcija = sekcijaRepository.findById(sekcija.getId()).get();
        // Disconnect from session so that the updates on updatedSekcija are not directly saved in db
        em.detach(updatedSekcija);
        updatedSekcija
            .naziv(UPDATED_NAZIV)
            .slika(UPDATED_SLIKA)
            .slikaContentType(UPDATED_SLIKA_CONTENT_TYPE)
            .pojasnjenje(UPDATED_POJASNJENJE);
        SekcijaDTO sekcijaDTO = sekcijaMapper.toDto(updatedSekcija);

        restSekcijaMockMvc.perform(put("/api/sekcijas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sekcijaDTO)))
            .andExpect(status().isOk());

        // Validate the Sekcija in the database
        List<Sekcija> sekcijaList = sekcijaRepository.findAll();
        assertThat(sekcijaList).hasSize(databaseSizeBeforeUpdate);
        Sekcija testSekcija = sekcijaList.get(sekcijaList.size() - 1);
        assertThat(testSekcija.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testSekcija.getSlika()).isEqualTo(UPDATED_SLIKA);
        assertThat(testSekcija.getSlikaContentType()).isEqualTo(UPDATED_SLIKA_CONTENT_TYPE);
        assertThat(testSekcija.getPojasnjenje()).isEqualTo(UPDATED_POJASNJENJE);
    }

    @Test
    @Transactional
    public void updateNonExistingSekcija() throws Exception {
        int databaseSizeBeforeUpdate = sekcijaRepository.findAll().size();

        // Create the Sekcija
        SekcijaDTO sekcijaDTO = sekcijaMapper.toDto(sekcija);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSekcijaMockMvc.perform(put("/api/sekcijas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sekcijaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sekcija in the database
        List<Sekcija> sekcijaList = sekcijaRepository.findAll();
        assertThat(sekcijaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSekcija() throws Exception {
        // Initialize the database
        sekcijaRepository.saveAndFlush(sekcija);

        int databaseSizeBeforeDelete = sekcijaRepository.findAll().size();

        // Delete the sekcija
        restSekcijaMockMvc.perform(delete("/api/sekcijas/{id}", sekcija.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Sekcija> sekcijaList = sekcijaRepository.findAll();
        assertThat(sekcijaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sekcija.class);
        Sekcija sekcija1 = new Sekcija();
        sekcija1.setId(1L);
        Sekcija sekcija2 = new Sekcija();
        sekcija2.setId(sekcija1.getId());
        assertThat(sekcija1).isEqualTo(sekcija2);
        sekcija2.setId(2L);
        assertThat(sekcija1).isNotEqualTo(sekcija2);
        sekcija1.setId(null);
        assertThat(sekcija1).isNotEqualTo(sekcija2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SekcijaDTO.class);
        SekcijaDTO sekcijaDTO1 = new SekcijaDTO();
        sekcijaDTO1.setId(1L);
        SekcijaDTO sekcijaDTO2 = new SekcijaDTO();
        assertThat(sekcijaDTO1).isNotEqualTo(sekcijaDTO2);
        sekcijaDTO2.setId(sekcijaDTO1.getId());
        assertThat(sekcijaDTO1).isEqualTo(sekcijaDTO2);
        sekcijaDTO2.setId(2L);
        assertThat(sekcijaDTO1).isNotEqualTo(sekcijaDTO2);
        sekcijaDTO1.setId(null);
        assertThat(sekcijaDTO1).isNotEqualTo(sekcijaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sekcijaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sekcijaMapper.fromId(null)).isNull();
    }
}
