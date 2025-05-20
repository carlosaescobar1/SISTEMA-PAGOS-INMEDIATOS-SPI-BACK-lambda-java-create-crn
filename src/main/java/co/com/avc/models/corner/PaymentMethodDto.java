package co.com.avc.models.corner;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Introspected
@SerdeImport(PaymentMethodDto.class)
public class PaymentMethodDto {

    /**
     * Tipo de pago
     */
    private String typePaymentAcc;

    /**
     * Número de cuenta
     */
    private String accountNumber;
}
