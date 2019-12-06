package by.tractorsheart.service.mapper;

import by.tractorsheart.domain.*;
import by.tractorsheart.service.dto.MarkTDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MarkT} and its DTO {@link MarkTDTO}.
 */
@Mapper(componentModel = "spring", uses = {TypeTMapper.class})
public interface MarkTMapper extends EntityMapper<MarkTDTO, MarkT> {


    @Mapping(target = "removeTypeT", ignore = true)

    default MarkT fromId(Long id) {
        if (id == null) {
            return null;
        }
        MarkT markT = new MarkT();
        markT.setId(id);
        return markT;
    }
}
