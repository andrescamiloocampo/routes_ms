package com.project.card_marker.services;

import com.project.card_marker.models.Route;
import com.project.card_marker.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Tarea implements Runnable {
    private final Route route;
    private final Queue<Route> processedRoutes;

    Tarea(Route route, Queue<Route> processedRoutes) {
        this.route = route;
        this.processedRoutes = processedRoutes;
    }

    @Override
    public void run() {
        System.out.println("Procesando la ruta: " + route.getZoneName());
        processedRoutes.add(route);
    }
}

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;

    private ExecutorService executor = Executors.newFixedThreadPool(2);

    public List<Route> run() {
        if (executor.isShutdown() || executor.isTerminated()) {
            executor = Executors.newFixedThreadPool(2);
        }

        List<Route> routes = routeRepository.findAll();
        Queue<Route> processedRoutes = new ConcurrentLinkedQueue<>();

        for (Route route : routes) {
            executor.execute(new Tarea(route, processedRoutes));
        }

        executor.shutdown();
        while (!executor.isTerminated());
        return List.copyOf(processedRoutes);
    }

    public void shutdownExecutor() {
        executor.shutdown();
    }
}
