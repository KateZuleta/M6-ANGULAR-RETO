package co.bancolombia.aplicacionbancaria.modelo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class CompraResponse {
    private Long cuentaId;
    private BigDecimal saldoCuenta;
    private BigDecimal valor;
    private String tipoTransaccion;
    private String subTipoTransaccion;

}
