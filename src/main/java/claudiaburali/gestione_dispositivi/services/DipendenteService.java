package claudiaburali.gestione_dispositivi.services;

import claudiaburali.gestione_dispositivi.entities.Dipendente;
import claudiaburali.gestione_dispositivi.exceptions.BadRequestException;
import claudiaburali.gestione_dispositivi.exceptions.NotFoundException;
import claudiaburali.gestione_dispositivi.payloads.DipendenteDTO;
import claudiaburali.gestione_dispositivi.repositories.DipendenteRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

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

    public Page<Dipendente> getDipendenti(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return dipendenteRepository.findAll(pageable);
    }

    public Dipendente findById(UUID dipendenteId) {
        return this.dipendenteRepository.findById(dipendenteId).orElseThrow(() -> new NotFoundException(dipendenteId));
    }

    public Dipendente findByIdAndUpdate(UUID dipendenteId, Dipendente modifiedDipendente) {
        Dipendente found = this.findById(dipendenteId);
        found.setUsername(modifiedDipendente.getUsername());
        found.setName(modifiedDipendente.getName());
        found.setSurname(modifiedDipendente.getSurname());
        found.setEmail(modifiedDipendente.getEmail());
        found.setAvatarURL("https://png.pngtree.com/png-clipart/20191120/original/pngtree-outline-user-icon-png-image_5045523.jpg");
        return this.dipendenteRepository.save(found);
    }

    public void findByIdAndDelete(UUID dipendenteId) {
        Dipendente found = this.findById(dipendenteId);
        this.dipendenteRepository.delete(found);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        return (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    }
}
