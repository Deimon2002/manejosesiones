package com.ronny.aplicacionweb.manejosesiones.controllers;

/*
DESARROLLADOR: Ronny Bastidas
DESCRIPCION: Servlet para cerrar sesión.
Si hay usuario logueado, invalida la HttpSession y luego redirige al login.
*/

import com.ronny.aplicacionweb.manejosesiones.services.LoginService;
import com.ronny.aplicacionweb.manejosesiones.services.LoginServiceSessionImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

// Este Servlet escucha en la ruta /logout
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Llama a nuestro "trabajador" de servicio
        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> username = auth.getUsername(req);

        // ¿Realmente hay una sesión que cerrar?
        if (username.isPresent()) {
            // Si SÍ, obtenemos la sesión...
            HttpSession session = req.getSession();
            // La invalidamos (Esto borra el "username" y todo lo demás)
            session.invalidate();
        }
        // Mandamos al usuario de vuelta al login
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}