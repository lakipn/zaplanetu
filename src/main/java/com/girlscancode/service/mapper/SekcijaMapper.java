package com.girlscancode.service.mapper;

import com.girlscancode.domain.*;
import com.girlscancode.service.dto.SekcijaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sekcija} and its DTO {@link SekcijaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SekcijaMapper extends EntityMapper<SekcijaDTO, Sekcija> {


    @Mapping(target = "pitanjas", ignore = true)
    Sekcija toEntity(SekcijaDTO sekcijaDTO);

    default Sekcija fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sekcija sekcija = new Sekcija();
        sekcija.setId(id);
        return sekcija;
    }
}
