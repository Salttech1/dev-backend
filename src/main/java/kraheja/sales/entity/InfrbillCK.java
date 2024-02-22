package kraheja.sales.entity;

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

public class InfrbillCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Type(type = "kraheja.commons.utils.CharType") 
	@Column(name = "infr_ownerid")
	private String infrOwnerId; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String infrMonth ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String infrBillnum ; 


}