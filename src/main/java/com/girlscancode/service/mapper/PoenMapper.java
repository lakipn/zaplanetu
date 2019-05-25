package com.girlscancode.service.mapper;

import com.girlscancode.domain.*;
import com.girlscancode.service.dto.PoenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Poen} and its DTO {@link PoenDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, DrzavaMapper.class})
public interface PoenMapper extends EntityMapper<PoenDTO, Poen> {

    @Mapping(source = "korisnik.id", target = "korisnikId")
    @Mapping(source = "drzava.id", target = "drzavaId")
    PoenDTO toDto(Poen poen);

    @Mapping(source = "korisnikId", target = "korisnik")
    @Mapping(source = "drzavaId", target = "drzava")
    Poen toEntity(PoenDTO poenDTO);

    default Poen fromId(Long id) {
        if (id == null) {
            return null;
        }
        Poen poen = new Poen();
        poen.setId(id);
        return poen;
    }
}
