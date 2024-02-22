package kraheja.sales.infra.bean.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InfraAuxiBillRequest {
	@NotEmpty
	private String chargeCode;
	@NotEmpty
	private String billType;
	@NotEmpty
	private String ownerIdFrom;
	@NotEmpty
	private String ownerIdTo;
	private LocalDateTime billRecDate;
	private String billDate;

}
