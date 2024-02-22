package kraheja.adminexp.billing.dataentry.invoiceCreation.bean.request;

import java.util.List;

import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Invoicedetail;
import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Invoiceheader;
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
public class CombinedEntity2 {
	Invoiceheader invoiceheader;
	List<Invoicedetail> invoiceDetail;
}
