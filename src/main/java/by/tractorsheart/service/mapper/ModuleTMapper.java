package by.tractorsheart.service.mapper;

import by.tractorsheart.domain.*;
import by.tractorsheart.service.dto.ModuleTDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModuleT} and its DTO {@link ModuleTDTO}.
 */
@Mapper(componentModel = "spring", uses = {NodeTMapper.class})
public interface ModuleTMapper extends EntityMapper<ModuleTDTO, ModuleT> {


    @Mapping(target = "removeNodeT", ignore = true)
    @Mapping(target = "partTS", ignore = true)
    @Mapping(target = "removePartT", ignore = true)
    ModuleT toEntity(ModuleTDTO moduleTDTO);

    default ModuleT fromId(Long id) {
        if (id == null) {
            return null;
        }
        ModuleT moduleT = new ModuleT();
        moduleT.setId(id);
        return moduleT;
    }
}
