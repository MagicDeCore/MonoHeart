package by.tractorsheart.service.mapper;

import by.tractorsheart.domain.*;
import by.tractorsheart.service.dto.DetailTDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetailT} and its DTO {@link DetailTDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DetailTMapper extends EntityMapper<DetailTDTO, DetailT> {


    @Mapping(target = "nodeTS", ignore = true)
    @Mapping(target = "removeNodeT", ignore = true)
    DetailT toEntity(DetailTDTO detailTDTO);

    default DetailT fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetailT detailT = new DetailT();
        detailT.setId(id);
        return detailT;
    }
}
