package kraheja.fd.deposit.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Form15hgCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String formCoy ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String formAcyear ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String formDepositor ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String formQuarter ; 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}