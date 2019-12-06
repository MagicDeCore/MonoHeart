package by.tractorsheart.repository;
import by.tractorsheart.domain.MarkT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the MarkT entity.
 */
@Repository
public interface MarkTRepository extends JpaRepository<MarkT, Long>, JpaSpecificationExecutor<MarkT> {

    @Query(value = "select distinct markT from MarkT markT left join fetch markT.typeTS",
        countQuery = "select count(distinct markT) from MarkT markT")
    Page<MarkT> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct markT from MarkT markT left join fetch markT.typeTS")
    List<MarkT> findAllWithEagerRelationships();

    @Query("select markT from MarkT markT left join fetch markT.typeTS where markT.id =:id")
    Optional<MarkT> findOneWithEagerRelationships(@Param("id") Long id);

}
