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

import by.tractorsheart.domain.TypeT;
import by.tractorsheart.domain.*; // for static metamodels
import by.tractorsheart.repository.TypeTRepository;
import by.tractorsheart.service.dto.TypeTCriteria;
import by.tractorsheart.service.dto.TypeTDTO;
import by.tractorsheart.service.mapper.TypeTMapper;

/**
 * Service for executing complex queries for {@link TypeT} entities in the database.
 * The main input is a {@link TypeTCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypeTDTO} or a {@link Page} of {@link TypeTDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypeTQueryService extends QueryService<TypeT> {

    private final Logger log = LoggerFactory.getLogger(TypeTQueryService.class);

    private final TypeTRepository typeTRepository;

    private final TypeTMapper typeTMapper;

    public TypeTQueryService(TypeTRepository typeTRepository, TypeTMapper typeTMapper) {
        this.typeTRepository = typeTRepository;
        this.typeTMapper = typeTMapper;
    }

    /**
     * Return a {@link List} of {@link TypeTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypeTDTO> findByCriteria(TypeTCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TypeT> specification = createSpecification(criteria);
        return typeTMapper.toDto(typeTRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TypeTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeTDTO> findByCriteria(TypeTCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TypeT> specification = createSpecification(criteria);
        return typeTRepository.findAll(specification, page)
            .map(typeTMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypeTCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TypeT> specification = createSpecification(criteria);
        return typeTRepository.count(specification);
    }

    /**
     * Function to convert {@link TypeTCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TypeT> createSpecification(TypeTCriteria criteria) {
        Specification<TypeT> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TypeT_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TypeT_.name));
            }
            if (criteria.getModelTId() != null) {
                specification = specification.and(buildSpecification(criteria.getModelTId(),
                    root -> root.join(TypeT_.modelTS, JoinType.LEFT).get(ModelT_.id)));
            }
            if (criteria.getMarkTId() != null) {
                specification = specification.and(buildSpecification(criteria.getMarkTId(),
                    root -> root.join(TypeT_.markTS, JoinType.LEFT).get(MarkT_.id)));
            }
        }
        return specification;
    }
}
