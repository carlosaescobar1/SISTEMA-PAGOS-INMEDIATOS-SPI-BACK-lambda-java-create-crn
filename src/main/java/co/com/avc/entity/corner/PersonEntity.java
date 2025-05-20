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
 * AcctInfoEntity
 * <p>
 * Desarrollo ATH - SPBVI
 * <p>
 * Creado él: 09 de septiembre de 2024
 *
 * @author Luis F. Herreño Mateus
 * @version 1.0
 * @since 1.0
 * <p>
 * Requerimiento: SPBVI - Sistema de pagos de bajo valor inmediatos
 * <p>
 * Copyright © A Toda Hora S.A. Todos los derechos reservados
 * <p>
 * Este software es confidencial y es propiedad de ATH, queda prohibido
 * su uso, reproducción y copia de manera parcial o permanente salvo autorización
 * expresa de A Toda Hora S.A o de quién represente sus derechos.
 */

@Getter
@Setter
@ToString
@Introspected
@ReflectiveAccess
@SerdeImport(PersonEntity.class)
@DynamoDBDocument
public class PersonEntity {

    /**
     * Primer nombre del cliente
     */
    @DynamoDBAttribute(attributeName = "firstName")
    private String firstName;

    /**
     * Segundo nombre del cliente
     */
    @DynamoDBAttribute(attributeName = "secondName")
    private String secondName;

    /**
     * Primer apellido del cliente
     */
    @DynamoDBAttribute(attributeName = "firstSurName")
    private String firstSurName;

    /**
     * Segundo apellido del cliente
     */
    @DynamoDBAttribute(attributeName = "secondSurName")
    private String secondSurName;

    /**
     * Tipo de persona
     */
    @DynamoDBAttribute(attributeName = "typePerson")
    private String typePerson;

    /**
     * Nombre legal de la empresa
     */
    @DynamoDBAttribute(attributeName = "businessName")
    private String businessName;

    /**
     * Tipo de documento
     */
    @DynamoDBAttribute(attributeName = "documentType")
    private String documentType;

    /**
     * Numero de documento
     */
    @DynamoDBAttribute(attributeName = "documentNumber")
    private String documentNumber;

    public static final TableSchema<PersonEntity> SCHEMA =
            TableSchema.builder(PersonEntity.class)
                    .newItemSupplier(PersonEntity::new)
                    .addAttribute(String.class, a -> a.name("firstName")
                            .getter(PersonEntity::getFirstName)
                            .setter(PersonEntity::setFirstName))
                    .addAttribute(String.class, a -> a.name("secondName")
                            .getter(PersonEntity::getSecondName)
                            .setter(PersonEntity::setSecondName))
                    .addAttribute(String.class, a -> a.name("firstSurName")
                            .getter(PersonEntity::getFirstSurName)
                            .setter(PersonEntity::setFirstSurName))
                    .addAttribute(String.class, a -> a.name("secondSurName")
                            .getter(PersonEntity::getSecondSurName)
                            .setter(PersonEntity::setSecondSurName))
                    .addAttribute(String.class, a -> a.name("typePerson")
                            .getter(PersonEntity::getTypePerson)
                            .setter(PersonEntity::setTypePerson))
                    .addAttribute(String.class, a -> a.name("businessName")
                            .getter(PersonEntity::getBusinessName)
                            .setter(PersonEntity::setBusinessName))
                    .addAttribute(String.class, a -> a.name("documentType")
                            .getter(PersonEntity::getDocumentType)
                            .setter(PersonEntity::setDocumentType))
                    .addAttribute(String.class, a -> a.name("documentNumber")
                            .getter(PersonEntity::getDocumentNumber)
                            .setter(PersonEntity::setDocumentNumber))
                    .build();
}
