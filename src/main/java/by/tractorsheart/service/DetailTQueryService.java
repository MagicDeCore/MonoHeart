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

import by.tractorsheart.domain.DetailT;
import by.tractorsheart.domain.*; // for static metamodels
import by.tractorsheart.repository.DetailTRepository;
import by.tractorsheart.service.dto.DetailTCriteria;
import by.tractorsheart.service.dto.DetailTDTO;
import by.tractorsheart.service.mapper.DetailTMapper;

/**
 * Service for executing complex queries for {@link DetailT} entities in the database.
 * The main input is a {@link DetailTCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DetailTDTO} or a {@link Page} of {@link DetailTDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DetailTQueryService extends QueryService<DetailT> {

    private final Logger log = LoggerFactory.getLogger(DetailTQueryService.class);

    private final DetailTRepository detailTRepository;

    private final DetailTMapper detailTMapper;

    public DetailTQueryService(DetailTRepository detailTRepository, DetailTMapper detailTMapper) {
        this.detailTRepository = detailTRepository;
        this.detailTMapper = detailTMapper;
    }

    /**
     * Return a {@link List} of {@link DetailTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DetailTDTO> findByCriteria(DetailTCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DetailT> specification = createSpecification(criteria);
        return detailTMapper.toDto(detailTRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DetailTDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DetailTDTO> findByCriteria(DetailTCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DetailT> specification = createSpecification(criteria);
        return detailTRepository.findAll(specification, page)
            .map(detailTMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DetailTCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DetailT> specification = createSpecification(criteria);
        return detailTRepository.count(specification);
    }

    /**
     * Function to convert {@link DetailTCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DetailT> createSpecification(DetailTCriteria criteria) {
        Specification<DetailT> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DetailT_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DetailT_.name));
            }
            if (criteria.getNodeTId() != null) {
                specification = specification.and(buildSpecification(criteria.getNodeTId(),
                    root -> root.join(DetailT_.nodeTS, JoinType.LEFT).get(NodeT_.id)));
            }
        }
        return specification;
    }
}
