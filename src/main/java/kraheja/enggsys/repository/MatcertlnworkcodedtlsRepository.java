package kraheja.enggsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.entity.Matcertlnworkcodedtls;
import kraheja.enggsys.entity.MatcertlnworkcodedtlsCK;

@Repository
public interface MatcertlnworkcodedtlsRepository extends JpaRepository<Matcertlnworkcodedtls, MatcertlnworkcodedtlsCK> {
	Matcertlnworkcodedtls findByMatcertlnworkcodedtlsCKMclwLogicnotenum(String logicNoteNum);
}
