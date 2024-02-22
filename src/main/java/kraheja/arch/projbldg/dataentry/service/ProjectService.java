package kraheja.arch.projbldg.dataentry.service;

import org.springframework.http.ResponseEntity;
import kraheja.arch.projbldg.dataentry.bean.request.ProjectRequestBean;

import java.text.ParseException;

public interface ProjectService {

//	ResponseEntity<?> fetchProjectByProprietorAndCompanyAndCode(String  proprietor, String  company, String  code) ;  Commneted by shahaji (05/01/2023)

	ResponseEntity<?> fetchProjectByCode(String  code) ; //Added by shahaji(26/12/2022)
	
	ResponseEntity<?> addProject(ProjectRequestBean projectRequestBean)  throws ParseException;

	ResponseEntity<?> updateProject(ProjectRequestBean projectRequestBean) throws ParseException;

//	ResponseEntity<?> deleteProject(String  proprietor, String  company, String  code) throws ParseException; 

//	ResponseEntity<?> checkProprietorAndCompanyAndCodeExists(String  proprietor, String  company, String  code) ;    Commneted by shahaji (05/01/2023)
}