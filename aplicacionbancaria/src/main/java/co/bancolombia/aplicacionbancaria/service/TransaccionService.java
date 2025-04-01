package co.bancolombia.aplicacionbancaria.service;

import co.bancolombia.aplicacionbancaria.modelo.CuentaBanco;
import co.bancolombia.aplicacionbancaria.modelo.DTO.CompraDTO;
import co.bancolombia.aplicacionbancaria.modelo.DTO.DepositoDTO;
import co.bancolombia.aplicacionbancaria.modelo.DTO.TransaccionDTO;
import co.bancolombia.aplicacionbancaria.modelo.Transaccion;
import co.bancolombia.aplicacionbancaria.modelo.response.CompraResponse;
import co.bancolombia.aplicacionbancaria.modelo.response.DepositoResponse;
import co.bancolombia.aplicacionbancaria.modelo.response.RetiroResponse;
import co.bancolombia.aplicacionbancaria.repository.CuentaRepository;
import co.bancolombia.aplicacionbancaria.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class TransaccionService {

    private final CuentaRepository cuentaRepository;
    private final TransaccionRepository transaccionRepository;

    public DepositoResponse depositar(DepositoDTO depositoDTO) {

        CuentaBanco cuenta = cuentaRepository.findByNroCuenta(depositoDTO.getIdCuenta())
                .orElseThrow(() -> new NoSuchElementException("La cuenta con ID " + depositoDTO.getIdCuenta() + " no fue encontrada"));

        cuenta.Deposito(depositoDTO.getMonto(), depositoDTO.getTipoDeposito());
        cuentaRepository.save(cuenta);

        Transaccion transaccion = buildtransaccion(depositoDTO, "Deposito", cuenta, depositoDTO.getTipoDeposito().name());

        transaccionRepository.save(transaccion);

        return DepositoResponse.builder()
                .saldoCuenta(cuenta.getSaldo())
                .valor(transaccion.getValor())
                .cuentaId(cuenta.getId())
                .subTipoTransaccion(transaccion.getSubTipoTransaccion())
                .tipoTransaccion(transaccion.getTipoTransaccion())
                .build();
    }

    public CompraResponse Compra(CompraDTO compraDTO) {

        CuentaBanco cuenta = cuentaRepository.findByNroCuenta(compraDTO.getIdCuenta())
                .orElseThrow(() -> new NoSuchElementException("La cuenta con ID " + compraDTO.getIdCuenta() + " no fue encontrada"));

        cuenta.Compra(compraDTO.getMonto(), compraDTO.getTipoCompra());
        cuentaRepository.save(cuenta);

        Transaccion transaccion = buildtransaccion(compraDTO, "Compra", cuenta, compraDTO.getTipoCompra().name());

        transaccionRepository.save(transaccion);

        return CompraResponse.builder()
                .saldoCuenta(cuenta.getSaldo())
                .valor(transaccion.getValor())
                .cuentaId(cuenta.getId())
                .subTipoTransaccion(transaccion.getSubTipoTransaccion())
                .tipoTransaccion(transaccion.getTipoTransaccion())
                .build();

    }

    public List<Transaccion> Transacciones(String cuenta) {

        CuentaBanco cuentaB = cuentaRepository.findByNroCuenta(cuenta)
                .orElseThrow(() -> new NoSuchElementException("La cuenta con ID " + cuenta + " no fue encontrada"));

        return transaccionRepository.findTop5ByCuentaAsociada_IdOrderByTimestampDesc(cuentaB.getId());
    }

    private Transaccion buildtransaccion(TransaccionDTO transaccionDTO, String Tipo, CuentaBanco cuenta, String tipoDeposito) {
        return Transaccion.builder()
                .tipoTransaccion(Tipo)
                .cuentaAsociada(cuenta)
                .subTipoTransaccion(tipoDeposito)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .valor(transaccionDTO.getMonto())
                .build();
    }

    public RetiroResponse retirar(TransaccionDTO transaccionDTO) {
        CuentaBanco cuenta = cuentaRepository.findByNroCuenta(transaccionDTO.getIdCuenta())
                .orElseThrow(() -> new NoSuchElementException("La cuenta con ID " + transaccionDTO.getIdCuenta() + " no fue encontrada"));

        validarMonto(cuenta.getSaldo(), transaccionDTO.getMonto());
        cuenta.setSaldo(cuenta.getSaldo().subtract(transaccionDTO.getMonto()));
        cuentaRepository.save(cuenta);

        Transaccion transaccion = buildtransaccion(transaccionDTO, "Retiro", cuenta, "Retiro");

        transaccionRepository.save(transaccion);

        return RetiroResponse.builder()
                .saldoCuenta(cuenta.getSaldo())
                .valor(transaccion.getValor())
                .cuentaId(cuenta.getId())
                .tipoTransaccion(transaccion.getTipoTransaccion())
                .build();
    }

    private void validarMonto(BigDecimal saldo, BigDecimal monto) {
        if (saldo.compareTo(monto) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar retiro");
        }
    }
}
