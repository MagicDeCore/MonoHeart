package by.tractorsheart.service.impl;

import by.tractorsheart.service.PartTService;
import by.tractorsheart.domain.PartT;
import by.tractorsheart.repository.PartTRepository;
import by.tractorsheart.service.dto.PartTDTO;
import by.tractorsheart.service.mapper.PartTMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PartT}.
 */
@Service
@Transactional
public class PartTServiceImpl implements PartTService {

    private final Logger log = LoggerFactory.getLogger(PartTServiceImpl.class);

    private final PartTRepository partTRepository;

    private final PartTMapper partTMapper;

    public PartTServiceImpl(PartTRepository partTRepository, PartTMapper partTMapper) {
        this.partTRepository = partTRepository;
        this.partTMapper = partTMapper;
    }

    /**
     * Save a partT.
     *
     * @param partTDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PartTDTO save(PartTDTO partTDTO) {
        log.debug("Request to save PartT : {}", partTDTO);
        PartT partT = partTMapper.toEntity(partTDTO);
        partT = partTRepository.save(partT);
        return partTMapper.toDto(partT);
    }

    /**
     * Get all the partTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PartTDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PartTS");
        return partTRepository.findAll(pageable)
            .map(partTMapper::toDto);
    }

    /**
     * Get all the partTS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PartTDTO> findAllWithEagerRelationships(Pageable pageable) {
        return partTRepository.findAllWithEagerRelationships(pageable).map(partTMapper::toDto);
    }
    

    /**
     * Get one partT by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PartTDTO> findOne(Long id) {
        log.debug("Request to get PartT : {}", id);
        return partTRepository.findOneWithEagerRelationships(id)
            .map(partTMapper::toDto);
    }

    /**
     * Delete the partT by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PartT : {}", id);
        partTRepository.deleteById(id);
    }
}
