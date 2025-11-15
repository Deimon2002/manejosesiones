package com.ronny.aplicacionweb.manejosesiones.controllers;
/*
DESARROLLADOR: Ronny Bastidas
DESCRIPCION: Servlet que muestra la tabla de nuestros productos
Si hay un usuario logueado es saludado y mostrarar la columna de precios
en el caso contrario solo vera la info basica de id nombre y tipo de producto
*/

import com.ronny.aplicacionweb.manejosesiones.models.Producto;
import com.ronny.aplicacionweb.manejosesiones.services.LoginService;
import com.ronny.aplicacionweb.manejosesiones.services.LoginServiceSessionImpl;
import com.ronny.aplicacionweb.manejosesiones.services.ProductoService;
import com.ronny.aplicacionweb.manejosesiones.services.ProductoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import static java.lang.System.out;

@WebServlet({"/productos", "/productos.html"})
public class ProductoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ProductoService service = new ProductoServiceImpl();
        List<Producto> productos = service.listar();

        // Obtener información de sesión
        HttpSession session = req.getSession();
        Optional<String> usernameOptional = Optional.ofNullable(
                (String) session.getAttribute("username"));

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>Listado de Productos</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Listado de Productos!</h1>");

            // Inicio de la tabla
            out.println("<table border='1'>");
            out.println("<tr>");
            out.println("<th>id</th>");
            out.println("<th>nombre</th>");
            out.println("<th>tipo</th>");
            if (usernameOptional.isPresent()) {
                out.println("<th>precio</th>");
                out.println("<th>precio con IVA</th>");  // Nueva columna
                out.println("<th>agregar</th>");
            }
            out.println("</tr>");

            // BUCLE para llenar la tabla
            for (Producto p : productos) {
                out.println("<tr>");
                out.println("<td>" + p.getId() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getTipo() + "</td>");

                if (usernameOptional.isPresent()) {
                    out.println("<td>$" + p.getPrecio() + "</td>");
                    out.println("<td>$" + String.format("%.2f", p.getPrecio() * 1.12) + "</td>"); // Precio con IVA formateado
                    out.println("<td><a href=\""
                            + req.getContextPath()
                            + "/agregar-carro?id="
                            + p.getId()
                            + "\">Agregar al carro</a></td>");
                }
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}