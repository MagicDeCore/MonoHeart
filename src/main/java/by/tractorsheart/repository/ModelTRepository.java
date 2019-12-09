package by.tractorsheart.repository;
import by.tractorsheart.domain.ModelT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ModelT entity.
 */
@Repository
public interface ModelTRepository extends JpaRepository<ModelT, Long>, JpaSpecificationExecutor<ModelT> {

    @Query(value = "select distinct modelT from ModelT modelT left join fetch modelT.partTS",
        countQuery = "select count(distinct modelT) from ModelT modelT")
    Page<ModelT> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct modelT from ModelT modelT left join fetch modelT.partTS")
    List<ModelT> findAllWithEagerRelationships();

    @Query("select modelT from ModelT modelT left join fetch modelT.partTS where modelT.id =:id")
    Optional<ModelT> findOneWithEagerRelationships(@Param("id") Long id);

}
