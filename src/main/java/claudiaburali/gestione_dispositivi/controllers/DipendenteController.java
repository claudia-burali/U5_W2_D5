package claudiaburali.gestione_dispositivi.controllers;

import claudiaburali.gestione_dispositivi.exceptions.BadRequestException;
import claudiaburali.gestione_dispositivi.payloads.DipendenteDTO;
import claudiaburali.gestione_dispositivi.payloads.DipententeResponseDTO;
import claudiaburali.gestione_dispositivi.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {
    @Autowired
    private DipendenteService dipendenteService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DipententeResponseDTO dipententeResponseDTO (@RequestBody @Validated DipendenteDTO dipendenteDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return new DipententeResponseDTO(dipendenteService.saveDipendente(dipendenteDTO).getId());
    }
}
