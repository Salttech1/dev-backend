package kraheja.commons.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class GenericAuditBuilder {

    public GenericAuditDto build(HttpServletRequest httpServletRequest) {
        return GenericAuditDto.builder()
        		.uri(httpServletRequest.getRequestURI())
        		.httpMethod(httpServletRequest.getMethod())
        		.menucode(StringUtils.isNotBlank(httpServletRequest.getHeader("menucode")) ? Integer.parseInt(httpServletRequest.getHeader("menucode")) : null)
        		.site(httpServletRequest.getHeader("site"))
        		.userid(httpServletRequest.getHeader("userid"))
        		.ipAddress(httpServletRequest.getRemoteAddr())
        		.remoteHost(httpServletRequest.getRemoteHost())
                //populate fields here from httpServlet
                .build();
    }
}
