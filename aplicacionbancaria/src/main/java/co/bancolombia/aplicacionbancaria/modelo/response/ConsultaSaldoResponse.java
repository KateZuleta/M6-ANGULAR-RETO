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
public class ConsultaSaldoResponse {
    private String nroCuenta;
    private BigDecimal saldo;
}
