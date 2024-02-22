package kraheja.adminexp.billing.dataentry.adminBill.bean.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PartyIsLegalOrSecurityResponseBean {
   private Boolean isLegal;
   private Boolean isSecurity;
   private Boolean isDisabled;
}
