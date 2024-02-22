package kraheja.adminexp.billing.dataentry.debitNote.bean.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DebitNoteRequest {
	
Boolean isUpdate;
AdbnotehRequestBean adbnotehRequestBean;
List<AdbnotedRequestBean> adbnotedRequestBean;
}
