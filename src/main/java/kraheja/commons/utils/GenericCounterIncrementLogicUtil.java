package kraheja.commons.utils;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Coybillserial;
import kraheja.adminexp.billing.dataentry.invoiceCreation.repository.CoybillserialRepository;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.EntityRepository;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class GenericCounterIncrementLogicUtil {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static EntityRepository entityRepository;
	
	@Autowired
	private CoybillserialRepository coybillserialRepository;

	@Autowired
	public void setEntityRepository(EntityRepository entityRepository) {
		GenericCounterIncrementLogicUtil.entityRepository = entityRepository;
	}

	public static String generateTranNo(String entClass, String entId) {
		String entity = entityRepository.fetchByEntityCk_EntClassAndEntityCk_EntId(entClass, entId);
		logger.info("DB Entity :: {}", entity);
		
		// 1 index e.entNum1,
		// 2 e.entityCk.entChar1,
		// 3 e.entityCk.entChar2 
		
		String[] entityArray = entity.split(CommonConstraints.INSTANCE.COMMA_STRING);
		Integer lastCounterValue =  Double.valueOf(entityArray[0]).intValue();
		String starFormatTemplate = null;
		String checkSuccessiveSeries = null;
		if(!entityArray[1].equals("null")) {
			starFormatTemplate = entityArray[1].trim();
			Integer indexOfStar = starFormatTemplate.indexOf(CommonConstraints.INSTANCE.ASTERISK_STRING);

			if(!entityArray[2].equals("null"))
				checkSuccessiveSeries = entityArray[2];

			if(indexOfStar >= 0) {
				if(checkSuccessiveSeries == "Y")
					return lastCounterValue.toString();
				else {
					String strWithoutStar = starFormatTemplate.replace(CommonConstraints.INSTANCE.ASTERISK_STRING, CommonConstraints.INSTANCE.BLANK_STRING);
					String result=(starFormatTemplate.substring(lastCounterValue.toString().length(),starFormatTemplate.length())+lastCounterValue.toString()).replace(CommonConstraints.INSTANCE.ASTERISK_STRING, BigInteger.ZERO.toString());
					entityRepository.updateIncrementCounter(entClass, entId, lastCounterValue.doubleValue() + 1);
					return strWithoutStar.concat(result.substring(strWithoutStar.length()));
				}
			}
		}else {
			entityRepository.updateIncrementCounter(entClass, entId, lastCounterValue.doubleValue() + 1);
			return lastCounterValue.toString();
		}
		return null;
	}
	
	public static String generateTranNoWithSite(String entClass, String entId, String site) {
		String entity = entityRepository.fetchByEntityCk_EntClassAndEntityCk_EntIdAndEntityCk_EntChar1(entClass, entId, site);
		logger.info("DB Entity :: {}", entity);
		
		String[] entityArray = entity.split(CommonConstraints.INSTANCE.COMMA_STRING);
		Integer lastCounterValue =  Double.valueOf(entityArray[0]).intValue();
		String starFormatTemplate = null;
		String checkSuccessiveSeries = null;
		if(!entityArray[1].equals("null")) {
			starFormatTemplate = entityArray[1].trim();
			Integer indexOfStar = starFormatTemplate.indexOf(CommonConstraints.INSTANCE.ASTERISK_STRING);

			if(!entityArray[2].equals("null"))
				checkSuccessiveSeries = entityArray[2];

			if(indexOfStar >= 0) {
				if(checkSuccessiveSeries == "Y")
					return lastCounterValue.toString();
				else {
					String strWithoutStar = starFormatTemplate.replace(CommonConstraints.INSTANCE.ASTERISK_STRING, CommonConstraints.INSTANCE.BLANK_STRING);
					String result=(starFormatTemplate.substring(lastCounterValue.toString().length(), starFormatTemplate.length())+lastCounterValue.toString()).replace(CommonConstraints.INSTANCE.ASTERISK_STRING, BigInteger.ZERO.toString());
					entityRepository.updateIncrementCounterWithSite(entClass, entId, lastCounterValue.doubleValue() + 1, site);
					return strWithoutStar.concat(result.substring(strWithoutStar.length()));
				}
			}
		}else {
			entityRepository.updateIncrementCounterWithSite(entClass, entId, lastCounterValue.doubleValue() + 1, site);
			return lastCounterValue.toString();
		}
		return null;
	}
	
	public String generateTranNoWithCompanyCodeAndEntClassAndEntIdAndSite(String companyCode ,String entClass, String entId, String site, String date) {
		
		Coybillserial coybillserialentity =null;
		if(companyCode.equalsIgnoreCase("FEHO") ||companyCode.equalsIgnoreCase("FERA")  || companyCode.equalsIgnoreCase("INDV") || companyCode.equalsIgnoreCase("PMCD") || companyCode.equalsIgnoreCase("DIDO"))
		{
			companyCode="FERA";
		}
		try {
		
		 coybillserialentity = coybillserialRepository.findCustomResults(companyCode.trim(), entClass, entId, site, date);
			
				log.info(coybillserialentity);
						} 
		catch(Exception e) {
			e.printStackTrace();
		}
		
	
		logger.info("DB Entity :: {}", coybillserialentity);

		if(Objects.nonNull(coybillserialentity)){
			String transerNo = null;
			
			String stringWithZeros = "000000"; // The initial string with 6 zeros
			String serNo=coybillserialentity.getCbsSrno().toString();
			double doubleValue = Double.parseDouble(serNo);
			int number =(int) doubleValue ; 
			
	        // Convert the number to a string with leading zeros
	        String numberString = String.format("%06d", number);

	        // Replace the portion of the inputString with numberString
	        String resultString = stringWithZeros.substring(0, 6 - numberString.length()) + numberString;
			
			transerNo = coybillserialentity.getCoybillserialCK().getCbsCoycode().trim()
					  + coybillserialentity.getCoybillserialCK().getCbsBilltype().trim()
					  + coybillserialentity.getCbsSitechar()
					  + coybillserialentity.getCoybillserialCK().getCbsYear().substring(2, 4)
					  + resultString;
			log.info("transerNo : {} ",transerNo);
			coybillserialRepository.updateCoybillserial(coybillserialentity.getCbsSrno() + 1,GenericAuditContextHolder.getContext().getUserid(),companyCode,entClass,entId,GenericAuditContextHolder.getContext().getSite(),date);
			return transerNo;
		}
 
		return null ;
	}
	
	
   public String generateInvoiceNo(String companyCode, String entClass, String entId, String site, String financialYearMonth) {
		Coybillserial coybillserialentity = Coybillserial.builder().build();
		
		try {
			 if(companyCode.equalsIgnoreCase("FEHO") ||companyCode.equalsIgnoreCase("FERA")  || companyCode.equalsIgnoreCase("INDV") || companyCode.equalsIgnoreCase("PMCD") || companyCode.equalsIgnoreCase("DIDO")) {
				 companyCode = "FERA";
			 }
			 coybillserialentity = coybillserialRepository.findCustomResults(companyCode, entClass, entId, site, financialYearMonth);
			} 
		
		catch(Exception exception) {
			logger.debug("exception occured: {}", exception.getMessage());

		}
		if(Objects.nonNull(coybillserialentity)){
			String transerNo = null;
			String stringWithZeros = "000000"; // THE INITIAL STRING WITH 6 ZEROS
			
			
			String strSerNo = coybillserialentity.getCbsSrno().toString();
			int number = (int) Double.parseDouble(strSerNo);
			
	        // CONVERT THE NUMBER TO A STRING WITH LEADING ZEROS
	        String numberString = String.format("%06d", number);

	        // REPLACE THE PORTION OF THE INPUTSTRING WITH NUMBERSTRING
	        String resultString = stringWithZeros.substring(0, 6 - numberString.length()) + numberString;
			
			transerNo = coybillserialentity.getCoybillserialCK().getCbsCoycode().trim()
					  + coybillserialentity.getCoybillserialCK().getCbsBilltype().trim()
					  + coybillserialentity.getCbsSitechar()
					  + coybillserialentity.getCoybillserialCK().getCbsYear().substring(2, 4)
					  + resultString;
			log.info("transerNo : {} ",transerNo);
			Double cbsSrno = coybillserialentity.getCbsSrno();
			
			coybillserialentity.setCbsSrno(cbsSrno + 1);
			coybillserialentity.setCbsUserid(GenericAuditContextHolder.getContext().getUserid());
			coybillserialentity.setCbsModifiedon(LocalDateTime.now());	
			coybillserialRepository.save(coybillserialentity);
			return transerNo;
		}
 
		return null ;
	}
	
}