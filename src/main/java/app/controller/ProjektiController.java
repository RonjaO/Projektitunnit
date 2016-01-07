package app.controller;

import app.repository.ProjektiRepository;
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


@Controller
@RequestMapping("/projektit")
public class ProjektiController {
    
    @Autowired
    private ProjektiRepository projektit;
    
    @RequestMapping(method=RequestMethod.GET)
    public String view(Model model, @ModelAttribute Tunti tunti) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nimi = auth.getName();
        model.addAttribute("projektit", projektit.findAllByUser(nimi));
        

        model.addAttribute("kayttaja", nimi);
        
        return "projektit";
    }
    
    @RequestMapping(value="/uusi_projekti", method=RequestMethod.POST)
    public String create(@Valid @ModelAttribute Projekti projekti, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "uusi_projekti";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nimi = auth.getName();
        projektit.save(projekti, nimi);
        
        return "redirect:/projektit";
    }
    
    @RequestMapping(value="/uusi_projekti", method=RequestMethod.GET)
    public String uusiProjekti(@ModelAttribute Projekti projekti) {
        return "uusi_projekti";
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/kaikki")
    public String muokkaaProjekteja(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nimi = auth.getName();
        model.addAttribute("projektit", projektit.findAllByUser(nimi));
        
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nimi = auth.getName();
        model.addAttribute("projektit", projektit.findAllByUser(nimi));
        
        return "raportti";
    }
    
}