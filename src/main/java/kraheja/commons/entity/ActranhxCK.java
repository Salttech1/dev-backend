package kraheja.commons.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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

public class ActranhxCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String acthRevision ; 

	@Column
	private LocalDateTime acthToday ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String acthTranser ; 
	
	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String acthCoy;
}