package kraheja.enggsys.entity;

import java.io.Serializable;

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

public class MatcertlnvendorhdrCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Type(type = "kraheja.commons.utils.CharType") 
	private String mcvhLogicnotenum ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String mcvhVendorsrno ; 


}