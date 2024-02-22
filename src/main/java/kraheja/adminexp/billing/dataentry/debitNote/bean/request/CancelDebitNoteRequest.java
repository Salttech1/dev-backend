package kraheja.adminexp.billing.dataentry.debitNote.bean.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CancelDebitNoteRequest {

	String serNo;
	String partyType;
	String partyCode;
	String suppBillNo;
}
