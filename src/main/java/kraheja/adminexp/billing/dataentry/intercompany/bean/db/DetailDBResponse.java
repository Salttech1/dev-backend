package kraheja.adminexp.billing.dataentry.intercompany.bean.db;

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
public class DetailDBResponse {
	private Integer srNo;
	private String acMajor;
	private String acMajorName;
	private String acMinor;
	private String acMinorName;
	private String minType;
	private String partyType;
	private double acAmount;
}
