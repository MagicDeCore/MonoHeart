package by.tractorsheart.service.impl;

import by.tractorsheart.service.NodeTService;
import by.tractorsheart.domain.NodeT;
import by.tractorsheart.repository.NodeTRepository;
import by.tractorsheart.service.dto.NodeTDTO;
import by.tractorsheart.service.mapper.NodeTMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NodeT}.
 */
@Service
@Transactional
public class NodeTServiceImpl implements NodeTService {

    private final Logger log = LoggerFactory.getLogger(NodeTServiceImpl.class);

    private final NodeTRepository nodeTRepository;

    private final NodeTMapper nodeTMapper;

    public NodeTServiceImpl(NodeTRepository nodeTRepository, NodeTMapper nodeTMapper) {
        this.nodeTRepository = nodeTRepository;
        this.nodeTMapper = nodeTMapper;
    }

    /**
     * Save a nodeT.
     *
     * @param nodeTDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NodeTDTO save(NodeTDTO nodeTDTO) {
        log.debug("Request to save NodeT : {}", nodeTDTO);
        NodeT nodeT = nodeTMapper.toEntity(nodeTDTO);
        nodeT = nodeTRepository.save(nodeT);
        return nodeTMapper.toDto(nodeT);
    }

    /**
     * Get all the nodeTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NodeTDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NodeTS");
        return nodeTRepository.findAll(pageable)
            .map(nodeTMapper::toDto);
    }

    /**
     * Get all the nodeTS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<NodeTDTO> findAllWithEagerRelationships(Pageable pageable) {
        return nodeTRepository.findAllWithEagerRelationships(pageable).map(nodeTMapper::toDto);
    }
    

    /**
     * Get one nodeT by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NodeTDTO> findOne(Long id) {
        log.debug("Request to get NodeT : {}", id);
        return nodeTRepository.findOneWithEagerRelationships(id)
            .map(nodeTMapper::toDto);
    }

    /**
     * Delete the nodeT by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NodeT : {}", id);
        nodeTRepository.deleteById(id);
    }
}
