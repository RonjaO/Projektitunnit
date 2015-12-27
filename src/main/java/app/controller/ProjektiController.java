package app.controller;

import app.repository.ProjektiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.lang.*;

@Controller
@RequestMapping("/projektit")
puvlic class ProjektiController {
    
    @Autowired
    private ProjektiRepository projektit;
    
    @RequestMapping(method=RequestMethod.GET)
    public String view(Model model) {
        model.addAttribute("projektit", projektit.findAll());
        
        return "projektit";
    }
    
    
}