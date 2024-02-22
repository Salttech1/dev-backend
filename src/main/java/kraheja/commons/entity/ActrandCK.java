package kraheja.commons.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActrandCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String actdCoy;
	
	private String actdTranser;
	
	private Integer actdBunum;
	
	
}
