package com.girlscancode.service.mapper;

import com.girlscancode.domain.*;
import com.girlscancode.service.dto.PitanjeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pitanje} and its DTO {@link PitanjeDTO}.
 */
@Mapper(componentModel = "spring", uses = {SekcijaMapper.class})
public interface PitanjeMapper extends EntityMapper<PitanjeDTO, Pitanje> {

    @Mapping(source = "sekcija.id", target = "sekcijaId")
    @Mapping(source = "sekcija.naziv", target = "sekcijaNaziv")
    PitanjeDTO toDto(Pitanje pitanje);

    @Mapping(source = "sekcijaId", target = "sekcija")
    @Mapping(target = "odgovoris", ignore = true)
    Pitanje toEntity(PitanjeDTO pitanjeDTO);

    default Pitanje fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pitanje pitanje = new Pitanje();
        pitanje.setId(id);
        return pitanje;
    }
}
