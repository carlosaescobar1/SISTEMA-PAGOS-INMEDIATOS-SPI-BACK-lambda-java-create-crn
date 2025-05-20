package co.com.avc.entity.corner;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

/**
 * PaymentMethodEntity
 * <p>
 * Desarrollo ATH - SPBVI
 * <p>
 * Creado él: 09 de septiembre de 2024
 *
 * @author Luis F. Herreño Mateus
 * @version 1.0
 * @since 1.0
 *
 * Requerimiento: SPBVI - Sistema de pagos de bajo valor inmediatos
 *
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 *
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 */

@Getter
@Setter
@ToString
@Introspected
@SerdeImport(PaymentMethodEntity.class)
@ReflectiveAccess
@DynamoDBDocument
public class PaymentMethodEntity {

    @DynamoDBAttribute(attributeName = "typePaymentAcc")
    private String typePaymentAcc;

    @DynamoDBAttribute(attributeName = "accountNumber")
    private String accountNumber;

    public static final TableSchema<PaymentMethodEntity> SCHEMA = TableSchema.builder(PaymentMethodEntity.class)
                    .newItemSupplier(PaymentMethodEntity::new)
                    .addAttribute(String.class, a -> a.name("typePaymentAcc")
                            .getter(PaymentMethodEntity::getTypePaymentAcc)
                            .setter(PaymentMethodEntity::setAccountNumber))
                    .addAttribute(String.class, a -> a.name("accountNumber")
                            .getter(PaymentMethodEntity::getAccountNumber)
                            .setter(PaymentMethodEntity::setAccountNumber))
                    .build();
}
