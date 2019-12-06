package by.tractorsheart.repository;
import by.tractorsheart.domain.TypeT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TypeT entity.
 */
@Repository
public interface TypeTRepository extends JpaRepository<TypeT, Long>, JpaSpecificationExecutor<TypeT> {

    @Query(value = "select distinct typeT from TypeT typeT left join fetch typeT.modelTS",
        countQuery = "select count(distinct typeT) from TypeT typeT")
    Page<TypeT> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct typeT from TypeT typeT left join fetch typeT.modelTS")
    List<TypeT> findAllWithEagerRelationships();

    @Query("select typeT from TypeT typeT left join fetch typeT.modelTS where typeT.id =:id")
    Optional<TypeT> findOneWithEagerRelationships(@Param("id") Long id);

}
