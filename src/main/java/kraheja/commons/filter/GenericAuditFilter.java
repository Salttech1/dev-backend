package kraheja.commons.filter;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import kraheja.commons.service.AuditService;

@Component
@Order(1)
public class GenericAuditFilter implements Filter {

	@Autowired
	private GenericAuditBuilder genericAuditBuilder;

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	
	@Autowired
	private AuditService auditService;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			GenericAuditDto build = genericAuditBuilder.build((HttpServletRequest) servletRequest);
			GenericAuditContextHolder.setContext(build);
			filterChain.doFilter(servletRequest, servletResponse);
		} finally {
			// service to save
			LOGGER.info("GenericAuditDto :: {}", GenericAuditContextHolder.getContext());
			if (GenericAuditContextHolder.getContext().isAuditable()) 
				this.auditService.audit(GenericAuditContextHolder.getContext());

			GenericAuditContextHolder.clearContext();
		}
	}
}
