package kraheja.sales.bean.request;

import lombok.Data;

@Data
public class OutgoingSocietyAccountsReportRequestBean {
	String proprietor;
	String company;
	String dateUpto;
}
