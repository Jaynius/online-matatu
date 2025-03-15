package com.benjamin.onlinematatu.service;

import com.benjamin.onlinematatu.DTO.RouteDTO;
import com.benjamin.onlinematatu.entity.Route;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RouteService {
    public RouteDTO convertRouteToDTO(Route route);
    public Route convertToEntity(RouteDTO routeDTO);
    public List<RouteDTO> getAllRoutes();
    public RouteDTO getRouteById(int id);
    public RouteDTO createRoute(RouteDTO routeDTO);
    public RouteDTO updateRoute(int routeId,RouteDTO routeDTO);
    public void deleteRoute(int routeId);

}
