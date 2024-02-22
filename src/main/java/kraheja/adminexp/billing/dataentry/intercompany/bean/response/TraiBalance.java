package kraheja.adminexp.billing.dataentry.intercompany.bean.response;

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
public class TraiBalance {
	private String acmajor;
	private String acname;
	private String acminor;
	private String acminorname;
	private String mintype;
	private double amount;
}
