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
public class DcCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String dcpEntryno ;
	
	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String dcpSuppcode;
	
	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String dcpDcno;
}