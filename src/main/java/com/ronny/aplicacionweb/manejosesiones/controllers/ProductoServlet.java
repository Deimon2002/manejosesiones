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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;


@WebServlet({"/productos", "/productos.html"})// rutas que atiende este servlet: /productos y /productos.html
public class ProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //  Servicio que entrega la lista de productos
        ProductoService productoService = new ProductoServiceImpl();
        LoginService auth = new LoginServiceSessionImpl(); // servicio para revisar la sesión

        //  Obtenemos la lista de productos
        List<Producto> productos = productoService.listar();

        //  Revisamos si el usuario tiene sesión iniciada
        Optional<String> usernameOptional = auth.getUsername(req);

        //  Empezamos a construir la página HTML
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("  <head>");
            out.println("    <meta charset=\"UTF-8\">");
            out.println("    <title>Listado de Productos</title>");
            out.println("  </head>");
            out.println("  <body>");
            out.println("    <h1>Listado de Productos!</h1>");

            //  Saludo condicional si esta autenticado
            if (usernameOptional.isPresent()) {
                out.println("<div style='color: blue;'>Hola " + usernameOptional.get() + ", Bienvenido!</div>");
            }

            //  Empezamos la tabla HTML
            out.println("<table>");
            out.println("  <tr>");
            out.println("    <th>id</th>");
            out.println("    <th>nombre</th>");
            out.println("    <th>tipo</th>");

            //  la columna precio solo aparece si hay una sesión iniciada
            if (usernameOptional.isPresent()) {
                out.println("    <th>precio</th>");
            }
            out.println("  </tr>");

            //  Llenamos la tabla con los productos
            for (Producto p : productos) {
                out.println("  <tr>");
                out.println("    <td>" + p.getId() + "</td>");
                out.println("    <td>" + p.getNombre() + "</td>");
                out.println("    <td>" + p.getTipo() + "</td>");

                //  Mostramos el precio solo si hay una sesión iniciada
                if (usernameOptional.isPresent()) {
                    out.println("    <td>" + p.getPrecio() + "</td>");
                }
                out.println("  </tr>");
            }
            out.println("</table>");
            out.println("  </body>");
            out.println("</html>");
        }
    }
}