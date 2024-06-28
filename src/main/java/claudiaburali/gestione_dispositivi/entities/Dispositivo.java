package claudiaburali.gestione_dispositivi.entities;
import claudiaburali.gestione_dispositivi.enums.StatoDispositivo;
import claudiaburali.gestione_dispositivi.enums.TipoDispositivo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "dispositivi")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Dispositivo {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private TipoDispositivo tipoDispositivo;
    @Enumerated(EnumType.STRING)
    private StatoDispositivo statoDispositivo;
    @ManyToOne
    @JoinColumn (name = "dipendente_id")
    private Dipendente dipendente;

    public Dispositivo(TipoDispositivo tipoDispositivo, StatoDispositivo statoDispositivo) {
        this.tipoDispositivo = tipoDispositivo;
        this.statoDispositivo = statoDispositivo;
    }
}
