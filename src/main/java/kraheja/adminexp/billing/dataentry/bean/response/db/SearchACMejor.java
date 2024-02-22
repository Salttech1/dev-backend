package kraheja.adminexp.billing.dataentry.bean.response.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchACMejor {
	private int srNo;
	private String acmajor;
	private String majorName;
	private String acminor;
	private String minorName;
	private String acMinType;
	private String partyType;
	private Double acAmount;
}
