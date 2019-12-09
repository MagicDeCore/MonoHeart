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

import by.tractorsheart.domain.ModelT;
import by.tractorsheart.domain.*; // for static metamodels
import by.tractorsheart.repository.ModelTRepository;
import by.tractorsheart.service.dto.ModelTCriteria;
import by.tractorsheart.service.dto.ModelTDTO;
import by.tractorsheart.service.mapper.ModelTMapper;

/**
 * Service for executing complex queries for {@link ModelT} entities in the database.
 * The main input is a {@link ModelTCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ModelTDTO} or a {@link Page} of {@link ModelTDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ModelTQueryService extends QueryService<ModelT> {

    private final Logger log = LoggerFactory.getLogger(ModelTQueryService.class);

    private final ModelTRepository modelTRepository;

    private final ModelTMapper modelTMapper;

    public ModelTQueryService(ModelTRepository modelTRepository, ModelTMapper modelTMapper) {
        this.modelTRepository = modelTRepository;
        this.modelTMapper = modelTMapper;
    }

    /**
     * Return a {@link List} of {@link ModelTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ModelTDTO> findByCriteria(ModelTCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ModelT> specification = createSpecification(criteria);
        return modelTMapper.toDto(modelTRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ModelTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ModelTDTO> findByCriteria(ModelTCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ModelT> specification = createSpecification(criteria);
        return modelTRepository.findAll(specification, page)
            .map(modelTMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ModelTCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ModelT> specification = createSpecification(criteria);
        return modelTRepository.count(specification);
    }

    /**
     * Function to convert {@link ModelTCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ModelT> createSpecification(ModelTCriteria criteria) {
        Specification<ModelT> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ModelT_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ModelT_.name));
            }
            if (criteria.getWrong() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWrong(), ModelT_.wrong));
            }
            if (criteria.getPartTId() != null) {
                specification = specification.and(buildSpecification(criteria.getPartTId(),
                    root -> root.join(ModelT_.partTS, JoinType.LEFT).get(PartT_.id)));
            }
            if (criteria.getTypeTId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeTId(),
                    root -> root.join(ModelT_.typeTS, JoinType.LEFT).get(TypeT_.id)));
            }
        }
        return specification;
    }
}
