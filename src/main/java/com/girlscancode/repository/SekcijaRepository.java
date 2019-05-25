package com.girlscancode.repository;

import com.girlscancode.domain.Sekcija;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sekcija entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SekcijaRepository extends JpaRepository<Sekcija, Long>, JpaSpecificationExecutor<Sekcija> {

}
