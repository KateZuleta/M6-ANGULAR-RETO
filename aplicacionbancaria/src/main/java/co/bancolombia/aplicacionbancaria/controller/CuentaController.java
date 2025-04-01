package co.bancolombia.aplicacionbancaria.controller;

import co.bancolombia.aplicacionbancaria.modelo.DTO.ConsultaSaldoDTO;
import co.bancolombia.aplicacionbancaria.modelo.DTO.TransaccionDTO;
import co.bancolombia.aplicacionbancaria.modelo.response.ConsultaSaldoResponse;
import co.bancolombia.aplicacionbancaria.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cuenta")
public class CuentaController {
    private final CuentaService cuentaService;

    @PostMapping("/saldo")
    public ConsultaSaldoResponse consultarSaldo(@Valid @RequestBody ConsultaSaldoDTO consultaSaldoDTO) {
        return cuentaService.consultarSaldo(consultaSaldoDTO);
    }
}
