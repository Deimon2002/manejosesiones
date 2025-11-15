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

            // CDN Bootstrap
            out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">");

            out.println("</head>");
            out.println("<body class='bg-light'>");

            out.println("<div class='container mt-5'>");
            out.println("<h1 class='text-center mb-4'>Listado de Productos</h1>");

            // Inicio de tabla con Bootstrap
            out.println("<table class='table table-striped table-bordered'>");
            out.println("<thead class='table-dark'>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Nombre</th>");
            out.println("<th>Tipo</th>");

            if (usernameOptional.isPresent()) {
                out.println("<th>Precio</th>");
                out.println("<th>Precio con IVA</th>");
                out.println("<th>Acción</th>");
            }

            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            // Llenar tabla
            for (Producto p : productos) {
                out.println("<tr>");
                out.println("<td>" + p.getId() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getTipo() + "</td>");

                if (usernameOptional.isPresent()) {
                    out.println("<td>$" + p.getPrecio() + "</td>");
                    out.println("<td>$" + String.format("%.2f", p.getPrecio() * 1.12) + "</td>");
                    out.println("<td><a class='btn btn-primary btn-sm' href=\""
                            + req.getContextPath()
                            + "/agregar-carro?id=" + p.getId()
                            + "\">Agregar</a></td>");
                }
                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>"); // container

            out.println("</body>");
            out.println("</html>");
        }
    }
}
