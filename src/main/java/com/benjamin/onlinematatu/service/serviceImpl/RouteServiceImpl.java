package com.benjamin.onlinematatu.service.serviceImpl;

import com.benjamin.onlinematatu.DTO.RouteDTO;
import com.benjamin.onlinematatu.entity.Route;
import com.benjamin.onlinematatu.repository.RouteRepo;
import com.benjamin.onlinematatu.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepo routeRepo;

    @Override
    public RouteDTO convertRouteToDTO(Route route) {
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setRouteName(route.getRouteName());
        routeDTO.setOrigin(route.getOrigin());
        routeDTO.setDestination(route.getDestination());
        routeDTO.setMatatuList(route.getMatatuList());
        routeDTO.setFare(route.getFare());

        return routeDTO;
    }

    @Override
    public Route convertToEntity(RouteDTO routeDTO) {
        Route route = new Route();
        route.setRouteName(routeDTO.getRouteName());
        route.setOrigin(routeDTO.getOrigin());
        route.setDestination(routeDTO.getDestination());
        route.setMatatuList(routeDTO.getMatatuList());
        route.setFare(routeDTO.getFare());
        return route;
    }

    @Override
    public List<RouteDTO> getAllRoutes() {
        return routeRepo.findAll()
                .stream()
                .map(this::convertRouteToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RouteDTO getRouteById(int id) {
        Route route = routeRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Route not found"));
        return convertRouteToDTO(route);
    }

    @Override
    public RouteDTO createRoute(RouteDTO routeDTO) {
        Route route = convertToEntity(routeDTO);
        routeRepo.save(route);
        return convertRouteToDTO(route);
    }

    @Override
    public RouteDTO updateRoute(int routeId, RouteDTO routeDTO) {
        Optional<Route> route = routeRepo.findById(routeId);
        if(route.isPresent()){
            Route updatedRoute = route.get();
            updatedRoute.setRouteName(routeDTO.getRouteName());
            updatedRoute.setOrigin(routeDTO.getOrigin());
            updatedRoute.setDestination(routeDTO.getDestination());
            updatedRoute.setMatatuList(routeDTO.getMatatuList());
            routeRepo.save(updatedRoute);
            return convertRouteToDTO(updatedRoute);
        }

        throw new RuntimeException("Route not found");
    }

    @Override
    public void deleteRoute(int routeId) {
        routeRepo.deleteById(routeId);

    }
}
