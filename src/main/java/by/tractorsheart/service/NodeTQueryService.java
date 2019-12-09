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

import by.tractorsheart.domain.NodeT;
import by.tractorsheart.domain.*; // for static metamodels
import by.tractorsheart.repository.NodeTRepository;
import by.tractorsheart.service.dto.NodeTCriteria;
import by.tractorsheart.service.dto.NodeTDTO;
import by.tractorsheart.service.mapper.NodeTMapper;

/**
 * Service for executing complex queries for {@link NodeT} entities in the database.
 * The main input is a {@link NodeTCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NodeTDTO} or a {@link Page} of {@link NodeTDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NodeTQueryService extends QueryService<NodeT> {

    private final Logger log = LoggerFactory.getLogger(NodeTQueryService.class);

    private final NodeTRepository nodeTRepository;

    private final NodeTMapper nodeTMapper;

    public NodeTQueryService(NodeTRepository nodeTRepository, NodeTMapper nodeTMapper) {
        this.nodeTRepository = nodeTRepository;
        this.nodeTMapper = nodeTMapper;
    }

    /**
     * Return a {@link List} of {@link NodeTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NodeTDTO> findByCriteria(NodeTCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NodeT> specification = createSpecification(criteria);
        return nodeTMapper.toDto(nodeTRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NodeTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NodeTDTO> findByCriteria(NodeTCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NodeT> specification = createSpecification(criteria);
        return nodeTRepository.findAll(specification, page)
            .map(nodeTMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NodeTCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NodeT> specification = createSpecification(criteria);
        return nodeTRepository.count(specification);
    }

    /**
     * Function to convert {@link NodeTCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NodeT> createSpecification(NodeTCriteria criteria) {
        Specification<NodeT> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NodeT_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), NodeT_.name));
            }
            if (criteria.getDetailTId() != null) {
                specification = specification.and(buildSpecification(criteria.getDetailTId(),
                    root -> root.join(NodeT_.detailTS, JoinType.LEFT).get(DetailT_.id)));
            }
            if (criteria.getModuleTId() != null) {
                specification = specification.and(buildSpecification(criteria.getModuleTId(),
                    root -> root.join(NodeT_.moduleTS, JoinType.LEFT).get(ModuleT_.id)));
            }
        }
        return specification;
    }
}
