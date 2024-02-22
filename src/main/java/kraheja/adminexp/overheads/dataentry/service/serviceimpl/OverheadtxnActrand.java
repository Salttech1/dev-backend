package kraheja.adminexp.overheads.dataentry.service.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import kraheja.commons.bean.ActrandBean;
import kraheja.commons.bean.DetnarrBean;
import kraheja.commons.utils.CommonConstraints;

public class OverheadtxnActrand {

	public static  ActrandBean billPaymentActrand(
			String StrPrmTranserNo,Integer StrPrmBunNum,Integer StrPrmOldBunNum,Integer StrPrmRefBunbum,	
			Integer StrPrmContraBunNum,String StrPrmTranType,String StrPrmTranDate,String StrPrmLedgcode,String StrPrmProprietor,
			String StrPrmCoy,String StrPrmPartyType,String StrPrmPartyCode,String StrPrmAcmajor,String StrPrmAcMinor,  
			String StrPrmMinType,String StrPrmMinCode,Double StrPrmTranAmt,String StrPrmPeriod,String StrPrmRefFrom,String StrPrmRefupto,
			String StrPrmProject,String StrPrmbldgCode,String StrPrmDocnum,String StrPrmDocDate,String StrPrmPaymode,
			String StrPrmDocPartytype,String StrPrmdocParcode,String StrPrmxProject,String StrPrmxacMajor,
			String StrPrmxacMinor,String StrPrmxMinType,String StrPrmxPartytype,String StrPrmxPartycode,
			String StrPrmxTranser,String StrPrmSite,String StrPrmUserid,String Narration
			)
	{
		
		//AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(StrPrmAcmajor, StrPrmTranType);
		
		return ActrandBean.builder()
				.transer(StrPrmTranserNo)
				.bunum(StrPrmBunNum)
				.oldbunum(StrPrmOldBunNum)
				.contrabunum(StrPrmContraBunNum)
				.xrefbunum(StrPrmRefBunbum)
				.trantype(StrPrmTranType)
				.trandate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
				.ledgcode(StrPrmLedgcode)
				.proprietor(StrPrmProprietor)
				.coy(StrPrmCoy)
				.partytype(StrPrmPartyType)
				.partycode(StrPrmPartyCode)
				.acmajor(StrPrmAcmajor)
				.acminor(StrPrmAcMinor)
				.mintype(StrPrmMinType)
				.mincode(StrPrmMinCode)
				.tranamt(StrPrmTranAmt)
				.period(StrPrmPeriod)
				.reffrom(StrPrmRefFrom)
				.refupto(StrPrmRefupto)
				.project(StrPrmProject)		
				.bldgcode(StrPrmbldgCode)
				
				//.cfgroup(accoutingDataCashFlow.getCfGroup())
				//.cfcode(accoutingDataCashFlow.getCfCode())
				.cfgroup(" ")
				.cfcode(" ")
				.docnum(StrPrmDocnum)
				.docdate(StrPrmDocDate)
				.vounum(StrPrmDocnum)
				.voudate(StrPrmDocDate)
				//.paymode(StrPrmPaymode)
				.paymode("Q")
				.docpartype(StrPrmDocPartytype)
				.docparcode(StrPrmdocParcode)
				.xproject(StrPrmxProject)
				.xacmajor(StrPrmxacMajor)
				.xacminor(StrPrmxacMinor)
				.xmintype(StrPrmxMinType)
				.xpartytype(StrPrmxPartytype)
				.xpartycode(StrPrmxPartycode)
				.xreftranser(StrPrmxTranser)
				.xreftrandate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
				.site(StrPrmSite)
				.userid(StrPrmUserid)
				.today(LocalDateTime.now())
				.build();
		
	}
	public static  DetnarrBean updatedetNarrDetails( String strCoy,String StrTranser,Integer bunbum,String narrative,
			String dettype,String StrPrmSite,String StrPrmUserid)
	{
		return DetnarrBean.builder()
				.coy(strCoy)
				.transer(StrTranser) 
				.bunum(bunbum)
				.narrative(narrative) /// consumer no from overheadcons
				.dettype(dettype) /// sun class from sundata
				.site(StrPrmSite)
				.userid(StrPrmUserid)
				.today(LocalDateTime.now())
				.build();
		
	}
	
}
