package kraheja.adminexp.billing.dataentry.invoiceCreation.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class InvpartymasterCK implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ipmsCoycode;

	private String ipmsPartytype;

	private String ipmsPartycode;

	private String ipmsBilltype;

	private String ipmsItemBillCode;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
