package kraheja.commons.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TransactionlogCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	private Double tlogSrno ; 
}