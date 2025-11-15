package com.ronny.aplicacionweb.manejosesiones.controllers;

import com.ronny.aplicacionweb.manejosesiones.models.DetalleCarro;
import com.ronny.aplicacionweb.manejosesiones.models.ItemCarro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DESARROLLADOR: Ronny Bastidas
 * DESCRIPCION: Servlet que genera una factura en HTML lista para imprimir como PDF
 * No requiere dependencias externas
 */
@WebServlet("/generar-factura")
public class GenerarFacturaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtener la sesión y el carrito
        HttpSession session = req.getSession();
        DetalleCarro carro = (DetalleCarro) session.getAttribute("carro");
        String username = (String) session.getAttribute("username");

        // Validar que existe un carrito con productos
        if (carro == null || carro.getItem().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/ver-carro");
            return;
        }

        // Configurar respuesta HTML
        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            // Obtener fecha y hora actual
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String numeroFactura = String.format("%010d", System.currentTimeMillis() % 10000000000L);

            // Calcular totales
            double subtotal = carro.getTotal();
            double iva = subtotal * 0.12;
            double totalConIva = subtotal + iva;

            // Generar HTML de la factura
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Factura - " + numeroFactura + "</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 40px; }");
            out.println(".header { text-align: center; margin-bottom: 30px; border-bottom: 3px solid #333; padding-bottom: 20px; }");
            out.println(".header h1 { margin: 5px 0; color: #2c3e50; }");
            out.println(".header p { margin: 3px 0; color: #7f8c8d; }");
            out.println(".factura-info { margin: 20px 0; background-color: #f8f9fa; padding: 15px; border-radius: 5px; }");
            out.println(".factura-info p { margin: 5px 0; }");
            out.println("table { width: 100%; border-collapse: collapse; margin: 20px 0; }");
            out.println("th { background-color: #34495e; color: white; padding: 12px; text-align: left; }");
            out.println("td { padding: 10px; border-bottom: 1px solid #ddd; }");
            out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
            out.println(".totales { margin-top: 30px; float: right; width: 300px; }");
            out.println(".totales table { margin: 0; }");
            out.println(".totales td { padding: 8px; }");
            out.println(".total-final { font-size: 1.3em; font-weight: bold; background-color: #27ae60 !important; color: white; }");
            out.println(".footer { clear: both; text-align: center; margin-top: 50px; padding-top: 20px; border-top: 2px solid #333; color: #7f8c8d; }");
            out.println(".no-print { margin-top: 30px; text-align: center; }");
            out.println(".btn-print { background-color: #3498db; color: white; padding: 12px 30px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; margin: 5px; }");
            out.println(".btn-print:hover { background-color: #2980b9; }");
            out.println(".btn-back { background-color: #95a5a6; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; font-size: 16px; margin: 5px; display: inline-block; }");
            out.println(".btn-back:hover { background-color: #7f8c8d; }");
            out.println("@media print { .no-print { display: none; } }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            // Encabezado
            out.println("<div class='header'>");
            out.println("<h1>FACTURA</h1>");
            out.println("<p>Quito - Pichincha - Ecuador</p>");
            out.println("</div>");

            // Información de la factura
            out.println("<div class='factura-info'>");
            out.println("<h2 style='margin-top: 0;'>FACTURA</h2>");
            out.println("<p><strong>Factura No:</strong> " + numeroFactura + "</p>");
            out.println("<p><strong>Fecha:</strong> " + now.format(formatter) + "</p>");
            out.println("<p><strong>Cliente:</strong> " + (username != null ? username : "Invitado") + "</p>");
            out.println("</div>");

            // Tabla de productos
            out.println("<h3>Detalle de Productos</h3>");
            out.println("<table>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Cant.</th>");
            out.println("<th>Producto</th>");
            out.println("<th>Tipo</th>");
            out.println("<th>Precio Unit.</th>");
            out.println("<th>IVA 12%</th>");
            out.println("<th>Subtotal</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            // Agregar items del carrito
            for (ItemCarro item : carro.getItem()) {
                double ivaItem = item.getSubtotal() * 0.12;
                out.println("<tr>");
                out.println("<td>" + item.getCantidad() + "</td>");
                out.println("<td>" + item.getProducto().getNombre() + "</td>");
                out.println("<td>" + item.getProducto().getTipo() + "</td>");
                out.println("<td>$" + String.format("%.2f", (double)item.getProducto().getPrecio()) + "</td>");
                out.println("<td>$" + String.format("%.2f", ivaItem) + "</td>");
                out.println("<td>$" + String.format("%.2f", item.getSubtotal()) + "</td>");
                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");

            // Totales
            out.println("<div class='totales'>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td><strong>Subtotal:</strong></td>");
            out.println("<td style='text-align: right;'>$" + String.format("%.2f", subtotal) + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td><strong>IVA 12%:</strong></td>");
            out.println("<td style='text-align: right;'>$" + String.format("%.2f", iva) + "</td>");
            out.println("</tr>");
            out.println("<tr class='total-final'>");
            out.println("<td><strong>TOTAL:</strong></td>");
            out.println("<td style='text-align: right;'><strong>$" + String.format("%.2f", totalConIva) + "</strong></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</div>");

            // Footer
            out.println("<div class='footer'>");
            out.println("<p><strong>¡Gracias por su compra!</strong></p>");
            out.println("</div>");

            // Botones de acción (no se imprimen)
            out.println("<div class='no-print'>");
            out.println("<button class='btn-print' onclick='window.print()'> Imprimir / Guardar como PDF</button>");
            out.println("<a href='" + req.getContextPath() + "/ver-carro' class='btn-back'> Volver al carrito</a>");
            out.println("</div>");

            // Script para auto-abrir dialogo de impresión (opcional)
            out.println("<script>");
            out.println("// Descomentar la siguiente línea si quieres que se abra automáticamente el diálogo de impresión");
            out.println("// window.onload = function() { window.print(); }");
            out.println("</script>");

            out.println("</body>");
            out.println("</html>");
        }
    }
}