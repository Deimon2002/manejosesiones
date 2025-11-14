package com.ronny.aplicacionweb.manejosesiones.controllers;

import com.ronny.aplicacionweb.manejosesiones.models.DetalleCarro;
import com.ronny.aplicacionweb.manejosesiones.models.ItemCarro;
import com.ronny.aplicacionweb.manejosesiones.models.Producto;
import com.ronny.aplicacionweb.manejosesiones.services.ProductoService;
import com.ronny.aplicacionweb.manejosesiones.services.ProductoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Servlet que maneja la funcionalidad de agregar productos al carrito de compras.
 * Mapea la URL "/agregar-carro" para procesar las solicitudes.
 */
@WebServlet("/agregar-carro")
public class AgregarCarroServlet extends HttpServlet {

    /**
     * Procesa las peticiones GET para agregar un producto al carrito.
     *
     * @param req  Objeto HttpServletRequest con la información de la petición
     * @param resp Objeto HttpServletResponse para enviar la respuesta
     * @throws ServletException Si ocurre un error específico del servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtiene el ID del producto desde los parámetros de la URL
        Long id = Long.parseLong(req.getParameter("id"));

        // Crea una instancia del servicio para consultar productos
        ProductoService service = new ProductoServiceImpl();

        // Busca el producto por su ID en la base de datos
        Optional<Producto> producto = service.porId(id);

        // Verifica si el producto existe
        if (producto.isPresent()) {
            // Crea un nuevo item para el carrito con cantidad inicial de 1
            ItemCarro item = new ItemCarro(1, producto.get());

            // Obtiene la sesión actual del usuario
            HttpSession session = req.getSession();
            DetalleCarro detalleCarro;

            // Verifica si ya existe un carrito en la sesión
            if (session.getAttribute("carro") == null) {
                // Si no existe, crea un nuevo carrito vacío
                detalleCarro = new DetalleCarro();
                // Almacena el nuevo carrito en la sesión
                session.setAttribute("carro", detalleCarro);
            } else {
                // Si ya existe, recupera el carrito existente de la sesión
                detalleCarro = (DetalleCarro) session.getAttribute("carro");
            }

            // Agrega el item al carrito (actualiza cantidad si ya existe)
            detalleCarro.addItemCarro(item);
        }

        // Redirige al usuario a la página para ver el carrito
        resp.sendRedirect(req.getContextPath() + "/ver-carro");
    }
}