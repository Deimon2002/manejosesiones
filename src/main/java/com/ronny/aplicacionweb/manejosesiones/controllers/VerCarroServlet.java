package com.ronny.aplicacionweb.manejosesiones.controllers;
/**
 * DESCRIPCION:Servlet que maneja la visualización del carrito de compras.
 * Mapea la URL "/ver-carro" para mostrar el contenido del carrito al usuario.
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ver-carro")
public class VerCarroServlet extends HttpServlet {

    /**
     * Procesa las peticiones GET para mostrar la página del carrito.
     * Redirige la petición al JSP que renderiza la vista del carrito.
     * @param req  Objeto HttpServletRequest con la información de la petición
     * @param resp Objeto HttpServletResponse para enviar la respuesta
     * @throws ServletException Si ocurre un error específico del servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Obtiene el despachador de peticiones y redirige a la página JSP del carrito
        // El método forward() mantiene la petición y respuesta originales
        // permitiendo que el JSP acceda a los atributos de sesión (como el carrito)
        getServletContext().getRequestDispatcher("/carro.jsp").forward(req, resp);
    }
}