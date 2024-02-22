package kraheja.commons.bean.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class DynaPopResponseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Object[]> dataSet;
	
	private BigDecimal bringBackColumn;

	private String colhead1;
	
	private String colhead2;

	private String colhead3;
	
	private String colhead4;

	private String colhead5;
	
	private String mainheader;
}