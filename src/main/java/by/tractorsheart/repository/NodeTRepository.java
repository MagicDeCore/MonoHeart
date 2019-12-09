package by.tractorsheart.repository;
import by.tractorsheart.domain.NodeT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the NodeT entity.
 */
@Repository
public interface NodeTRepository extends JpaRepository<NodeT, Long>, JpaSpecificationExecutor<NodeT> {

    @Query(value = "select distinct nodeT from NodeT nodeT left join fetch nodeT.detailTS",
        countQuery = "select count(distinct nodeT) from NodeT nodeT")
    Page<NodeT> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct nodeT from NodeT nodeT left join fetch nodeT.detailTS")
    List<NodeT> findAllWithEagerRelationships();

    @Query("select nodeT from NodeT nodeT left join fetch nodeT.detailTS where nodeT.id =:id")
    Optional<NodeT> findOneWithEagerRelationships(@Param("id") Long id);

}
