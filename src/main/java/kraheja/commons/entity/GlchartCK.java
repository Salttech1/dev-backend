package kraheja.commons.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class GlchartCK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String chartAcnum;
	
	private LocalDateTime chartClosedate;
	
}
