package kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
    private boolean success;
    private String message;
    private T data;
    
    public GenericResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}


