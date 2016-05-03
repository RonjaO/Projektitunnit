package app;

import app.domain.Tunti;
import app.domain.Projekti;
import app.repository.TuntiRepository;
import app.repository.ProjektiRepository;
import java.util.*;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;

public class ProjektinKesto {
    
    @Autowired
    public ProjektiRepository projektit;
    
    @Autowired
    public TuntiRepository tunnit;
    
    public String kokonaiskesto(int projektiId) {
        Projekti projekti = projektit.findOne(projektiId);
        List<Tunti> kaikkiTunnit = tunnit.findAllByProjekti(projektiId);
        
        if (kaikkiTunnit.size() == 0) {
            return "00:00";
        }
        
        Duration kesto = null;

        for (Tunti tunti : kaikkiTunnit) {
            kesto = kesto.plus(tunti.getDuration());
        }
        
        projektit.paivitaKesto(projektiId, kesto);
        
        return kesto.toString();
    }

}
