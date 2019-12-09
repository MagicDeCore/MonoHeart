package by.tractorsheart.service.mapper;

import by.tractorsheart.domain.*;
import by.tractorsheart.service.dto.NodeTDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link NodeT} and its DTO {@link NodeTDTO}.
 */
@Mapper(componentModel = "spring", uses = {DetailTMapper.class})
public interface NodeTMapper extends EntityMapper<NodeTDTO, NodeT> {


    @Mapping(target = "removeDetailT", ignore = true)
    @Mapping(target = "moduleTS", ignore = true)
    @Mapping(target = "removeModuleT", ignore = true)
    NodeT toEntity(NodeTDTO nodeTDTO);

    default NodeT fromId(Long id) {
        if (id == null) {
            return null;
        }
        NodeT nodeT = new NodeT();
        nodeT.setId(id);
        return nodeT;
    }
}
