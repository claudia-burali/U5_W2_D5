package claudiaburali.gestione_dispositivi.services;

import claudiaburali.gestione_dispositivi.entities.Dipendente;
import claudiaburali.gestione_dispositivi.payloads.DipendenteDTO;
import claudiaburali.gestione_dispositivi.repositories.DipendenteRepository;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DipendenteService {
    @Autowired
    DipendenteRepository dipendenteRepository;
    @Autowired
    private Cloudinary cloudinaryUploader;

    /*public Dipendente saveDipendente(DipendenteDTO dipendenteDto) {

    }*/

}
