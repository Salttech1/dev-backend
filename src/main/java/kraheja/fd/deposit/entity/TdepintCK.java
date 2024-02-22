package kraheja.fd.deposit.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TdepintCK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column
	private String tdinCoy;
	
	@Column
	private String tdinDepositor;
	
	@Column
	private String tdinReceiptnum;

	@Column
	private LocalDate tdinIntupto;
	
}
