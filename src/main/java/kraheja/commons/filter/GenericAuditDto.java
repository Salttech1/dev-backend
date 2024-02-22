package kraheja.commons.filter;


import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class GenericAuditDto {
	
	private boolean isAuditable; 
	private String uri;
    private String transactionNo;
    private List<String> transactionList;
    private Integer menucode;
    private String userid;
    private String site;
    private String transactionMode;
    private String remoteHost;
    private String ipAddress;
    private String httpMethod;
}
