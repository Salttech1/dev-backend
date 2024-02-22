package kraheja.adminexp.billing.dataentry.intercompany.bean.response;

import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MinorResponse {
	String locAccCodeAcMinor;
	String locAccParty;
	String locAccPartyType;
	String locAccMinType;
	String locAccMinCode;
	String locXAccCodeAcMinor;
	String locXAccParty;
	String locXAccPartyType;
	String locXAccMinType;
	String locxAccMinCode;
}
