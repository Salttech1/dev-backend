package kraheja.enggsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.entity.Lcauthboe;
import kraheja.enggsys.entity.LcauthboeCK;

@Repository
public interface LcauthboeRepository extends JpaRepository<Lcauthboe, LcauthboeCK> {
	List<Lcauthboe> findLcauthboeByLcauthboeCKLcabAuthnum(String lcabAuthnum);
	void deleteLcauthboeByLcauthboeCKLcabAuthnum(String lcabAuthnum);
}
