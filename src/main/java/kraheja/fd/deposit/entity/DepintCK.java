package kraheja.fd.deposit.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepintCK implements Serializable{

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String dinDepositor ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String dinReceiptnum ; 

	@Column
	private LocalDate dinIntfrom ; 

	@Column
	private LocalDate dinIntupto ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String dinCoy ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String dinChqnum ; 
}