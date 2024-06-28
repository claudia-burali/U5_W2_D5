package claudiaburali.gestione_dispositivi.services;

import claudiaburali.gestione_dispositivi.entities.Dipendente;
import claudiaburali.gestione_dispositivi.exceptions.BadRequestException;
import claudiaburali.gestione_dispositivi.payloads.DipendenteDTO;
import claudiaburali.gestione_dispositivi.repositories.DipendenteRepository;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepository;
    @Autowired
    private Cloudinary cloudinaryUploader;

    public Dipendente saveDipendente(DipendenteDTO dipendenteDTO) {
    dipendenteRepository.findByEmail(dipendenteDTO.email()).ifPresent(
            dipendente -> {
                throw new BadRequestException("L'indirizzo " + dipendenteDTO.email() + " è già in uso!");
            }
    );
    Dipendente dipendenteForDb = new Dipendente(dipendenteDTO.username(), dipendenteDTO.name(), dipendenteDTO.surname(), dipendenteDTO.email());
    dipendenteForDb.setAvatarURL("https://png.pngtree.com/png-clipart/20191120/original/pngtree-outline-user-icon-png-image_5045523.jpg");
    return dipendenteRepository.save(dipendenteForDb);
    }
}
