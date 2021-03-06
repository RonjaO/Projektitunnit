package app.controller;

import app.repository.ProjektiRepository;
import app.repository.TuntiRepository;
import app.domain.Projekti;
import app.domain.Tunti;
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
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import app.domain.Kayttaja; 
import java.util.*;
import java.time.Duration;


@Controller
@RequestMapping("/projektit")
public class ProjektiController {
    
    @Autowired
    private ProjektiRepository projektit;
    
    @Autowired
    private TuntiRepository tunnit;
    
    @RequestMapping(method=RequestMethod.GET)
    public String view(Model model) {
        if (!(tunnit.kesken().isEmpty())) {
            List<Tunti> tunti = tunnit.kesken();
            System.out.println("tunti: " + tunti.get(tunti.size() - 1).getId());
            model.addAttribute("tunti", tunti.get(0));

            int projekti = tunnit.kesken().get(0).getProjektiId();
            model.addAttribute("projekti", projektit.findOne(projekti));

            return "projektit";
        }
                

        model.addAttribute("projektit", laskeKestot());
        

        model.addAttribute("kayttaja", kirjautunut());
        
        return "projektit";
    }
    
    @RequestMapping(value="/uusi_projekti", method=RequestMethod.POST)
    public String create(@Valid @ModelAttribute Projekti projekti, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "uusi_projekti";
        }

        projektit.save(projekti, kirjautunut());
        
        return "redirect:/projektit";
    }
    
    @RequestMapping(value="/uusi_projekti", method=RequestMethod.GET)
    public String uusiProjekti(@ModelAttribute Projekti projekti) {
        return "uusi_projekti";
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/kaikki")
    public String muokkaaProjekteja(Model model) {
        model.addAttribute("projektit", laskeKestot());
        
        return "kaikki_projektit";
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String delete(@PathVariable int id) {
        projektit.delete(id);
        
        return "redirect:/projektit/kaikki";
    }
    
    @RequestMapping(value="/{id}/muokkaa", method=RequestMethod.GET)
    public String muokkaa(@PathVariable int id, Model model) {
        model.addAttribute("projekti", projektit.findOne(id));
        
        return "muokkaa_projektia";
    }
    
    @RequestMapping(value="/{id}/muokkaa", method=RequestMethod.POST)
    public String update(@PathVariable int id, @Valid @ModelAttribute Projekti projekti, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "muokkaa_projektia";
        }
        projektit.update(projekti, id);
        
        return "redirect:/projektit/kaikki";
    }
    
    @RequestMapping(value="/raportti", method=RequestMethod.GET)
    public String raportti(Model model) {
        model.addAttribute("projektit", laskeKestot());
        
        return "raportti";
    }
    
    private String kirjautunut() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    
    private List<Projekti> laskeKestot() {
        List<Projekti> kaikkiprojektit = projektit.findAllByUser(kirjautunut());

        if (kaikkiprojektit.isEmpty()) {
            return kaikkiprojektit;
        }
        
        for (Projekti projekti : kaikkiprojektit) {
            kokonaiskesto(projekti);
        }
        
        return kaikkiprojektit;
    }
    
    private void kokonaiskesto(Projekti projekti) {
        List<Tunti> kaikkitunnit = tunnit.findAllByKayttajaAndProjekti(kirjautunut(), projekti.getId());
        if (kaikkitunnit.isEmpty()) {
            return;
        }
        
        Duration kesto = Duration.ZERO;
        
        for (Tunti tunti : kaikkitunnit) {
            if(tunti.getAlkuaika() != null || tunti.getLoppuaika() != null) {
                kesto = kesto.plus(Duration.between(tunti.getAlkuaika(), tunti.getLoppuaika()));
            }
        }
        
        System.out.println("Kesto on " + kesto.toString());
        
        long min = kesto.toMinutes();
        long h = 0;
        
        while (true) {
            if (min >= 60) {
                h++;
                min = min - 60;
            } else {
                break;
            }
        }
        
        String parsittuKesto = h + ":" + min;
        projekti.setKesto(parsittuKesto);
    }
    
}
