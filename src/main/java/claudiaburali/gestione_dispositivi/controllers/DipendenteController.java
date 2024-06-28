package claudiaburali.gestione_dispositivi.controllers;

import claudiaburali.gestione_dispositivi.entities.Dipendente;
import claudiaburali.gestione_dispositivi.exceptions.BadRequestException;
import claudiaburali.gestione_dispositivi.payloads.DipendenteDTO;
import claudiaburali.gestione_dispositivi.payloads.DipententeResponseDTO;
import claudiaburali.gestione_dispositivi.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

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

    @GetMapping
    public Page<Dipendente> getAllDipendenti(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.dipendenteService.getDipendenti(page, size, sortBy);
    }

    @GetMapping("/{dipendenteId}")
    public Dipendente findById(@PathVariable UUID dipendenteId) {
        return this.dipendenteService.findById(dipendenteId);
    }

    @PutMapping("/{dipendenteId}")
    public Dipendente findByIdAndUpdate(@PathVariable UUID dipendenteId, @RequestBody Dipendente body) {
        return this.dipendenteService.findByIdAndUpdate(dipendenteId, body);
    }

    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID dipendenteId) {
        this.dipendenteService.findByIdAndDelete(dipendenteId);
    }

    @PostMapping("/{dipendenteId}/avatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile image) throws IOException {
        return this.dipendenteService.uploadImage(image);
    }
}
