package by.tractorsheart.service.mapper;

import by.tractorsheart.domain.*;
import by.tractorsheart.service.dto.ModelTDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModelT} and its DTO {@link ModelTDTO}.
 */
@Mapper(componentModel = "spring", uses = {PartTMapper.class})
public interface ModelTMapper extends EntityMapper<ModelTDTO, ModelT> {


    @Mapping(target = "removePartT", ignore = true)
    @Mapping(target = "typeTS", ignore = true)
    @Mapping(target = "removeTypeT", ignore = true)
    ModelT toEntity(ModelTDTO modelTDTO);

    default ModelT fromId(Long id) {
        if (id == null) {
            return null;
        }
        ModelT modelT = new ModelT();
        modelT.setId(id);
        return modelT;
    }
}
