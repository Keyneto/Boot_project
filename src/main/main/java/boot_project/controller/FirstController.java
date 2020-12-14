package boot_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class FirstController {

    @GetMapping("/main_page")
    public String mainPage() {
        return "index";
    }
}