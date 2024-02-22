package kraheja.sales.bean.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InchequeDetailResponse {
	private String chequeNumber;
	private String chequeAmount;
	private String message;
}