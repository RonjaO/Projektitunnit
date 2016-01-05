package app.controller;

import app.domain.Kayttaja;
import app.repository.KayttajaRepository;
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

@Controller
@RequestMapping("/signup")
public class KayttajaController {
    
    @Autowired
    private KayttajaRepository kayttajat;
    
    @RequestMapping(method=RequestMethod.GET)
    public String view(@ModelAttribute Kayttaja kayttaja) {
        return "signup";
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String signup(@Valid @ModelAttribute Kayttaja kayttaja, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        
        kayttajat.save(kayttaja);
        
        return "redirect:/login";
    }

}
