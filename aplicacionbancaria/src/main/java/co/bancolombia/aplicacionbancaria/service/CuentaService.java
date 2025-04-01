package co.bancolombia.aplicacionbancaria.service;

import co.bancolombia.aplicacionbancaria.modelo.CuentaBanco;
import co.bancolombia.aplicacionbancaria.modelo.DTO.ConsultaSaldoDTO;
import co.bancolombia.aplicacionbancaria.modelo.response.ConsultaSaldoResponse;
import co.bancolombia.aplicacionbancaria.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public ConsultaSaldoResponse consultarSaldo(ConsultaSaldoDTO consultaSaldoDTO) {
        CuentaBanco  cuentaBanco = cuentaRepository.findByNroCuenta(consultaSaldoDTO.getIdCuenta())
                .orElseThrow(() -> new NoSuchElementException("La cuenta con ID " + consultaSaldoDTO.getIdCuenta() + " no fue encontrada"));

        return ConsultaSaldoResponse.builder()
                .nroCuenta(cuentaBanco.getNroCuenta())
                .saldo(cuentaBanco.getSaldo())
                .build();
    }


    private void validarMonto(BigDecimal saldo, BigDecimal monto) {
        if (saldo.compareTo(monto) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar retiro");
        }
    }

    private void validarCuenta(CuentaBanco cuenta){
        if (cuenta == null) {
            throw new NullPointerException("Cuenta no existe");
        }
    }


}
