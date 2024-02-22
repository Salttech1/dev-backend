package kraheja.enggsys.bean.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class CertworknarrdtlResponseBean {

	private Integer amount ;
	private String certnum ;
	private String itemdesc ;
	private Double quantity ;
	private String site ;
	private Integer srno ;
	private LocalDateTime today ;
	private String userid ;
	private String workcode ;
}