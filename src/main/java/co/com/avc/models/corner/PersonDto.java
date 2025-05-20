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
@SerdeImport(PersonDto.class)
public class PersonDto {

    /**
     * Primer nombre del cliente
     */
    private String firstName;

    /**
     * Segundo nombre del cliente
     */
    private String secondName;

    /**
     * Primer apellido del cliente
     */
    private String firstSurName;

    /**
     * Segundo apellido del cliente
     */
    private String secondSurName;

    /**
     * Tipo de persona
     */
    private String typePerson;

    /**
     * Nombre legal de la empresa
     */
    private String businessName;

    /**
     * Tipo de documento
     */
    private String documentType;

    /**
     * Numero de documento
     */
    private String documentNumber;

}
