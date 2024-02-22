package kraheja.adminexp.insurance.dataentry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.insurance.dataentry.entity.Insassetiteminsured ;
import kraheja.adminexp.insurance.dataentry.entity.InsassetiteminsuredCK;
import kraheja.adminexp.insurance.dataentry.entity.Inspolicy;
@Repository
public interface InsassetiteminsuredRepository extends JpaRepository<Insassetiteminsured, InsassetiteminsuredCK> {

	 
	Insassetiteminsured findByInsassetiteminsuredCK_IaiPolicyidAndInsassetiteminsuredCK_IaiLinenumber(String policyid, Double linenumber) ; 
	List<Insassetiteminsured> findByInsassetiteminsuredCK_IaiPolicyid(String policyid);
}