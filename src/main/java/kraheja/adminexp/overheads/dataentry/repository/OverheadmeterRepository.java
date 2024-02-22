package kraheja.adminexp.overheads.dataentry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kraheja.adminexp.overheads.dataentry.entity.Overheadmeter;
import kraheja.adminexp.overheads.dataentry.entity.OverheadmeterCK;

public interface OverheadmeterRepository extends JpaRepository<Overheadmeter,OverheadmeterCK> {
	List<Overheadmeter> findByOverheadmeterCK_Ohmeconnocode(String ohmeconnocode);
}
