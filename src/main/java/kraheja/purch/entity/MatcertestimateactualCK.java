package kraheja.purch.entity;

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

public class MatcertestimateactualCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String clseProjcode ; 

	@Column
	private String clseMatcerttype ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String clseMatcertcode ; 

	@Column
	private String clseGroupcode ; 

	@Column
	private String clseSubgroupcode ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String clsePartytype ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String clsePartycode ; 
}