package com.girlscancode.service.mapper;

import com.girlscancode.domain.*;
import com.girlscancode.service.dto.PojasnjenjeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pojasnjenje} and its DTO {@link PojasnjenjeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PojasnjenjeMapper extends EntityMapper<PojasnjenjeDTO, Pojasnjenje> {


    @Mapping(target = "odgovor", ignore = true)
    Pojasnjenje toEntity(PojasnjenjeDTO pojasnjenjeDTO);

    default Pojasnjenje fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pojasnjenje pojasnjenje = new Pojasnjenje();
        pojasnjenje.setId(id);
        return pojasnjenje;
    }
}
