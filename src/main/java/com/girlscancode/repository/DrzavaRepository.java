package com.girlscancode.repository;

import com.girlscancode.domain.Drzava;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Drzava entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrzavaRepository extends JpaRepository<Drzava, Long>, JpaSpecificationExecutor<Drzava> {

}
