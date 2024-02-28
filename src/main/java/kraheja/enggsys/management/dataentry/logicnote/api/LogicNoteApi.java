package kraheja.enggsys.management.dataentry.logicnote.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.enggsys.management.dataentry.logicnote.payload.response.LogicNoteResponse;
import kraheja.enggsys.management.dataentry.logicnote.service.LogicNoteService;

@RestController
@RequestMapping("/management")
public class LogicNoteApi {

	private final LogicNoteService logicNoteService;
	
	public LogicNoteApi(LogicNoteService logicNoteService) {
		this.logicNoteService = logicNoteService;
	}

	@GetMapping("/retrieve-logic-note")
	public ResponseEntity<LogicNoteResponse> retrieveLogicNote(@RequestParam String projectCode,
			@RequestParam String logicNote, @RequestParam String tenderCode) {
		
		LogicNoteResponse response = logicNoteService.retriveLogicNote(projectCode, logicNote, tenderCode);
		 
		return ResponseEntity.ok(response);
	}
}