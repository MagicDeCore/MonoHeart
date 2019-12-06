package by.tractorsheart.service.impl;

import by.tractorsheart.service.TypeTService;
import by.tractorsheart.domain.TypeT;
import by.tractorsheart.repository.TypeTRepository;
import by.tractorsheart.service.dto.TypeTDTO;
import by.tractorsheart.service.mapper.TypeTMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeT}.
 */
@Service
@Transactional
public class TypeTServiceImpl implements TypeTService {

    private final Logger log = LoggerFactory.getLogger(TypeTServiceImpl.class);

    private final TypeTRepository typeTRepository;

    private final TypeTMapper typeTMapper;

    public TypeTServiceImpl(TypeTRepository typeTRepository, TypeTMapper typeTMapper) {
        this.typeTRepository = typeTRepository;
        this.typeTMapper = typeTMapper;
    }

    /**
     * Save a typeT.
     *
     * @param typeTDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeTDTO save(TypeTDTO typeTDTO) {
        log.debug("Request to save TypeT : {}", typeTDTO);
        TypeT typeT = typeTMapper.toEntity(typeTDTO);
        typeT = typeTRepository.save(typeT);
        return typeTMapper.toDto(typeT);
    }

    /**
     * Get all the typeTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeTDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeTS");
        return typeTRepository.findAll(pageable)
            .map(typeTMapper::toDto);
    }

    /**
     * Get all the typeTS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TypeTDTO> findAllWithEagerRelationships(Pageable pageable) {
        return typeTRepository.findAllWithEagerRelationships(pageable).map(typeTMapper::toDto);
    }
    

    /**
     * Get one typeT by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeTDTO> findOne(Long id) {
        log.debug("Request to get TypeT : {}", id);
        return typeTRepository.findOneWithEagerRelationships(id)
            .map(typeTMapper::toDto);
    }

    /**
     * Delete the typeT by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeT : {}", id);
        typeTRepository.deleteById(id);
    }
}
