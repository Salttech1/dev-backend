package kraheja.fd.deposit.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.utils.CommonConstraints;
import kraheja.fd.deposit.entity.Depint;
import kraheja.fd.deposit.entity.DepintCK;
import kraheja.fd.deposit.entity.Tdepint;

public interface FdEntityEntityMapper {

	@SuppressWarnings("unchecked")
	public static Function<Object[], List<Depint>> addDepositToTdepintFdPojoEntityMapping = objectArray -> {
		List<Tdepint> tdepintList  = (List<Tdepint>)(Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String transer  = (String)(Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		
		return tdepintList.stream().map(tdepint-> {
			return Depint.builder()
					.depintCK(DepintCK.builder()
							.dinCoy(tdepint.getTDepintCK().getTdinCoy())
							.dinDepositor(tdepint.getTDepintCK().getTdinDepositor())
							.dinReceiptnum(tdepint.getTDepintCK().getTdinReceiptnum())
							.dinChqnum(CommonConstraints.INSTANCE.SPACE_STRING)						
							.dinIntfrom(tdepint.getTdinIntfrom())
							.dinIntupto(tdepint.getTDepintCK().getTdinIntupto())
							.build())
					.dinBankcode(tdepint.getTdinBankcode())
					.dinCanceldate(tdepint.getTdinCanceldate())
					.dinFromdate(tdepint.getTdinFromdate())
					.dinInterest(tdepint.getTdinInterest())
					.dinNewprin(tdepint.getTdinNewprin())
					.dinOrigsite(tdepint.getTdinSite().trim())
					.dinProprietor(tdepint.getTdinProprietor())
					.dinSessid(tdepint.getTdinSessid())
					.dinSite(tdepint.getTdinSite().trim())
					.dinStaffallow(tdepint.getTdinStaffallow())
					.dinTaxalwaysyn("Y")
					.dinTds(tdepint.getTdinTds())
					.dinTds15hyn(tdepint.getTdinTds15hyn())
					.dinTodate(tdepint.getTdinTodate())
					.dinToday(LocalDateTime.now())
					.dinTranser(transer)
					.dinUserid(tdepint.getTdinUserid().trim())
					.build();
		}).collect(Collectors.toList());
	};

}

