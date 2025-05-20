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
@SerdeImport(KeyDto.class)
public class KeyDto {

    /**
     * Tipo de llave
     */
    private String keyType;

    /**
     * Valor de la llave
     */
    private String valueKey;
}
