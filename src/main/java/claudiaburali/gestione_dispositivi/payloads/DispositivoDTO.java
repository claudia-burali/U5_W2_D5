package claudiaburali.gestione_dispositivi.payloads;

import jakarta.validation.constraints.NotEmpty;

public record DispositivoDTO(
        @NotEmpty(message = "Campo obbligatorio!")
        String TipoDispositivo,
        @NotEmpty(message = "Campo obbligatorio!")
        String StatoDispositivo
) {

}
