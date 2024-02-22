package kraheja.sales.bean.request;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
public class BookingWrapperBean {
	private BookingRequestBean bookingRequestBean;
	private BookingAltBldgRequestBean bookingAltBldgRequestBean;
}
