package com.ronny.aplicacionweb.manejosesiones.controllers;


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

        // 1. Llama a nuestro "trabajador" de servicio
        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> username = auth.getUsername(req);

        // 2. ¿Realmente hay una sesión que cerrar?
        if (username.isPresent()) {
            // 3. Si SÍ, obtenemos la sesión...
            HttpSession session = req.getSession();
            // 4.La invalidamos (Esto borra el "username" y todo lo demás)
            session.invalidate();
        }
        // 5. Mandamos al usuario de vuelta al login
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}