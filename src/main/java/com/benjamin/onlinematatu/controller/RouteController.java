package com.benjamin.onlinematatu.controller;

import com.benjamin.onlinematatu.DTO.RouteDTO;
import com.benjamin.onlinematatu.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/route")
@RequiredArgsConstructor
public class RouteController {


    private final RouteService routeService;

    @PostMapping
    public ResponseEntity<RouteDTO> newRoute(@RequestBody RouteDTO routeDTO){
        RouteDTO route=routeService.createRoute(routeDTO);
        return new ResponseEntity<>(route, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RouteDTO>> getAllRoutes(){
        List<RouteDTO> routeList=routeService.getAllRoutes();
        return new ResponseEntity<>(routeList, HttpStatus.OK);
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<RouteDTO> getRouteById(@PathVariable Integer routeId){
        RouteDTO route=routeService.getRouteById(routeId);
        return new ResponseEntity<>(route, HttpStatus.OK);
    }

    @PostMapping("/{routeId}")
    public ResponseEntity<RouteDTO> updateRoute(@PathVariable Integer routeId,@RequestBody RouteDTO routeDTO){
        RouteDTO route=routeService.updateRoute(routeId,routeDTO);
        return new ResponseEntity<>(route, HttpStatus.OK);
    }

    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Integer routeId){
        routeService.deleteRoute(routeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
