package com.girlscancode.service.mapper;

import com.girlscancode.domain.*;
import com.girlscancode.service.dto.OdgovorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Odgovor} and its DTO {@link OdgovorDTO}.
 */
@Mapper(componentModel = "spring", uses = {PojasnjenjeMapper.class, PitanjeMapper.class})
public interface OdgovorMapper extends EntityMapper<OdgovorDTO, Odgovor> {

    @Mapping(source = "pojasnjenje.id", target = "pojasnjenjeId")
    @Mapping(source = "pitanje.id", target = "pitanjeId")
    OdgovorDTO toDto(Odgovor odgovor);

    @Mapping(source = "pojasnjenjeId", target = "pojasnjenje")
    @Mapping(source = "pitanjeId", target = "pitanje")
    Odgovor toEntity(OdgovorDTO odgovorDTO);

    default Odgovor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Odgovor odgovor = new Odgovor();
        odgovor.setId(id);
        return odgovor;
    }
}
