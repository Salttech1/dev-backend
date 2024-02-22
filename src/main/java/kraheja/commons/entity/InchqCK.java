package kraheja.commons.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InchqCK implements Serializable{
	
	private String inchqNum;

	private String inchqCoy;
	
	private String inchqTranser;
	
	private String inchqRecnum;
	
	private String inchqBank;
}
