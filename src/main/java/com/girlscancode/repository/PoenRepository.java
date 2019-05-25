package com.girlscancode.repository;

import com.girlscancode.domain.Poen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Poen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoenRepository extends JpaRepository<Poen, Long>, JpaSpecificationExecutor<Poen> {

    @Query("select poen from Poen poen where poen.korisnik.login = ?#{principal.username}")
    List<Poen> findByKorisnikIsCurrentUser();

}
