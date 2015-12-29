package app.controller;

import app.repository.ProjektiRepository;
import app.domain.Projekti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import java.lang.*;

@Controller
public class RyhmaController {
    
    @RequestMapping(value="/uusi_ryhma", method=RequestMethod.GET)
    public String view(Model model) {
        return "uusi_ryhma";
    }

}