package kraheja.purch.materialpayments.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.purch.bean.request.Auth_HRequestBean;
import kraheja.purch.bean.response.AuthHPassMaterialBean;
import kraheja.purch.bean.response.Auth_HResponseBean;
import kraheja.purch.bean.response.Auth_HResponseBean.Auth_HResponseBeanBuilder;
import kraheja.purch.entity.Auth_H;
import kraheja.purch.entity.Auth_HCK;

public interface Auth_HEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<Auth_HResponseBean>> fetchAuth_HEntityPojoMapper = objectArray -> {
		Auth_HResponseBeanBuilder auth_hBeanBuilder = Auth_HResponseBean.builder();

		List<Auth_H> auth_hEntityList = (List<Auth_H>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
						: null);
		return auth_hEntityList.stream().map(auth_hEntity -> {
			auth_hBeanBuilder
			.authnum(auth_hEntity.getAuthhCK().getAuthAuthnum())
			.advadjpast(auth_hEntity.getAuthAdvadjpast())
			.bldgcode(auth_hEntity.getAuthBldgcode())
			.partycode(auth_hEntity.getAuthPartycode())
			.matgroup(auth_hEntity.getAuthMatgroup())
			.transer(auth_hEntity.getAuthTranser())
			.coy(auth_hEntity.getAuthCoy())
			.advadjust(auth_hEntity.getAuthAdvadjust())
			.advgranted(auth_hEntity.getAuthAdvgranted())
			.authamount(auth_hEntity.getAuthAuthamount())
			.authdate(Objects.nonNull(auth_hEntity.getAuthAuthdate()) ? auth_hEntity.getAuthAuthdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.authquanty(auth_hEntity.getAuthAuthquanty())
			.authstatus(auth_hEntity.getAuthAuthstatus())
			.bldgauthno(auth_hEntity.getAuthBldgauthno())
			.debsocyn(auth_hEntity.getAuthDebsocyn())
			.description(auth_hEntity.getAuthDescription())
			.expenstype(auth_hEntity.getAuthExpenstype())
			.lotno(auth_hEntity.getAuthLotno())
			.matbldauth(auth_hEntity.getAuthMatbldauth())
			.matcode(auth_hEntity.getAuthMatcode())
			.misbldg(auth_hEntity.getAuthMisbldg())
			.misproject(auth_hEntity.getAuthMisproject())
			.opradvadj(auth_hEntity.getAuthOpradvadj())
			.opradvance(auth_hEntity.getAuthOpradvance())
			.oprauthamt(auth_hEntity.getAuthOprauthamt())
			.oprauthqty(auth_hEntity.getAuthOprauthqty())
			.oprrelret(auth_hEntity.getAuthOprrelret())
			.oprretain(auth_hEntity.getAuthOprretain())
			.oprretentionadj(auth_hEntity.getAuthOprretentionadj())
			.origsite(auth_hEntity.getAuthOrigsite())
			.partyauth(auth_hEntity.getAuthPartyauth())
			.partycode2(auth_hEntity.getAuthPartycode2())
			.partytype(auth_hEntity.getAuthPartytype())
			.passedon(Objects.nonNull(auth_hEntity.getAuthPassedon()) ? auth_hEntity.getAuthPassedon().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.payamount(auth_hEntity.getAuthPayamount())
			.paydate(Objects.nonNull(auth_hEntity.getAuthPaydate()) ? auth_hEntity.getAuthPaydate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.payref(auth_hEntity.getAuthPayref())
			.paytender(auth_hEntity.getAuthPaytender())
			.perdone(auth_hEntity.getAuthPerdone())
			.preparedby(auth_hEntity.getAuthPreparedby())
			.printed(auth_hEntity.getAuthPrinted())
			.printedon(Objects.nonNull(auth_hEntity.getAuthPrintedon()) ? auth_hEntity.getAuthPrintedon().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.project(auth_hEntity.getAuthProject())
			.prop(auth_hEntity.getAuthProp())
			.property(auth_hEntity.getAuthProperty())
			.prvadvadj(auth_hEntity.getAuthPrvadvadj())
			.prvadvance(auth_hEntity.getAuthPrvadvance())
			.prvauthamt(auth_hEntity.getAuthPrvauthamt())
			.prvauthno(auth_hEntity.getAuthPrvauthno())
			.prvauthqty(auth_hEntity.getAuthPrvauthqty())
			.prvdate(Objects.nonNull(auth_hEntity.getAuthPrvdate()) ? auth_hEntity.getAuthPrvdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.prvrelret(auth_hEntity.getAuthPrvrelret())
			.prvretamt(auth_hEntity.getAuthPrvretamt())
			.prvretentionadj(auth_hEntity.getAuthPrvretentionadj())
			.prvtype(auth_hEntity.getAuthPrvtype())
			.relretain(auth_hEntity.getAuthRelretain())
			.remarks(auth_hEntity.getAuthRemarks())
			.retained(auth_hEntity.getAuthRetained())
			.retentionadj(auth_hEntity.getAuthRetentionadj())
			.site(auth_hEntity.getAuthSite())
			.tdsamount(auth_hEntity.getAuthTdsamount())
			.today(auth_hEntity.getAuthToday())
			.userid(auth_hEntity.getAuthUserid())
			;
			return auth_hBeanBuilder.build();
		}).collect(Collectors.toList());
	};
	
	public static Function<List<AuthHPassMaterialBean>, List<Auth_HResponseBean>> fetchAuthHPassEntityPojoMapper = auth_hEntityList -> {

		return auth_hEntityList.stream().map(auth_hEntity -> {
			return Auth_HResponseBean.builder()
			.partyname(auth_hEntity.getPartyName())
			.authnum(auth_hEntity.getAuthAuthnum())
			.bldgcode(auth_hEntity.getAuthBldgcode())
			.partycode(auth_hEntity.getAuthPartycode())
			.matgroup(auth_hEntity.getAuthMatgroup())
			.authtype(auth_hEntity.getAuthAuthtype())
			.authstatus(auth_hEntity.getAuthAuthstatus())
			.transer(auth_hEntity.getAuthTranser())
			.coy(auth_hEntity.getAuthCoy())
			.payamount(auth_hEntity.getAuthPayamount())
			.advadjust(auth_hEntity.getAuthAdvadjust())
			.authamount(auth_hEntity.getAuthAuthamount())
			.authdate(Objects.nonNull(auth_hEntity.getAuthAuthdate()) ? auth_hEntity.getAuthAuthdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.partytype(auth_hEntity.getAuthPartytype())
			.passedon(Objects.nonNull(auth_hEntity.getAuthPassedon()) ? auth_hEntity.getAuthPassedon().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.payamount(auth_hEntity.getAuthPayamount())
			.project(auth_hEntity.getAuthProject())
			.prop(auth_hEntity.getAuthProp())
			.property(auth_hEntity.getAuthProperty())
			.relretain(auth_hEntity.getAuthRelretain())
			.retained(auth_hEntity.getAuthRetained())
			.tdsamount(auth_hEntity.getAuthTdsamount())
			.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], Auth_H> addAuth_HEntityPojoMapper = objectArray -> {
		Auth_HRequestBean auth_hRequestBean = (Auth_HRequestBean) (Objects
				.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);

		Auth_H.Auth_HBuilder auth_hbuilder = Auth_H.builder();	
		auth_hbuilder
		.authhCK(Auth_HCK.builder()
				.authAuthnum(auth_hRequestBean.getAuthnum())
				.build())
		.authBldgcode(auth_hRequestBean.getBldgcode())
		.authPartycode(auth_hRequestBean.getPartycode())
		.authMatgroup(auth_hRequestBean.getMatgroup())
		.authAuthtype(auth_hRequestBean.getAuthtype())
		.authTranser(auth_hRequestBean.getTranser())
		.authCoy(auth_hRequestBean.getCoy())
		.authAdvadjpast(auth_hRequestBean.getAdvadjpast())
		.authAdvadjust(auth_hRequestBean.getAdvadjust())
		.authAdvgranted(auth_hRequestBean.getAdvgranted())
		.authAuthamount(auth_hRequestBean.getAuthamount())
		.authAuthdate(Objects.nonNull(auth_hRequestBean.getAuthdate()) ? LocalDate.parse(auth_hRequestBean.getAuthdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.authAuthquanty(auth_hRequestBean.getAuthquanty())
		.authAuthstatus(auth_hRequestBean.getAuthstatus())
		.authBldgauthno(auth_hRequestBean.getBldgauthno())
		.authDebsocyn(auth_hRequestBean.getDebsocyn())
		.authDescription(auth_hRequestBean.getDescription())
		.authExpenstype(auth_hRequestBean.getExpenstype())
		.authLotno(auth_hRequestBean.getLotno())
		.authMatbldauth(auth_hRequestBean.getMatbldauth())
		.authMatcode(auth_hRequestBean.getMatcode())
		.authMisbldg(auth_hRequestBean.getMisbldg())
		.authMisproject(auth_hRequestBean.getMisproject())
		.authOpradvadj(auth_hRequestBean.getOpradvadj())
		.authOpradvance(auth_hRequestBean.getOpradvance())
		.authOprauthamt(auth_hRequestBean.getOprauthamt())
		.authOprauthqty(auth_hRequestBean.getOprauthqty())
		.authOprrelret(auth_hRequestBean.getOprrelret())
		.authOprretain(auth_hRequestBean.getOprretain())
		.authOprretentionadj(auth_hRequestBean.getOprretentionadj())
		.authOrigsite(GenericAuditContextHolder.getContext().getSite())
		.authPartyauth(auth_hRequestBean.getPartyauth())
		.authPartycode2(auth_hRequestBean.getPartycode2())
		.authPartytype(auth_hRequestBean.getPartytype())
		.authPassedon(Objects.nonNull(auth_hRequestBean.getPassedon()) ? LocalDate.parse(auth_hRequestBean.getPassedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.authPayamount(auth_hRequestBean.getPayamount())
		.authPaydate(Objects.nonNull(auth_hRequestBean.getPaydate()) ? LocalDate.parse(auth_hRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.authPayref(auth_hRequestBean.getPayref())
		.authPaytender(auth_hRequestBean.getPaytender())
		.authPerdone(auth_hRequestBean.getPerdone())
		.authPreparedby(auth_hRequestBean.getPreparedby())
		.authPrinted(auth_hRequestBean.getPrinted())
		.authPrintedon(Objects.nonNull(auth_hRequestBean.getPrintedon()) ? LocalDateTime.parse(auth_hRequestBean.getPrintedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.authProject(auth_hRequestBean.getProject())
		.authProp(auth_hRequestBean.getProp())
		.authProperty(auth_hRequestBean.getProperty())
		.authPrvadvadj(auth_hRequestBean.getPrvadvadj())
		.authPrvadvance(auth_hRequestBean.getPrvadvance())
		.authPrvauthamt(auth_hRequestBean.getPrvauthamt())
		.authPrvauthno(auth_hRequestBean.getPrvauthno())
		.authPrvauthqty(auth_hRequestBean.getPrvauthqty())
		.authPrvdate(Objects.nonNull(auth_hRequestBean.getPrvdate()) ? LocalDate.parse(auth_hRequestBean.getPrvdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.authPrvrelret(auth_hRequestBean.getPrvrelret())
		.authPrvretamt(auth_hRequestBean.getPrvretamt())
		.authPrvretentionadj(auth_hRequestBean.getPrvretentionadj())
		.authPrvtype(auth_hRequestBean.getPrvtype())
		.authRelretain(auth_hRequestBean.getRelretain())
		.authRemarks(auth_hRequestBean.getRemarks())
		.authRetained(auth_hRequestBean.getRetained())
		.authRetentionadj(auth_hRequestBean.getRetentionadj())
		.authSite(GenericAuditContextHolder.getContext().getSite())
		.authTdsamount(auth_hRequestBean.getTdsamount())
		.authToday(LocalDateTime.now())
		.authUserid(GenericAuditContextHolder.getContext().getUserid());
		return auth_hbuilder.build();
	};

	public static BiFunction<Auth_H, Auth_HRequestBean, Auth_H> updateAuth_HEntityPojoMapper = (auth_hEntity, auth_hRequestBean) -> {
		auth_hEntity.setAuthBldgcode(Objects.nonNull(auth_hRequestBean.getAdvadjpast()) ? auth_hRequestBean.getBldgcode() : auth_hEntity.getAuthBldgcode());
		auth_hEntity.setAuthPartycode(Objects.nonNull(auth_hRequestBean.getPartycode()) ? auth_hRequestBean.getPartycode() : auth_hEntity.getAuthPartycode());
		auth_hEntity.setAuthTranser(Objects.nonNull(auth_hRequestBean.getTranser()) ? auth_hRequestBean.getTranser() : auth_hEntity.getAuthTranser());
		auth_hEntity.setAuthMatgroup(Objects.nonNull(auth_hRequestBean.getMatgroup()) ? auth_hRequestBean.getMatgroup() : auth_hEntity.getAuthMatgroup());
		auth_hEntity.setAuthCoy(Objects.nonNull(auth_hRequestBean.getCoy()) ? auth_hRequestBean.getCoy() : auth_hEntity.getAuthCoy());
		auth_hEntity.setAuthAdvadjpast(Objects.nonNull(auth_hRequestBean.getAdvadjpast()) ? auth_hRequestBean.getAdvadjpast() : auth_hEntity.getAuthAdvadjpast());
		auth_hEntity.setAuthAdvadjust(Objects.nonNull(auth_hRequestBean.getAdvadjust()) ? auth_hRequestBean.getAdvadjust() : auth_hEntity.getAuthAdvadjust());
		auth_hEntity.setAuthAdvgranted(Objects.nonNull(auth_hRequestBean.getAdvgranted()) ? auth_hRequestBean.getAdvgranted() : auth_hEntity.getAuthAdvgranted());
		auth_hEntity.setAuthAuthamount(Objects.nonNull(auth_hRequestBean.getAuthamount()) ? auth_hRequestBean.getAuthamount() : auth_hEntity.getAuthAuthamount());
		auth_hEntity.setAuthAuthdate(Objects.nonNull(auth_hRequestBean.getAuthdate()) ? LocalDate.parse(auth_hRequestBean.getAuthdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : auth_hEntity.getAuthAuthdate());
		auth_hEntity.setAuthAuthquanty(Objects.nonNull(auth_hRequestBean.getAuthquanty()) ? auth_hRequestBean.getAuthquanty() : auth_hEntity.getAuthAuthquanty());
		auth_hEntity.setAuthAuthstatus(Objects.nonNull(auth_hRequestBean.getAuthstatus()) ? auth_hRequestBean.getAuthstatus().trim() : auth_hEntity.getAuthAuthstatus());
		auth_hEntity.setAuthBldgauthno(Objects.nonNull(auth_hRequestBean.getBldgauthno()) ? auth_hRequestBean.getBldgauthno() : auth_hEntity.getAuthBldgauthno());
		auth_hEntity.setAuthDebsocyn(Objects.nonNull(auth_hRequestBean.getDebsocyn()) ? auth_hRequestBean.getDebsocyn().trim() : auth_hEntity.getAuthDebsocyn());
		auth_hEntity.setAuthDescription(Objects.nonNull(auth_hRequestBean.getDescription()) ? auth_hRequestBean.getDescription().trim() : auth_hEntity.getAuthDescription());
		auth_hEntity.setAuthExpenstype(Objects.nonNull(auth_hRequestBean.getExpenstype()) ? auth_hRequestBean.getExpenstype().trim() : auth_hEntity.getAuthExpenstype());
		auth_hEntity.setAuthLotno(Objects.nonNull(auth_hRequestBean.getLotno()) ? auth_hRequestBean.getLotno().trim() : auth_hEntity.getAuthLotno());
		auth_hEntity.setAuthMatbldauth(Objects.nonNull(auth_hRequestBean.getMatbldauth()) ? auth_hRequestBean.getMatbldauth() : auth_hEntity.getAuthMatbldauth());
		auth_hEntity.setAuthMatcode(Objects.nonNull(auth_hRequestBean.getMatcode()) ? auth_hRequestBean.getMatcode().trim() : auth_hEntity.getAuthMatcode());
		auth_hEntity.setAuthMisbldg(Objects.nonNull(auth_hRequestBean.getMisbldg()) ? auth_hRequestBean.getMisbldg().trim() : auth_hEntity.getAuthMisbldg());
		auth_hEntity.setAuthMisproject(Objects.nonNull(auth_hRequestBean.getMisproject()) ? auth_hRequestBean.getMisproject().trim() : auth_hEntity.getAuthMisproject());
		auth_hEntity.setAuthOpradvadj(Objects.nonNull(auth_hRequestBean.getOpradvadj()) ? auth_hRequestBean.getOpradvadj() : auth_hEntity.getAuthOpradvadj());
		auth_hEntity.setAuthOpradvance(Objects.nonNull(auth_hRequestBean.getOpradvance()) ? auth_hRequestBean.getOpradvance() : auth_hEntity.getAuthOpradvance());
		auth_hEntity.setAuthOprauthamt(Objects.nonNull(auth_hRequestBean.getOprauthamt()) ? auth_hRequestBean.getOprauthamt() : auth_hEntity.getAuthOprauthamt());
		auth_hEntity.setAuthOprauthqty(Objects.nonNull(auth_hRequestBean.getOprauthqty()) ? auth_hRequestBean.getOprauthqty() : auth_hEntity.getAuthOprauthqty());
		auth_hEntity.setAuthOprrelret(Objects.nonNull(auth_hRequestBean.getOprrelret()) ? auth_hRequestBean.getOprrelret() : auth_hEntity.getAuthOprrelret());
		auth_hEntity.setAuthOprretain(Objects.nonNull(auth_hRequestBean.getOprretain()) ? auth_hRequestBean.getOprretain() : auth_hEntity.getAuthOprretain());
		auth_hEntity.setAuthOprretentionadj(Objects.nonNull(auth_hRequestBean.getOprretentionadj()) ? auth_hRequestBean.getOprretentionadj() : auth_hEntity.getAuthOprretentionadj());
		auth_hEntity.setAuthOrigsite(Objects.nonNull(auth_hRequestBean.getOrigsite()) ? auth_hRequestBean.getOrigsite().trim() : auth_hEntity.getAuthOrigsite());
		auth_hEntity.setAuthPartyauth(Objects.nonNull(auth_hRequestBean.getPartyauth()) ? auth_hRequestBean.getPartyauth() : auth_hEntity.getAuthPartyauth());
		auth_hEntity.setAuthPartycode2(Objects.nonNull(auth_hRequestBean.getPartycode2()) ? auth_hRequestBean.getPartycode2().trim() : auth_hEntity.getAuthPartycode2());
		auth_hEntity.setAuthPartytype(Objects.nonNull(auth_hRequestBean.getPartytype()) ? auth_hRequestBean.getPartytype().trim() : auth_hEntity.getAuthPartytype());
		auth_hEntity.setAuthPassedon(Objects.nonNull(auth_hRequestBean.getPassedon()) ? LocalDate.parse(auth_hRequestBean.getPassedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : auth_hEntity.getAuthPassedon());
		auth_hEntity.setAuthPayamount(Objects.nonNull(auth_hRequestBean.getPayamount()) ? auth_hRequestBean.getPayamount() : auth_hEntity.getAuthPayamount());
		auth_hEntity.setAuthPaydate(Objects.nonNull(auth_hRequestBean.getPaydate()) ? LocalDate.parse(auth_hRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : auth_hEntity.getAuthPaydate());
		auth_hEntity.setAuthPayref(Objects.nonNull(auth_hRequestBean.getPayref()) ? auth_hRequestBean.getPayref().trim() : auth_hEntity.getAuthPayref());
		auth_hEntity.setAuthPaytender(Objects.nonNull(auth_hRequestBean.getPaytender()) ? auth_hRequestBean.getPaytender().trim() : auth_hEntity.getAuthPaytender());
		auth_hEntity.setAuthPerdone(Objects.nonNull(auth_hRequestBean.getPerdone()) ? auth_hRequestBean.getPerdone() : auth_hEntity.getAuthPerdone());
		auth_hEntity.setAuthPreparedby(Objects.nonNull(auth_hRequestBean.getPreparedby()) ? auth_hRequestBean.getPreparedby().trim() : auth_hEntity.getAuthPreparedby());
		auth_hEntity.setAuthPrinted(Objects.nonNull(auth_hRequestBean.getPrinted()) ? auth_hRequestBean.getPrinted() : auth_hEntity.getAuthPrinted());
		auth_hEntity.setAuthPrintedon(Objects.nonNull(auth_hRequestBean.getPrintedon()) ? LocalDateTime.parse(auth_hRequestBean.getPrintedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : auth_hEntity.getAuthPrintedon());
		auth_hEntity.setAuthProject(Objects.nonNull(auth_hRequestBean.getProject()) ? auth_hRequestBean.getProject().trim() : auth_hEntity.getAuthProject());
		auth_hEntity.setAuthProp(Objects.nonNull(auth_hRequestBean.getProp()) ? auth_hRequestBean.getProp().trim() : auth_hEntity.getAuthProp());
		auth_hEntity.setAuthProperty(Objects.nonNull(auth_hRequestBean.getProperty()) ? auth_hRequestBean.getProperty().trim() : auth_hEntity.getAuthProperty());
		auth_hEntity.setAuthPrvadvadj(Objects.nonNull(auth_hRequestBean.getPrvadvadj()) ? auth_hRequestBean.getPrvadvadj() : auth_hEntity.getAuthPrvadvadj());
		auth_hEntity.setAuthPrvadvance(Objects.nonNull(auth_hRequestBean.getPrvadvance()) ? auth_hRequestBean.getPrvadvance() : auth_hEntity.getAuthPrvadvance());
		auth_hEntity.setAuthPrvauthamt(Objects.nonNull(auth_hRequestBean.getPrvauthamt()) ? auth_hRequestBean.getPrvauthamt() : auth_hEntity.getAuthPrvauthamt());
		auth_hEntity.setAuthPrvauthno(Objects.nonNull(auth_hRequestBean.getPrvauthno()) ? auth_hRequestBean.getPrvauthno().trim() : auth_hEntity.getAuthPrvauthno());
		auth_hEntity.setAuthPrvauthqty(Objects.nonNull(auth_hRequestBean.getPrvauthqty()) ? auth_hRequestBean.getPrvauthqty() : auth_hEntity.getAuthPrvauthqty());
		auth_hEntity.setAuthPrvdate(Objects.nonNull(auth_hRequestBean.getPrvdate()) ? LocalDate.parse(auth_hRequestBean.getPrvdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : auth_hEntity.getAuthPrvdate());
		auth_hEntity.setAuthPrvrelret(Objects.nonNull(auth_hRequestBean.getPrvrelret()) ? auth_hRequestBean.getPrvrelret() : auth_hEntity.getAuthPrvrelret());
		auth_hEntity.setAuthPrvretamt(Objects.nonNull(auth_hRequestBean.getPrvretamt()) ? auth_hRequestBean.getPrvretamt() : auth_hEntity.getAuthPrvretamt());
		auth_hEntity.setAuthPrvretentionadj(Objects.nonNull(auth_hRequestBean.getPrvretentionadj()) ? auth_hRequestBean.getPrvretentionadj() : auth_hEntity.getAuthPrvretentionadj());
		auth_hEntity.setAuthPrvtype(Objects.nonNull(auth_hRequestBean.getPrvtype()) ? auth_hRequestBean.getPrvtype().trim() : auth_hEntity.getAuthPrvtype());
		auth_hEntity.setAuthRelretain(Objects.nonNull(auth_hRequestBean.getRelretain()) ? auth_hRequestBean.getRelretain() : auth_hEntity.getAuthRelretain());
		auth_hEntity.setAuthRemarks(Objects.nonNull(auth_hRequestBean.getRemarks()) ? auth_hRequestBean.getRemarks().trim() : auth_hEntity.getAuthRemarks());
		auth_hEntity.setAuthRetained(Objects.nonNull(auth_hRequestBean.getRetained()) ? auth_hRequestBean.getRetained() : auth_hEntity.getAuthRetained());
		auth_hEntity.setAuthRetentionadj(Objects.nonNull(auth_hRequestBean.getRetentionadj()) ? auth_hRequestBean.getRetentionadj() : auth_hEntity.getAuthRetentionadj());
		auth_hEntity.setAuthSite(GenericAuditContextHolder.getContext().getSite());
		auth_hEntity.setAuthTdsamount(Objects.nonNull(auth_hRequestBean.getTdsamount()) ? auth_hRequestBean.getTdsamount() : auth_hEntity.getAuthTdsamount());
		auth_hEntity.setAuthToday(LocalDateTime.now());
		auth_hEntity.setAuthAuthtype(Objects.nonNull(auth_hRequestBean.getAuthtype()) ? auth_hRequestBean.getAuthtype() : auth_hEntity.getAuthAuthtype());;
		auth_hEntity.setAuthUserid(GenericAuditContextHolder.getContext().getUserid());

		return auth_hEntity;
	};

}
