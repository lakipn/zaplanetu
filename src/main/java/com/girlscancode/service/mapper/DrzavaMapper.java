package com.girlscancode.service.mapper;

import com.girlscancode.domain.*;
import com.girlscancode.service.dto.DrzavaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Drzava} and its DTO {@link DrzavaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DrzavaMapper extends EntityMapper<DrzavaDTO, Drzava> {



    default Drzava fromId(Long id) {
        if (id == null) {
            return null;
        }
        Drzava drzava = new Drzava();
        drzava.setId(id);
        return drzava;
    }
}
