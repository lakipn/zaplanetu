package com.girlscancode.repository;

import com.girlscancode.domain.Pojasnjenje;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pojasnjenje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PojasnjenjeRepository extends JpaRepository<Pojasnjenje, Long>, JpaSpecificationExecutor<Pojasnjenje> {

}
