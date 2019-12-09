package by.tractorsheart.service.impl;

import by.tractorsheart.service.ModuleTService;
import by.tractorsheart.domain.ModuleT;
import by.tractorsheart.repository.ModuleTRepository;
import by.tractorsheart.service.dto.ModuleTDTO;
import by.tractorsheart.service.mapper.ModuleTMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ModuleT}.
 */
@Service
@Transactional
public class ModuleTServiceImpl implements ModuleTService {

    private final Logger log = LoggerFactory.getLogger(ModuleTServiceImpl.class);

    private final ModuleTRepository moduleTRepository;

    private final ModuleTMapper moduleTMapper;

    public ModuleTServiceImpl(ModuleTRepository moduleTRepository, ModuleTMapper moduleTMapper) {
        this.moduleTRepository = moduleTRepository;
        this.moduleTMapper = moduleTMapper;
    }

    /**
     * Save a moduleT.
     *
     * @param moduleTDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ModuleTDTO save(ModuleTDTO moduleTDTO) {
        log.debug("Request to save ModuleT : {}", moduleTDTO);
        ModuleT moduleT = moduleTMapper.toEntity(moduleTDTO);
        moduleT = moduleTRepository.save(moduleT);
        return moduleTMapper.toDto(moduleT);
    }

    /**
     * Get all the moduleTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ModuleTDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ModuleTS");
        return moduleTRepository.findAll(pageable)
            .map(moduleTMapper::toDto);
    }

    /**
     * Get all the moduleTS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ModuleTDTO> findAllWithEagerRelationships(Pageable pageable) {
        return moduleTRepository.findAllWithEagerRelationships(pageable).map(moduleTMapper::toDto);
    }
    

    /**
     * Get one moduleT by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ModuleTDTO> findOne(Long id) {
        log.debug("Request to get ModuleT : {}", id);
        return moduleTRepository.findOneWithEagerRelationships(id)
            .map(moduleTMapper::toDto);
    }

    /**
     * Delete the moduleT by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModuleT : {}", id);
        moduleTRepository.deleteById(id);
    }
}
