package com.ronny.aplicacionweb.manejosesiones.services;
/*
DESARROLLADOR: Ronny Bastidas
DESCRIPCION: en esta clase se crea el servicio que lee el username guardado en nuestro HttpSession
*/
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

public class LoginServiceSessionImpl implements LoginService {

    @Override
    public Optional<String> getUsername(HttpServletRequest request) {

        HttpSession session = request.getSession(); // Obtengo la sesión actual (crea si no existe)
        String username = (String) session.getAttribute("username");// lee el atributo guardado en el login

        //  Verificamos si existe
        if (username != null) {
            // si hay usuario en sesión, lo regreso
            return Optional.of(username);
        }
        // Si no existe, devolvemos "vacío"
        return Optional.empty();
    }
}