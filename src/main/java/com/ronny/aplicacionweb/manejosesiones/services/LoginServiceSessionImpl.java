package com.ronny.aplicacionweb.manejosesiones.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

// "implements LoginService" -> "Firmo el contrato LoginService"
public class LoginServiceSessionImpl implements LoginService {


    @Override    // "@Override" -> "Estoy cumpliendo la promesa del contrato"
    public Optional<String> getUsername(HttpServletRequest request) {

        HttpSession session = request.getSession(); // Obtengo la sesión actual (crea si no existe)
        String username = (String) session.getAttribute("username");// leo el atributo guardado en el login

        // 3. Verificamos si existe
        if (username != null) {
            // si hay usuario en sesión, lo regreso
            return Optional.of(username);
        }
        // Si no existe, devolvemos "vacío"
        return Optional.empty();
    }
}