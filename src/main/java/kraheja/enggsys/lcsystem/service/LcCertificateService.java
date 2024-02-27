package kraheja.enggsys.lcsystem.service;

import kraheja.enggsys.lcsystem.payload.request.LcCertificateRequest;
import kraheja.enggsys.lcsystem.payload.response.ContractResponse;
import kraheja.payload.GenericResponse;

public interface LcCertificateService {

	public ContractResponse getContract(String recId, String certType, String lcerCertnum);
	public GenericResponse makeCertificate(LcCertificateRequest request, String recId, String certType, String lcerCertnum);
	public LcCertificateRequest retrieveCertificate(String recId, String certType, String lcerCertnum);
	public String fetchLastCertficateNo(String recId);
}
