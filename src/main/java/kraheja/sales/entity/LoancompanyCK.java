package kraheja.sales.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LoancompanyCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column
	private String lcoyCode;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}