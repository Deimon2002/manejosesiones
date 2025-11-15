<%@ page import="com.ronny.aplicacionweb.manejosesiones.models.DetalleCarro" %>
<%@ page import="com.ronny.aplicacionweb.manejosesiones.models.ItemCarro" %><%--
  Created by IntelliJ IDEA.
  User: Thede
  Date: 14/11/2025
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="com.ronny.aplicacionweb.manejosesiones.models.DetalleCarro,
            com.ronny.aplicacionweb.manejosesiones.models.ItemCarro" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carrito de Compras</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        h1 {
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .total-row {
            font-weight: bold;
            background-color: #f9f9f9;
        }
        .final-total {
            font-size: 1.2em;
            color: #4CAF50;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 5px;
            text-decoration: none;
            border-radius: 5px;
            cursor: pointer;
            border: none;
            font-size: 14px;
        }
        .btn-primary {
            background-color: #4CAF50;
            color: white;
        }
        .btn-primary:hover {
            background-color: #45a049;
        }
        .btn-secondary {
            background-color: #008CBA;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #007399;
        }
        .btn-pdf {
            background-color: #ff6b6b;
            color: white;
        }
        .btn-pdf:hover {
            background-color: #ee5a52;
        }
        .empty-cart {
            text-align: center;
            padding: 40px;
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .actions {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<h1>🛒 Carrito de Compras</h1>

<%
    DetalleCarro carro = (DetalleCarro) session.getAttribute("carro");
    String username = (String) session.getAttribute("username");

    if (carro == null || carro.getItem().isEmpty()) {
%>
<div class="empty-cart">
    <h2> El carrito está vacío</h2>
    <p>No has agregado ningún producto a tu carrito.</p>
</div>
<%
} else {
    double subtotal = carro.getTotal();
    double iva = subtotal * 0.12;
    double totalConIva = subtotal + iva;
%>
<table>
    <thead>
    <tr>
        <th>Producto</th>
        <th>Tipo</th>
        <th>Precio Unitario</th>
        <th>Cantidad</th>
        <th>Subtotal</th>
    </tr>
    </thead>
    <tbody>
    <% for (ItemCarro item : carro.getItem()) { %>
    <tr>
        <td><%= item.getProducto().getNombre() %></td>
        <td><%= item.getProducto().getTipo() %></td>
        <td>$<%= String.format("%.2f", (double)item.getProducto().getPrecio()) %></td>
        <td><%= item.getCantidad() %></td>
        <td>$<%= String.format("%.2f", item.getSubtotal()) %></td>
    </tr>
    <% } %>
    </tbody>
    <tfoot>
    <tr class="total-row">
        <td colspan="4" style="text-align: right;"><strong>Subtotal:</strong></td>
        <td>$<%= String.format("%.2f", subtotal) %></td>
    </tr>
    <tr class="total-row">
        <td colspan="4" style="text-align: right;"><strong>IVA (12%):</strong></td>
        <td>$<%= String.format("%.2f", iva) %></td>
    </tr>
    <tr class="total-row final-total">
        <td colspan="4" style="text-align: right;"><strong>TOTAL A PAGAR:</strong></td>
        <td><strong>$<%= String.format("%.2f", totalConIva) %></strong></td>
    </tr>
    </tfoot>
</table>

<div class="actions">
    <a href="<%= request.getContextPath() %>/generar-factura" class="btn btn-pdf">
        Ver Factura
    </a>
    <a href="<%= request.getContextPath() %>/productos.html" class="btn btn-secondary">
        Seguir comprando
    </a>
    <a href="<%= request.getContextPath() %>/index.html" class="btn btn-primary">
        Volver al inicio
    </a>
</div>
<%
    }
%>

<% if (carro != null && !carro.getItem().isEmpty()) { %>
<div style="margin-top: 30px; padding: 15px; background-color: #e8f5e9; border-radius: 5px;">
    <h3>Información</h3>
    <p><strong>Usuario:</strong> <%= username != null ? username : "Invitado" %></p>
    <p><strong>Productos en carrito:</strong> <%= carro.getItem().size() %></p>
</div>
<% } %>
</body>
</html>