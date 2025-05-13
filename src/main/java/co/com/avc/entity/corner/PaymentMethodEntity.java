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
