package kraheja.enggsys.management.dataentry.logicnote.service;
import kraheja.enggsys.management.dataentry.logicnote.payload.response.LogicNoteResponse;

public interface LogicNoteService {
	public LogicNoteResponse retriveLogicNote(String projectCode, String logicNoteNum, String tenderCode);
}