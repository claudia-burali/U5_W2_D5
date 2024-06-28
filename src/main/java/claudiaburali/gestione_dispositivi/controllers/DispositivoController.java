package claudiaburali.gestione_dispositivi.controllers;

import claudiaburali.gestione_dispositivi.entities.Dispositivo;
import claudiaburali.gestione_dispositivi.exceptions.BadRequestException;
import claudiaburali.gestione_dispositivi.payloads.DispositivoDTO;
import claudiaburali.gestione_dispositivi.payloads.DispositivoResponseDTO;
import claudiaburali.gestione_dispositivi.services.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dispositivi")
public class DispositivoController {
    @Autowired
    private DispositivoService dispositivoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DispositivoResponseDTO dispositivoResponseDTO (@RequestBody @Validated DispositivoDTO dispositivoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return new DispositivoResponseDTO(dispositivoService.saveDispositivo(dispositivoDTO).getId());
    }

    @GetMapping
    public Page<Dispositivo> getAllDispositivi(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.dispositivoService.getDispositivi(page, size, sortBy);
    }

    @GetMapping("/{dispositivoId}")
    public Dispositivo findById(@PathVariable UUID dispositivoId) {
        return this.dispositivoService.findById(dispositivoId);
    }

    @PutMapping("/{dispositivoId}")
    public Dispositivo findByIdAndUpdate(@PathVariable UUID dispositivoId, @RequestBody Dispositivo body, @PathVariable UUID dipendenteId) {
        return this.dispositivoService.findByIdAndUpdate(dispositivoId, body, dipendenteId);
    }

    @DeleteMapping("/{dispositivoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID dispositivoId) {
        this.dispositivoService.findByIdAndDelete(dispositivoId);
    }

}
