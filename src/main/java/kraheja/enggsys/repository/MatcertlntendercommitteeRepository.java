package kraheja.enggsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.entity.Matcertlntendercommittee;
import kraheja.enggsys.entity.MatcertlntendercommitteeCK;

@Repository
public interface MatcertlntendercommitteeRepository	extends JpaRepository<Matcertlntendercommittee, MatcertlntendercommitteeCK> {

	List<Matcertlntendercommittee> findByMatcertlntendercommitteeCKMclcEntrynumOrderByMclcSrno(String entryNum);
}
