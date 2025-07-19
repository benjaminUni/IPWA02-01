package de.iubh.webanwendungen.controller;

import java.util.List;
import de.iubh.webanwendungen.model.GhostNet;
import de.iubh.webanwendungen.model.Status;
import de.iubh.webanwendungen.model.Person;
import de.iubh.webanwendungen.repository.GhostNetRepository;
import de.iubh.webanwendungen.repository.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nets")
public class GhostNetController {

    @Autowired
    private GhostNetRepository ghostNetRepository;
    
    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/report")
    public String showReportForm(Model model) {
        model.addAttribute("ghostNet", new GhostNet());
        return "report";
    }
    
    @GetMapping("/list")
    public String showNetList(Model model) {
        model.addAttribute("nets", ghostNetRepository.findByStatus(Status.GEMELDET));
        return "list";
    }
    
    @GetMapping("/assigned")
    public String showAssignedNets(Model model) {
        model.addAttribute("nets", ghostNetRepository.findByStatus(Status.BERGUNG_BEVORSTEHEND));
        return "assigned";
    }
    
    @GetMapping("/assign/{id}")
    public String showAssignForm(@PathVariable Long id, Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("netId", id);
        return "assign";
    }
    
    @GetMapping("/map")
    public String showMap(Model model) {
        model.addAttribute("nets", ghostNetRepository.findByStatusNot(Status.GEBORGEN));
        model.addAttribute("pageTitel", "Geisternetze auf der Karte");
        return "map";
    }
    
    @GetMapping("/assignments")
    public String showAssignments(Model model) {
        model.addAttribute("nets", ghostNetRepository.findAll());
        return "assignments";
    }
    
    @GetMapping("/nets/assignments")
    public String showAllAssignments(Model model) {
        List<GhostNet> nets = ghostNetRepository.findAll();
        model.addAttribute("nets", nets);
        return "assignments";
    }
    
    @GetMapping("/report-missing/{id}")
    public String showReportMissingForm(@PathVariable Long id, Model model) {
        model.addAttribute("netId", id);
        model.addAttribute("person", new Person());
        return "report-missing";
    }
    
    @PostMapping("/report")
    public String submitReport(@ModelAttribute GhostNet ghostNet) {
        ghostNet.setStatus(Status.GEMELDET);
        ghostNetRepository.save(ghostNet);
        return "redirect:/nets/report";
    }

    @PostMapping("/assign/{id}")
    public String assignBergung(@PathVariable Long id, @Valid @ModelAttribute Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("netId", id);
            return "assign";
        }

        person.setAnonymous(false);
        personRepository.save(person);

        GhostNet net = ghostNetRepository.findById(id).orElseThrow();
        net.setSalvagingPerson(person);
        net.setStatus(Status.BERGUNG_BEVORSTEHEND);
        ghostNetRepository.save(net);

        return "redirect:/nets/list";
    }
    
    @PostMapping("/mark-recovered/{id}")
    public String markAsRecovered(@PathVariable Long id) {
        GhostNet net = ghostNetRepository.findById(id).orElseThrow();
        net.setStatus(Status.GEBORGEN);
        ghostNetRepository.save(net);
        return "redirect:/nets/assigned";
    }
        

    @PostMapping("/report-missing/{id}")
    public String submitReportMissing(@PathVariable Long id,
                                      @Valid @ModelAttribute Person person,
                                      BindingResult result,
                                      @RequestParam(required = false) boolean confirm,
                                      Model model) {
        if (!confirm) {
            result.reject("confirm", "Du musst bestätigen, dass das Netz verschollen ist.");
        }

        if (result.hasErrors()) {
            model.addAttribute("netId", id);
            return "report-missing";
        }

        person.setAnonymous(false);
        personRepository.save(person);

        GhostNet net = ghostNetRepository.findById(id).orElseThrow();
        net.setSalvagingPerson(person);
        net.setStatus(Status.VERSCHOLLEN);
        ghostNetRepository.save(net);

        return "redirect:/nets/list";
    }
}