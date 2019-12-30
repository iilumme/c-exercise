package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

import java.util.List;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(String name, String address) {
        signupRepository.save(new Signup(name, address));
        return "done";
    }

    @RequestMapping("/admin_signups")
    public String adminListSignups(Model model) {
        List<Signup> s = signupRepository.findAll();

        model.addAttribute("list", s);
        return "admin";
    }

    @RequestMapping(value = "/admin_signups/{id}", method = RequestMethod.POST)
    public String remove(@PathVariable Long id) {
        signupRepository.delete(id);
        return "redirect:/admin_signups";
    }

    @RequestMapping(value = "/admin_signups", method = RequestMethod.POST)
    public String adminForm(String name, String password) {
        if (name.equals("admin") && password.equals("1234")) {
            return  "redirect:/admin_signups";
        } else {
            return "form";
        }
    }

}