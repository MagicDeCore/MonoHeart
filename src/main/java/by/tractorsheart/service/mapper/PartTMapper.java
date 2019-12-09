package by.tractorsheart.service.mapper;

import by.tractorsheart.domain.*;
import by.tractorsheart.service.dto.PartTDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartT} and its DTO {@link PartTDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModuleTMapper.class})
public interface PartTMapper extends EntityMapper<PartTDTO, PartT> {


    @Mapping(target = "removeModuleT", ignore = true)
    @Mapping(target = "modelTS", ignore = true)
    @Mapping(target = "removeModelT", ignore = true)
    PartT toEntity(PartTDTO partTDTO);

    default PartT fromId(Long id) {
        if (id == null) {
            return null;
        }
        PartT partT = new PartT();
        partT.setId(id);
        return partT;
    }
}
