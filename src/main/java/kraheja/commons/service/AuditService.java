package kraheja.commons.service;

import kraheja.commons.filter.GenericAuditDto;


public interface AuditService {

	void audit(GenericAuditDto genericAuditDto);
}
