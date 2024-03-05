package kraheja.enggsys.management.dataentry.logicnote.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.enggsys.management.dataentry.logicnote.payload.request.NarrationAreaRequest;
import kraheja.enggsys.management.dataentry.logicnote.payload.request.WorkCodeDetailRequest;
import kraheja.payload.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude(Include.NON_DEFAULT)
public class LogicNoteResponse extends GenericResponse{

	private String tranType;
	private NarrationAreaRequest logicNoteHeaderRequest;
	private WorkCodeDetailRequest detailRequest;
}