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

public class MinorsCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
    @Type(type = "kraheja.commons.utils.CharType")
	private String minMinorscode ; 

	@Column
    @Type(type = "kraheja.commons.utils.CharType")
	private String minMinorstype ; 

	@Column
	private LocalDate minClosedate ; 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}