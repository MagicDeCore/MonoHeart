package by.tractorsheart.service.mapper;

import by.tractorsheart.domain.*;
import by.tractorsheart.service.dto.TypeTDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeT} and its DTO {@link TypeTDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModelTMapper.class})
public interface TypeTMapper extends EntityMapper<TypeTDTO, TypeT> {


    @Mapping(target = "removeModelT", ignore = true)
    @Mapping(target = "markTS", ignore = true)
    @Mapping(target = "removeMarkT", ignore = true)
    TypeT toEntity(TypeTDTO typeTDTO);

    default TypeT fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeT typeT = new TypeT();
        typeT.setId(id);
        return typeT;
    }
}
