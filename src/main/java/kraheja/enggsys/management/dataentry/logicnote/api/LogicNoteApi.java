package kraheja.enggsys.management.dataentry.logicnote.api;

import javax.validation.Valid;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.enggsys.management.dataentry.logicnote.payload.response.LogicNoteResponse;
import kraheja.enggsys.management.dataentry.logicnote.service.LogicNoteService;
import kraheja.payload.GenericResponse;
/**
 * <p>
 * This API is use for create LogicNote, retrieve LogicNote and update
 * LogicNote.
 * </p>
 * 
 * @author sazzad.alom
 * @since MAR-2024
 * @version 1.0.0
 * 
 */
@RestController
@RequestMapping("/management")
public class LogicNoteApi {

	private final LogicNoteService logicNoteService;
	
	public LogicNoteApi(LogicNoteService logicNoteService) {
		this.logicNoteService = logicNoteService;
	}

	@GetMapping("/retrieve-logic-note")
	public ResponseEntity<LogicNoteResponse> retrieveLogicNote(@Required @RequestParam String projectCode,
			@Required @RequestParam String logicNote, @RequestParam String tenderCode) {
		
		LogicNoteResponse response = logicNoteService.retriveLogicNote(projectCode, logicNote, tenderCode);
		 
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/create-logic-note")
	public ResponseEntity<GenericResponse> createLogicNote(@Valid @Required @RequestParam String projectCode,@RequestParam String logicNote, @Required @RequestParam String tenderCode, @RequestBody LogicNoteResponse logicNoteRequest) {
		
		GenericResponse response = logicNoteService.createLogicNote(projectCode,logicNote, tenderCode, logicNoteRequest);
		return ResponseEntity.ok(response);
	}
}