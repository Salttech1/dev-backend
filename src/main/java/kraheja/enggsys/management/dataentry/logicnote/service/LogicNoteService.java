package kraheja.enggsys.management.dataentry.logicnote.service;
import kraheja.enggsys.management.dataentry.logicnote.payload.response.LogicNoteResponse;
import kraheja.payload.GenericResponse;

public interface LogicNoteService {
	public LogicNoteResponse retriveLogicNote(String projectCode, String logicNoteNum, String tenderCode);

	public GenericResponse createLogicNote(String projectCode,String logicNote, String tenderCode,LogicNoteResponse logicNoteRequest);
}