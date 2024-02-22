package kraheja.adminexp.billing.dataentry.invoiceCreation.bean.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CombinedEntity {

	char isUpdate;
	InvoiceheaderRequestBean invoiceheaderRequestBean; 
	List<InvoicedetailRequestBean> invoicedetailRequestBean;

}
