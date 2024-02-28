package kraheja.enggsys.management.dataentry.logicnote.payload.request;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NarrationAreaRequest {

	private LocalDate date;
	private String revision;
	@NotEmpty(message = "Please enter task.")
	private String shortSubTask;
	private String location;
	private String importUnder;
	@NotEmpty(message = "Please enter Logic Description.")
	private String packageName;
	private String tranType;
	private String scopeOfWork;
	private String commercialDescription1;
	private String commercialDescription2;
	private String finalConsideration;
	private String technicalDescription;
	private String recommendationWithJustification;
	private String deliverySchedule;
	private String warrantyInformation;
	private String resourceAllocation;
	private String tenderNarrative;
	private String importantSpecification;
}