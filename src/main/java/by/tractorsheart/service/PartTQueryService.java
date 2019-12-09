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

import by.tractorsheart.domain.PartT;
import by.tractorsheart.domain.*; // for static metamodels
import by.tractorsheart.repository.PartTRepository;
import by.tractorsheart.service.dto.PartTCriteria;
import by.tractorsheart.service.dto.PartTDTO;
import by.tractorsheart.service.mapper.PartTMapper;

/**
 * Service for executing complex queries for {@link PartT} entities in the database.
 * The main input is a {@link PartTCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PartTDTO} or a {@link Page} of {@link PartTDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PartTQueryService extends QueryService<PartT> {

    private final Logger log = LoggerFactory.getLogger(PartTQueryService.class);

    private final PartTRepository partTRepository;

    private final PartTMapper partTMapper;

    public PartTQueryService(PartTRepository partTRepository, PartTMapper partTMapper) {
        this.partTRepository = partTRepository;
        this.partTMapper = partTMapper;
    }

    /**
     * Return a {@link List} of {@link PartTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PartTDTO> findByCriteria(PartTCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PartT> specification = createSpecification(criteria);
        return partTMapper.toDto(partTRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PartTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PartTDTO> findByCriteria(PartTCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PartT> specification = createSpecification(criteria);
        return partTRepository.findAll(specification, page)
            .map(partTMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PartTCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PartT> specification = createSpecification(criteria);
        return partTRepository.count(specification);
    }

    /**
     * Function to convert {@link PartTCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PartT> createSpecification(PartTCriteria criteria) {
        Specification<PartT> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PartT_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PartT_.name));
            }
            if (criteria.getModuleTId() != null) {
                specification = specification.and(buildSpecification(criteria.getModuleTId(),
                    root -> root.join(PartT_.moduleTS, JoinType.LEFT).get(ModuleT_.id)));
            }
            if (criteria.getModelTId() != null) {
                specification = specification.and(buildSpecification(criteria.getModelTId(),
                    root -> root.join(PartT_.modelTS, JoinType.LEFT).get(ModelT_.id)));
            }
        }
        return specification;
    }
}
