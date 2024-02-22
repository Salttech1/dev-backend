package kraheja.adminexp.overheads.dataentry.service.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kraheja.commons.bean.ActranhBean;
import kraheja.commons.enums.PartyType;
import kraheja.commons.enums.TranTypeEnum;
import kraheja.commons.utils.CommonConstraints;

public class OverheadtxnActranh {

	public static List<ActranhBean> billPaymentActranh(String StrPriTranserNo,String StrPriPartycode,
			String StrPriPropritor,String StrPriCoy,String siteFromDBEntity,String StrPriUserID, 
			String StrPriTodate,String StrPriBillTranserNo,String StrBillEntDate,String BillNo,Double billAmount,
			String StrPriNarration,Double DecPayAmount
			)
	{
		
		List<ActranhBean> actranhBeanList=new ArrayList<>();
		//.trandate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
		actranhBeanList.add(ActranhBean.builder()
				.transer(StrPriTranserNo)
				.trandate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
				.trantype(TranTypeEnum.PF.toString())
				.ledgcode("GL")
				.partytype(PartyType.Z.toString())
				.narrative("")
				.partycode(StrPriPartycode)
				//.tranamt(billAmount)
				.tranamt(DecPayAmount)
				.proprietor(StrPriPropritor)
				.coy(StrPriCoy)
				.postedyn("N")
				.site(siteFromDBEntity)
				.userid(StrPriUserID)
				.today(LocalDateTime.now())
				.clearacyn("Y")
				.provyn("N")
				.reverseyn("N")
				.closingjvyn("N")
				.balancedyn("Y")
				.build()
	    );
		//******** for bill actranh 
		if(StrPriBillTranserNo!="")
		{
			actranhBeanList.add(ActranhBean.builder()
					.transer(StrPriBillTranserNo)
					.trandate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
					.trantype(TranTypeEnum.BO.toString())
					.ledgcode("GL")
					.partytype(PartyType.Z.toString())		
					.partycode(StrPriPartycode)
					.tranamt(billAmount)
					.proprietor(StrPriPropritor)
					.coy(StrPriCoy)
					.postedyn("Y")
					.site(siteFromDBEntity)
					.userid(StrPriUserID)
					.today(LocalDateTime.now())
					.clearacyn("Y")
					.provyn("N")
					.reverseyn("N")
					.closingjvyn("N")
					.balancedyn("Y")
					//.trandate(StrPriTodate)
					.voudate(StrBillEntDate)
					.vounum(BillNo)
					.build()
		    );	
		}
		
		
		return actranhBeanList;
	}
}
