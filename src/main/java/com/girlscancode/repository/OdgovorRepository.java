package com.girlscancode.repository;

import com.girlscancode.domain.Odgovor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Odgovor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OdgovorRepository extends JpaRepository<Odgovor, Long>, JpaSpecificationExecutor<Odgovor> {

}
