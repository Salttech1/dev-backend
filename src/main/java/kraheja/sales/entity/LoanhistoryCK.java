package kraheja.sales.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import kraheja.purch.entity.Advrecvoucher;
import kraheja.purch.entity.AdvrecvoucherCK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LoanhistoryCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column
	private String lhistLoanco;

	@Column
	private String lhistLoannum;

	@Column
	private LocalDate lhistLoanclosedate;

	@Column
	private String lhistOwnerid;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}
