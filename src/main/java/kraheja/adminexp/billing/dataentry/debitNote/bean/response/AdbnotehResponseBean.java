package kraheja.adminexp.billing.dataentry.debitNote.bean.response;

import java.util.Date;

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
public class AdbnotehResponseBean {

	 String ADBNH_PARTYTYPE;
	 String partydesc;
	 String ADBNH_PARTYCODE;
	 String par_partyname;
	 String ADBNH_BLDGCODE;
	 String bldg_name;
	 String ADBNH_COY;
	 String coy_name;
	 String ADBNH_INVBILLNO;
	 Date ADBNH_DATE;
	 Date ADBNH_INVBILLDT;
	 String ADBNH_BILLTYPE;
	 Double ADBNH_AMOUNT;
	 Double ADBNH_TDSPERC;
	 Double ADBNH_TDSAMOUNT;
	 String ADBNH_NARRATION;
	 String ADBNH_DESCRIPTION1;
	 String ADBNH_DBNOTESER;
	 String ADBNH_PROP;
	 String ADBNH_PROJECT;
}
