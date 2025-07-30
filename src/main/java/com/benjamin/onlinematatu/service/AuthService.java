package com.benjamin.onlinematatu.service;

import com.benjamin.onlinematatu.DTO.AuthRequestDTO;
import com.benjamin.onlinematatu.DTO.AuthResponseDTO;
import com.benjamin.onlinematatu.DTO.RegisterRequestDTO;
import com.benjamin.onlinematatu.entity.Passenger;
import com.benjamin.onlinematatu.entity.User;
import com.benjamin.onlinematatu.repository.UserRepository;
import com.benjamin.onlinematatu.repository.PassengerRepo;
import com.benjamin.onlinematatu.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PassengerRepo passengerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthResponseDTO(null, null, null, "Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.PASSENGER);
        user.setEnabled(true);

        Passenger passenger = new Passenger();
        passenger.setPassengerName(request.getPassengerName());
        passenger.setPassengerEmail(request.getPassengerEmail());
        passenger.setPassengerPhone(request.getPassengerPhone());
        passenger.setUser(user);

        user.setPassenger(passenger);

        userRepository.save(user);
        passengerRepository.save(passenger);

        String token = jwtUtil.generateToken(user);
        return new AuthResponseDTO(token, user.getUsername(), user.getRole().name(), "Registration successful");
    }

    public AuthResponseDTO login(AuthRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtUtil.generateToken(user);
            return new AuthResponseDTO(token, user.getUsername(), user.getRole().name(), "Login successful");
        } catch (Exception e) {
            return new AuthResponseDTO(null, null, null, "Invalid username or password");
        }
    }

    public void createAdminUser() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            admin.setEnabled(true);
            userRepository.save(admin);
        }
    }
} 