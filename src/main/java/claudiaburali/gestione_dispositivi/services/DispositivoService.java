package claudiaburali.gestione_dispositivi.services;

import claudiaburali.gestione_dispositivi.entities.Dipendente;
import claudiaburali.gestione_dispositivi.entities.Dispositivo;
import claudiaburali.gestione_dispositivi.enums.StatoDispositivo;
import claudiaburali.gestione_dispositivi.enums.TipoDispositivo;
import claudiaburali.gestione_dispositivi.exceptions.BadRequestException;
import claudiaburali.gestione_dispositivi.exceptions.NotFoundException;
import claudiaburali.gestione_dispositivi.payloads.DispositivoDTO;
import claudiaburali.gestione_dispositivi.repositories.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DispositivoService {
    @Autowired
    DispositivoRepository dispositivoRepository;
    @Autowired
    DipendenteService dipendenteService;
    public TipoDispositivo tipoDispositivo (DispositivoDTO dispositivoDTO){
        try {
            return TipoDispositivo.valueOf(dispositivoDTO.TipoDispositivo().toUpperCase());
        }catch (IllegalArgumentException ex){
            throw new BadRequestException("Il tipo del dispositivo deve essere SMARTPHONE, TABLET O LAPTOP!");
        }
    }
    public StatoDispositivo statoDispositivo (DispositivoDTO dispositivoDTO){
        try {
            return StatoDispositivo.valueOf(dispositivoDTO.StatoDispositivo().toUpperCase());
        }catch (IllegalArgumentException ex){
            throw new BadRequestException("Lo stato del dispositivo deve essere DISPONIBILE, ASSEGNATO, IN_MANUTENZIONE o DISMESSO!");
        }
    }
    public Dispositivo saveDispositivo(DispositivoDTO dispositivoDTO) {
            Dispositivo newDispositivo = new Dispositivo(tipoDispositivo(dispositivoDTO), statoDispositivo(dispositivoDTO));
            return dispositivoRepository.save(newDispositivo);
    }
    public Page<Dispositivo> getDispositivi (int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return dispositivoRepository.findAll(pageable);
    }

    public Dispositivo findById(UUID dispositivoId) {
        return this.dispositivoRepository.findById(dispositivoId).orElseThrow(() -> new NotFoundException(dispositivoId));
    }

    public Dispositivo findByIdAndUpdate(UUID dispositivoId, Dispositivo modifiedDispositivo, UUID dipendenteId) {
        Dispositivo found = this.findById(dispositivoId);
        Dipendente dipendenteFound = dipendenteService.findById(dipendenteId);
        found.setStatoDispositivo(modifiedDispositivo.getStatoDispositivo());
        found.setTipoDispositivo(modifiedDispositivo.getTipoDispositivo());
        found.setDipendente(dipendenteFound);
        return this.dispositivoRepository.save(found);
    }

    public void findByIdAndDelete(UUID dispositivoId) {
        Dispositivo found = this.findById(dispositivoId);
        this.dispositivoRepository.delete(found);
    }
}
