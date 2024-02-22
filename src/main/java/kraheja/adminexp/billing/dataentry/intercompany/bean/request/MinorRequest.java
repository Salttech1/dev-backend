package kraheja.adminexp.billing.dataentry.intercompany.bean.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MinorRequest {
String acMajor;
String acMinor;
String partyCode;
String partyType;
String minType;
}
