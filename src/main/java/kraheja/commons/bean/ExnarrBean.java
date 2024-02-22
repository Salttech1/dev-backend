package kraheja.commons.bean;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ExnarrBean {

	private Double bunum;

	private String coy;
	
	private Double linenum;

	private String narrtype;
    
	private String transer;
	
	private String exnarr;

	private Double oldbunum;

	private String site;
	
	private String userid;

	private LocalDateTime today;
}
