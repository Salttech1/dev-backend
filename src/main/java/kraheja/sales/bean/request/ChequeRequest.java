package kraheja.sales.bean.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NotEmpty
@AllArgsConstructor
@ToString
public class ChequeRequest {
	private String bank;
	private String outstat;
	private String fundSource;
	private String acType;
	private String chequeNumber;
	private String chequeAmount;
	private LocalDateTime chequeDate;
	private String cheqDate;
}