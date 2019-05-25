package com.girlscancode.repository;

import com.girlscancode.domain.Pitanje;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pitanje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PitanjeRepository extends JpaRepository<Pitanje, Long>, JpaSpecificationExecutor<Pitanje> {

}
