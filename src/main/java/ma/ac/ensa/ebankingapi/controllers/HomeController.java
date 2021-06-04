package ma.ac.ensa.ebankingapi.controllers;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @GetMapping
    public Map<String, String> itWorks() {
        Map<String, String> map = new HashMap<>();
        map.put("messgae", "it workds!");
        return map;
    }
}
