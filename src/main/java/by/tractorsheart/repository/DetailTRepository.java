package by.tractorsheart.repository;
import by.tractorsheart.domain.DetailT;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DetailT entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailTRepository extends JpaRepository<DetailT, Long>, JpaSpecificationExecutor<DetailT> {

}
