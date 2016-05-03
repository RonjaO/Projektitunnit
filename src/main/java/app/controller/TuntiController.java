package app.controller;

import app.domain.Tunti;
import app.repository.TuntiRepository;
import app.repository.ProjektiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import java.lang.*;
import java.util.*;
import java.time.Duration;
import javax.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class TuntiController {
    
    @Autowired
    private TuntiRepository tunnit;
    
    @Autowired
    private ProjektiRepository projektit;
    
    @RequestMapping(value="/projektit/raportti/{projektiId}", method=RequestMethod.GET)
    public String projektitunnit(@PathVariable int projektiId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("tunnit", tunnit.findAllByKayttajaAndProjekti(kirjautunut(), projektiId));
        redirectAttributes.addFlashAttribute("projekti", projektit.findOne(projektiId));
        
        return "redirect:/projektit/raportti";
    }
    
    @RequestMapping(value="/projektit/tunti", method=RequestMethod.POST)
    public String aloitaTunti(@RequestParam Integer projektiId, RedirectAttributes redirectAttributes) {
        tunnit.save(projektiId, kirjautunut());        
        return "redirect:/projektit";
    }
    
    @RequestMapping(value="/projektit/tunti/{id}", method=RequestMethod.POST)
    public String lopeta(@ModelAttribute Tunti tunti)  {
        tunnit.loppu(tunti, kirjautunut());
        
        // kokonaiskesto(tunti.getProjektiId());

        return "redirect:/projektit";
    }

    @RequestMapping(value="/tunti/{id}", method=RequestMethod.GET)
    public String naytaMuokkaus(Model model, @PathVariable int id) {
        model.addAttribute("tunti", tunnit.findOne(id));
        return "muokkaa_tuntia";
    }

    @RequestMapping(value="/tunti/{id}", method=RequestMethod.POST) 
    public String muokkaa(@Valid @ModelAttribute Tunti tunti, @PathVariable int id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "muokkaa_tuntia";
        }
        
        tunti.setId(id);
            
        tunnit.update(tunti, kirjautunut());
        
        // kokonaiskesto(tunti.getProjektiId());
            
        return "redirect:/projektit/raportti/" + tunnit.findOne(id).getProjektiId();
    }
    
    @RequestMapping(value="/tunti/{id}", method=RequestMethod.DELETE)
    public String poista(@PathVariable int id) {
        int projektiId = tunnit.findOne(id).getProjektiId();
        tunnit.delete(id, kirjautunut());
        
        // kokonaiskesto(projektiId);
        
        return "redirect:/projektit/raportti/" + projektiId;
    }

    private String kirjautunut() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    
    // private void kokonaiskesto(int projektiId) {
    //     List<Tunti> projektinTunnit = tunnit.findAllByKayttajaAndProjekti(kirjautunut(), projektiId);
    //     Duration kesto = projektinTunnit.get(0).getDuration();
    //
    //     for (int i = 1; i < projektinTunnit.size(); i++) {
    //         kesto.plus(projektinTunnit.get(i).getDuration());
    //     }
    //
    //     projektit.paivitaKesto(kesto, projektiId);
    // }

}
