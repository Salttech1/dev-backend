package kraheja.commons.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExnarrCK implements Serializable{

	private static final long serialVersionUID = 1L;

	private Double exnBunum;

    @Type(type = "kraheja.commons.utils.CharType")
	private String exnCoy;
	
	private Double exnLinenum;

    @Type(type = "kraheja.commons.utils.CharType")
	private String exnNarrtype;
    
    @Type(type = "kraheja.commons.utils.CharType")
	private String exnTranser;
}
