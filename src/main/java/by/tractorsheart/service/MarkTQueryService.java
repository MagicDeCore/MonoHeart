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

import by.tractorsheart.domain.MarkT;
import by.tractorsheart.domain.*; // for static metamodels
import by.tractorsheart.repository.MarkTRepository;
import by.tractorsheart.service.dto.MarkTCriteria;
import by.tractorsheart.service.dto.MarkTDTO;
import by.tractorsheart.service.mapper.MarkTMapper;

/**
 * Service for executing complex queries for {@link MarkT} entities in the database.
 * The main input is a {@link MarkTCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MarkTDTO} or a {@link Page} of {@link MarkTDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MarkTQueryService extends QueryService<MarkT> {

    private final Logger log = LoggerFactory.getLogger(MarkTQueryService.class);

    private final MarkTRepository markTRepository;

    private final MarkTMapper markTMapper;

    public MarkTQueryService(MarkTRepository markTRepository, MarkTMapper markTMapper) {
        this.markTRepository = markTRepository;
        this.markTMapper = markTMapper;
    }

    /**
     * Return a {@link List} of {@link MarkTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MarkTDTO> findByCriteria(MarkTCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MarkT> specification = createSpecification(criteria);
        return markTMapper.toDto(markTRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MarkTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MarkTDTO> findByCriteria(MarkTCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MarkT> specification = createSpecification(criteria);
        return markTRepository.findAll(specification, page)
            .map(markTMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MarkTCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MarkT> specification = createSpecification(criteria);
        return markTRepository.count(specification);
    }

    /**
     * Function to convert {@link MarkTCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MarkT> createSpecification(MarkTCriteria criteria) {
        Specification<MarkT> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MarkT_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MarkT_.name));
            }
            if (criteria.getTypeTId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeTId(),
                    root -> root.join(MarkT_.typeTS, JoinType.LEFT).get(TypeT_.id)));
            }
        }
        return specification;
    }
}
