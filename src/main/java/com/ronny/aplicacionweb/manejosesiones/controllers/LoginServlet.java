package com.ronny.aplicacionweb.manejosesiones.controllers;
/*
DESARROLLADOR: Ronny Bastidas
DESCRIPCION: Servlet para lograr iniciar sesion.
ademas autentica si hay una sesion en caso contrario sera redirigido a login.jsp
si valida usuario y contraseña crea una sesion y redirige a la paguina principal
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
import java.io.PrintWriter;
import java.util.Optional;


@WebServlet({"/login", "/login.html"})    // Este Servlet escucha en dos rutas: /login y /login.html
public class LoginServlet extends HttpServlet {

    // Constantes para el usuario y contraseña de prueba
    final static String USERNAME = "admin";
    final static String PASSWORD = "12345";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. Llama a nuestro "trabajador" de servicio
        LoginService auth = new LoginServiceSessionImpl(); // servicio que lee username de la sesión
        Optional<String> usernameOptional = auth.getUsername(req); // devuelve Optional con el nombre si existe



        if (usernameOptional.isPresent()) { // 2. ya hay sesión activa
            // Si SÍ existe, lo saludamos
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("  <head>");
                out.println("    <meta charset=\"UTF-8\">");
                out.println("    <title>Hola " + usernameOptional.get() + "</title>");
                out.println("  </head>");
                out.println("  <body>");
                out.println("    <h1>Hola " + usernameOptional.get() + ", has iniciado sesión con éxito!</h1>");
                out.println("    <p><a href='" + req.getContextPath() + "/index.html'>Volver</a></p>");
                out.println("    <p><a href='" + req.getContextPath() + "/logout'>Cerrar sesión</a></p>");
                out.println("  </body>");
                out.println("</html>");
            }
        } else {
            // 3. Si NO existe, lo mandamos al formulario login.jsp
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. Obtenemos los datos del formulario
        String username = req.getParameter("user"); // (Asegúrese que su login.jsp use name="user")
        String password = req.getParameter("password"); // (Y name="password")

        // 2. Validamos las credenciales
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {

            // 3. Creamos la sesión
            HttpSession session = req.getSession();
            // 4. Guardamos el "username" en la memoria de sesión
            session.setAttribute("username", username);

            // 5. Redirigimos al usuario (puede ser al index o a productos, en este caso al index)
            resp.sendRedirect(req.getContextPath() + "/index.html");
        } else {
            // 6. Mandamos un error de "No Autorizado"
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario o contraseña incorrectos");
        }
    }
}