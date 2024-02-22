package kraheja.arch.projbldg.dataentry.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import kraheja.arch.projbldg.dataentry.entity.Project ;
import kraheja.arch.projbldg.dataentry.entity.ProjectCK;
@Repository
public interface ProjectRepository extends JpaRepository<Project, ProjectCK> {
	//Added by shahaji(26/12/2022)
	@Query("select e  from Project e WHERE trim(e.projectCK.projCode) = :code")
	Project findByCode(String code) ; 
		
}