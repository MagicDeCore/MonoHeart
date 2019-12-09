package by.tractorsheart.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import by.tractorsheart.domain.ModuleT;
import by.tractorsheart.domain.*; // for static metamodels
import by.tractorsheart.repository.ModuleTRepository;
import by.tractorsheart.service.dto.ModuleTCriteria;
import by.tractorsheart.service.dto.ModuleTDTO;
import by.tractorsheart.service.mapper.ModuleTMapper;

/**
 * Service for executing complex queries for {@link ModuleT} entities in the database.
 * The main input is a {@link ModuleTCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ModuleTDTO} or a {@link Page} of {@link ModuleTDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ModuleTQueryService extends QueryService<ModuleT> {

    private final Logger log = LoggerFactory.getLogger(ModuleTQueryService.class);

    private final ModuleTRepository moduleTRepository;

    private final ModuleTMapper moduleTMapper;

    public ModuleTQueryService(ModuleTRepository moduleTRepository, ModuleTMapper moduleTMapper) {
        this.moduleTRepository = moduleTRepository;
        this.moduleTMapper = moduleTMapper;
    }

    /**
     * Return a {@link List} of {@link ModuleTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ModuleTDTO> findByCriteria(ModuleTCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ModuleT> specification = createSpecification(criteria);
        return moduleTMapper.toDto(moduleTRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ModuleTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ModuleTDTO> findByCriteria(ModuleTCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ModuleT> specification = createSpecification(criteria);
        return moduleTRepository.findAll(specification, page)
            .map(moduleTMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ModuleTCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ModuleT> specification = createSpecification(criteria);
        return moduleTRepository.count(specification);
    }

    /**
     * Function to convert {@link ModuleTCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ModuleT> createSpecification(ModuleTCriteria criteria) {
        Specification<ModuleT> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ModuleT_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ModuleT_.name));
            }
            if (criteria.getNodeTId() != null) {
                specification = specification.and(buildSpecification(criteria.getNodeTId(),
                    root -> root.join(ModuleT_.nodeTS, JoinType.LEFT).get(NodeT_.id)));
            }
            if (criteria.getPartTId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartTId(),
                    root -> root.join(ModuleT_.partTS, JoinType.LEFT).get(PartT_.id)));
            }
        }
        return specification;
    }
}
