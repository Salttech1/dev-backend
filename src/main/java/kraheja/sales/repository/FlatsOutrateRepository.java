package kraheja.sales.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.sales.entity.Flats;
import kraheja.sales.entity.FlatsCK;

@Repository
public interface FlatsOutrateRepository extends JpaRepository<Flats, FlatsCK> {

    @Query(value = "select distinct flat_ownerid  from flats, outrate  where flat_bldgcode = outr_bldgcode   and flat_wing = outr_wing   and flat_flatnum = outr_flatnum  and flat_soldyn = 'Y'   and substr(flat_flatnum,1,1) in ('F','H','U')   and ( ? between OUTR_STARTDATE and OUTR_ENDDATE  or ? between OUTR_STARTDATE and OUTR_ENDDATE )  and trim(flat_ownerid) between ?  and ?   and trim(outr_billtype) = ? order by flat_ownerid", nativeQuery = true)
	String findDistinctFlatOwnerIds(String date, String qDate, String ownerStart, String ownerEnd, String billType);
}
