package kraheja.adminexp.insurance.dataentry.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class EndorsementPolRequestBean {
	
	List<InspolendorsementRequestBean>  inspolendorsementRequestBeanList;
	
	 List<InspolicyRequestBean>  inspolicyRequestBeanList;

}   