package kraheja.enggsys.entity;

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

public class MatcertlnworkcodedtlsCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String mclwLogicnotenum ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String mclwGroupcode ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String mclwSubgroupcode ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String mclwMatcerttype ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String mclwMatcertcode ; 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}