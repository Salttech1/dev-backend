package kraheja.arch.projbldg.dataentry.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.arch.projbldg.dataentry.bean.request.ProjectRequestBean;
import kraheja.arch.projbldg.dataentry.service.ProjectService;



@RestController
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

//	Commented by shahaji (05/01/2023)
//	@GetMapping("/fetch-project-by-Proprietor-and-Company-and-Code")
//	public ResponseEntity<?> fetchProjectByProprietorAndCompanyAndCode(@RequestParam(value = "proprietor") String  proprietor, @RequestParam(value = "company") String  company, @RequestParam(value = "code") String  code) throws ParseException {
//		return this.projectService.fetchProjectByProprietorAndCompanyAndCode(proprietor, company, code) ; 
//	}

//Added below code to retrive project details by ProjectCode ......Shahaji(26/12/2022) 
	@GetMapping("/fetch-project-by-Code")
	public ResponseEntity<?> fetchProjectByCode(@RequestParam(value = "code") String  code) throws ParseException {
		return this.projectService.fetchProjectByCode(code) ; 
	}

	
	@PostMapping("/add-project")
	public ResponseEntity<?> addProject(@Valid @RequestBody ProjectRequestBean projectRequestBean) throws ParseException {
		return this.projectService.addProject(projectRequestBean);
	}

	@PutMapping("/update-project")
	public ResponseEntity<?> updateProject(@RequestBody ProjectRequestBean projectRequestBean) throws ParseException {
//		System.out.println("In Update project");
		return this.projectService.updateProject(projectRequestBean);
	}

	//commented by shahaji (05/01/2023)
//	@GetMapping("/check-Proprietor-and-Company-and-Code-exists")
//	public ResponseEntity<?> checkProprietorAndCompanyAndCodeExists(@RequestParam(value = "proprietor") String  proprietor, @RequestParam(value = "company") String  company, @RequestParam(value = "code") String  code) throws ParseException {
//		return this.projectService.checkProprietorAndCompanyAndCodeExists(proprietor, company, code);
//	}

}