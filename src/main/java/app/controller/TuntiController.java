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
import javax.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class TuntiController {
    
    @Autowired
    private TuntiRepository tunnit;
    
    @Autowired
    private ProjektiRepository projektit;
    
    @RequestMapping(value="/projektit/raportti/{projektiId}", method=RequestMethod.POST)
    public String projektitunnit(@PathVariable int projektiId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("tunnit", tunnit.findAllByKayttajaAndProjekti(kirjautunut(), projektiId));
        redirectAttributes.addFlashAttribute("projekti", projektit.findOne(projektiId));
        
        return "redirect:/projektit/raportti";
    }
    
    @RequestMapping(value="/projektit/tunti", method=RequestMethod.POST)
    public String aloitaTunti(@ModelAttribute Tunti tunti, RedirectAttributes redirectAttributes) {
        tunnit.save(tunti, kirjautunut());
        
        redirectAttributes.addFlashAttribute("tunti", "joopajoo");
        
        return "redirect:/projektit";
    }
    
    private String kirjautunut() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

}