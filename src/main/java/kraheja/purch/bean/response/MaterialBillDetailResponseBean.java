package kraheja.purch.bean.response;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class MaterialBillDetailResponseBean {
	
	String suppbillno;
    String suppbilldt;
    BigDecimal authamount;
	BigDecimal debitamt;
	BigDecimal authtdsamt;
	BigDecimal retainos;
	BigDecimal advadj;
	BigDecimal advadj2;
	BigDecimal payamount;
	BigDecimal authqty;
	BigDecimal retainamt;
	BigDecimal relretamt;
	Character billtype;
	String ser;
	String matcode;
	String matgroup;
	Character omspurcyn;
	String uom;
	String warningMessage;
	
}