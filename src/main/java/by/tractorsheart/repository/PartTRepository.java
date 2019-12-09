package by.tractorsheart.repository;
import by.tractorsheart.domain.PartT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PartT entity.
 */
@Repository
public interface PartTRepository extends JpaRepository<PartT, Long>, JpaSpecificationExecutor<PartT> {

    @Query(value = "select distinct partT from PartT partT left join fetch partT.moduleTS",
        countQuery = "select count(distinct partT) from PartT partT")
    Page<PartT> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct partT from PartT partT left join fetch partT.moduleTS")
    List<PartT> findAllWithEagerRelationships();

    @Query("select partT from PartT partT left join fetch partT.moduleTS where partT.id =:id")
    Optional<PartT> findOneWithEagerRelationships(@Param("id") Long id);

}
