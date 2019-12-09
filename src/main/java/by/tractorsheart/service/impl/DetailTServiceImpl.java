package by.tractorsheart.service.impl;

import by.tractorsheart.service.DetailTService;
import by.tractorsheart.domain.DetailT;
import by.tractorsheart.repository.DetailTRepository;
import by.tractorsheart.service.dto.DetailTDTO;
import by.tractorsheart.service.mapper.DetailTMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DetailT}.
 */
@Service
@Transactional
public class DetailTServiceImpl implements DetailTService {

    private final Logger log = LoggerFactory.getLogger(DetailTServiceImpl.class);

    private final DetailTRepository detailTRepository;

    private final DetailTMapper detailTMapper;

    public DetailTServiceImpl(DetailTRepository detailTRepository, DetailTMapper detailTMapper) {
        this.detailTRepository = detailTRepository;
        this.detailTMapper = detailTMapper;
    }

    /**
     * Save a detailT.
     *
     * @param detailTDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DetailTDTO save(DetailTDTO detailTDTO) {
        log.debug("Request to save DetailT : {}", detailTDTO);
        DetailT detailT = detailTMapper.toEntity(detailTDTO);
        detailT = detailTRepository.save(detailT);
        return detailTMapper.toDto(detailT);
    }

    /**
     * Get all the detailTS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DetailTDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetailTS");
        return detailTRepository.findAll(pageable)
            .map(detailTMapper::toDto);
    }


    /**
     * Get one detailT by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DetailTDTO> findOne(Long id) {
        log.debug("Request to get DetailT : {}", id);
        return detailTRepository.findById(id)
            .map(detailTMapper::toDto);
    }

    /**
     * Delete the detailT by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetailT : {}", id);
        detailTRepository.deleteById(id);
    }
}
