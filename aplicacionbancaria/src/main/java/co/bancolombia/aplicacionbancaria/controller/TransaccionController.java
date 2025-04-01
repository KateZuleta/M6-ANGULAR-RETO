package co.bancolombia.aplicacionbancaria.controller;

import co.bancolombia.aplicacionbancaria.modelo.DTO.CompraDTO;
import co.bancolombia.aplicacionbancaria.modelo.DTO.DepositoDTO;
import co.bancolombia.aplicacionbancaria.modelo.DTO.TransaccionDTO;
import co.bancolombia.aplicacionbancaria.modelo.Transaccion;
import co.bancolombia.aplicacionbancaria.modelo.response.CompraResponse;
import co.bancolombia.aplicacionbancaria.modelo.response.DepositoResponse;
import co.bancolombia.aplicacionbancaria.modelo.response.RetiroResponse;
import co.bancolombia.aplicacionbancaria.service.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/transaccion")
public class TransaccionController {
    private final TransaccionService transaccionService;

    @PostMapping("/deposito")
    public DepositoResponse depositar(@Valid @RequestBody DepositoDTO depositoDTO) {
        return transaccionService.depositar(depositoDTO);
    }

    @PostMapping("/retiro")
    public RetiroResponse retirar(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        return transaccionService.retirar(transaccionDTO);
    }

    @PostMapping("/compra")
    public CompraResponse compra(@Valid @RequestBody CompraDTO compraDTO) {
        return transaccionService.Compra(compraDTO);
    }

    // Cambié a @GetMapping para que sea una consulta
    @GetMapping("/{cuenta}")
    public ResponseEntity<List<Transaccion>> obtenerTransacciones(
            @PathVariable("cuenta") String cuenta) {

        List<Transaccion> transacciones = transaccionService.Transacciones(cuenta);
        return ResponseEntity.ok(transacciones);
    }
}
