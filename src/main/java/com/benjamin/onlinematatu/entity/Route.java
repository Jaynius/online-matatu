package com.benjamin.onlinematatu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routeId;
    private String routeName;
    private String origin;
    private String destination;
    private double fare;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "matatu_list")
    private List<Matatu> matatuList;

}
