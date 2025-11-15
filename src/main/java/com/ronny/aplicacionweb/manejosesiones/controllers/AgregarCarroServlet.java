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
import java.io.PrintWriter;
import java.util.Optional;

/*
 * Servlet que maneja la funcionalidad de agregar productos al carrito de compras.
 * Mapea la URL "/agregar-carro" para procesar las solicitudes.
     * Procesa las peticiones GET para agregar un producto al carrito.
     *
     * @param req  Objeto HttpServletRequest con la información de la petición
     * @param resp Objeto HttpServletResponse para enviar la respuesta
     * @throws ServletException Si ocurre un error específico del servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    @WebServlet("/agregar-carro")
    public class AgregarCarroServlet extends HttpServlet {

        /*
         * Procesa las peticiones GET para agregar un producto al carrito.
         *
         * @param req  Objeto HttpServletRequest con la información de la petición
         * @param resp Objeto HttpServletResponse para enviar la respuesta
         * @throws ServletException Si ocurre un error específico del servlet
         * @throws IOException      Si ocurre un error de entrada/salida
         */
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            // Verificar si el usuario está logueado
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("username") == null) {
                resp.sendRedirect(req.getContextPath() + "/login.html");
                return;
            }

            // Obtiene el ID del producto desde los parámetros de la URL
            Long id = Long.parseLong(req.getParameter("id"));

            // Crea una instancia del servicio para consultar productos
            ProductoService service = new ProductoServiceImpl();

            // Busca el producto por su ID en la base de datos
            Optional<Producto> productoOpt = service.porId(id);

            // Verifica si el producto existe
            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();

                // Crea un nuevo item para el carrito con cantidad inicial de 1
                ItemCarro item = new ItemCarro(1, producto);

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

                // Mostrar página de confirmación con Bootstrap
                mostrarConfirmacion(req, resp, producto);

            } else {
                // Producto no encontrado
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado");
            }
        }

        /**
         * Muestra una página de confirmación con Bootstrap cuando se agrega un producto.
         *
         * @param req      Objeto HttpServletRequest
         * @param resp     Objeto HttpServletResponse
         * @param producto Producto que fue agregado al carrito
         * @throws IOException Si ocurre un error de entrada/salida
         */
        private void mostrarConfirmacion(HttpServletRequest req, HttpServletResponse resp, Producto producto)
                throws IOException {

            resp.setContentType("text/html;charset=UTF-8");

            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html lang='es'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Producto Agregado</title>");

                // 🎨 BOOTSTRAP CSS
                out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
                out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css'>");

                // Estilos personalizados
                out.println("<style>");
                out.println("body {");
                out.println("    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);");
                out.println("    min-height: 100vh;");
                out.println("    display: flex;");
                out.println("    align-items: center;");
                out.println("    justify-content: center;");
                out.println("    padding: 20px;");
                out.println("}");
                out.println(".success-card {");
                out.println("    animation: slideIn 0.5s ease-out;");
                out.println("    border-radius: 15px;");
                out.println("    box-shadow: 0 10px 30px rgba(0,0,0,0.3);");
                out.println("}");
                out.println("@keyframes slideIn {");
                out.println("    from { transform: translateY(-50px); opacity: 0; }");
                out.println("    to { transform: translateY(0); opacity: 1; }");
                out.println("}");
                out.println(".check-icon {");
                out.println("    animation: scaleIn 0.5s ease-out 0.3s both;");
                out.println("}");
                out.println("@keyframes scaleIn {");
                out.println("    from { transform: scale(0); }");
                out.println("    to { transform: scale(1); }");
                out.println("}");
                out.println("</style>");

                out.println("</head>");
                out.println("<body>");

                // Contenedor principal
                out.println("<div class='container'>");
                out.println("<div class='row justify-content-center'>");
                out.println("<div class='col-md-6 col-lg-5'>");

                // Card de éxito
                out.println("<div class='card success-card border-0'>");
                out.println("<div class='card-body text-center p-5'>");

                // Icono de éxito animado
                out.println("<div class='mb-4'>");
                out.println("<i class='bi bi-check-circle-fill text-success check-icon' style='font-size: 5rem;'></i>");
                out.println("</div>");

                // Mensaje principal
                out.println("<h2 class='card-title text-success fw-bold mb-3'>¡Producto Agregado!</h2>");
                out.println("<p class='card-text text-muted fs-5 mb-4'>");
                out.println("<strong>" + producto.getNombre() + "</strong> se agregó exitosamente a tu carrito.");
                out.println("</p>");

                // Información del producto
                out.println("<div class='alert alert-light border shadow-sm'>");
                out.println("<div class='row align-items-center'>");
                out.println("<div class='col-6 text-start'>");
                out.println("<p class='mb-2'><i class='bi bi-tag-fill text-primary'></i> <strong>Categoría:</strong></p>");
                out.println("<span class='badge bg-info text-dark fs-6'>" + producto.getTipo() + "</span>");
                out.println("</div>");
                out.println("<div class='col-6 text-end'>");
                out.println("<p class='mb-2'><i class='bi bi-cash-coin text-success'></i> <strong>Precio:</strong></p>");
                out.println("<span class='text-success fw-bold fs-3'>$" + producto.getPrecio() + "</span>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");

                // Separador
                out.println("<hr class='my-4'>");

                // Botones de acción
                out.println("<div class='d-grid gap-2 d-md-flex justify-content-md-center'>");
                out.println("<a href='" + req.getContextPath() + "/productos' class='btn btn-outline-primary btn-lg'>");
                out.println("<i class='bi bi-arrow-left-circle-fill'></i> Seguir Comprando");
                out.println("</a>");
                out.println("<a href='" + req.getContextPath() + "/ver-carro' class='btn btn-success btn-lg'>");
                out.println("<i class='bi bi-cart-check-fill'></i> Ver Mi Carrito");
                out.println("</a>");
                out.println("</div>");

                // Contador de redirección automática (opcional)
                out.println("<div class='mt-4'>");
                out.println("<small class='text-muted'>");
                out.println("<i class='bi bi-info-circle'></i> Serás redirigido al carrito en <span id='countdown'>5</span> segundos...");
                out.println("</small>");
                out.println("</div>");

                out.println("</div>"); // fin card-body
                out.println("</div>"); // fin card
                out.println("</div>"); // fin col
                out.println("</div>"); // fin row
                out.println("</div>"); // fin container

                // 🎨 BOOTSTRAP JS
                out.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js'></script>");

                // Script de redirección automática
                out.println("<script>");
                out.println("let seconds = 5;");
                out.println("const countdown = document.getElementById('countdown');");
                out.println("const interval = setInterval(() => {");
                out.println("    seconds--;");
                out.println("    countdown.textContent = seconds;");
                out.println("    if (seconds <= 0) {");
                out.println("        clearInterval(interval);");
                out.println("        window.location.href = '" + req.getContextPath() + "/ver-carro';");
                out.println("    }");
                out.println("}, 1000);");
                out.println("</script>");

                out.println("</body>");
                out.println("</html>");
            }
        }
    }
