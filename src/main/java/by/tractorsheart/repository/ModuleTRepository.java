package by.tractorsheart.repository;
import by.tractorsheart.domain.ModuleT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ModuleT entity.
 */
@Repository
public interface ModuleTRepository extends JpaRepository<ModuleT, Long>, JpaSpecificationExecutor<ModuleT> {

    @Query(value = "select distinct moduleT from ModuleT moduleT left join fetch moduleT.nodeTS",
        countQuery = "select count(distinct moduleT) from ModuleT moduleT")
    Page<ModuleT> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct moduleT from ModuleT moduleT left join fetch moduleT.nodeTS")
    List<ModuleT> findAllWithEagerRelationships();

    @Query("select moduleT from ModuleT moduleT left join fetch moduleT.nodeTS where moduleT.id =:id")
    Optional<ModuleT> findOneWithEagerRelationships(@Param("id") Long id);

}
