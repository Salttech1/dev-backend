package kraheja.sales.bean.entitiesresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GlchartEntityResponse {
	private String chartMinoryn;
	private String chartValidminors;
	private String chartPostprojonly;
	private String chartPostglonly;
	private Double pb1Start;
	private Double pb1Len;
	private Double pb2Start;
	private Double pb2Len;
	private Double mb1Start;
	private Double mb1Len;
}