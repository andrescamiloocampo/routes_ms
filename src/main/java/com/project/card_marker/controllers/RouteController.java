package com.project.card_marker.controllers;

import com.project.card_marker.models.Route;
import com.project.card_marker.repositories.RouteRepository;
import com.project.card_marker.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RouteController {
    @Autowired
    RouteService routeService;

    private final RouteRepository routeRepository;


    public RouteController(RouteRepository routeRepository) {
        super();
        this.routeRepository = routeRepository;
    }

    @GetMapping()
    public String index(){
        return "Aqui van las rutas";
    }

    @GetMapping(value = "/concurrent")
    public List<Route> concurrent(){
        return routeService.run();
    }

    @GetMapping(value = "/routes")
    public List<Route> findAll(){
        return this.routeRepository.findAll();
    }

    @PostMapping(value = "/create")
    public String create(@RequestBody Route route){
        return this.routeRepository.save(route).toString();


    }

}
