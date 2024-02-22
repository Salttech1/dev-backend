package kraheja.commons.entity;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class HsnsacmasterCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	private String hsmsCode ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String hsmsType ; 

	@Column
	private LocalDate hsmsOpendate ; 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}