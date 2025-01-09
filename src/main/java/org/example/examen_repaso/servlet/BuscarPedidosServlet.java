package org.example.examen_repaso.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.examen_repaso.dao.PedidoDAOImpl;
import org.example.examen_repaso.model.Pedido;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "BuscarPedidosPorRangoServlet", value = "/buscarPedidosServlet")
public class BuscarPedidosServlet extends HttpServlet {

    private PedidoDAOImpl pedidoDAO = new PedidoDAOImpl();  // Suponiendo que tienes una clase DAO para los pedidos

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtenemos los parámetros de cantidad mínima y máxima
        String cantidadMinStr = request.getParameter("cantidadMin");
        String cantidadMaxStr = request.getParameter("cantidadMax");

        // Validamos si los parámetros son válidos
        if (cantidadMinStr != null && cantidadMaxStr != null) {
            try {
                double cantidadMin = Double.parseDouble(cantidadMinStr);
                double cantidadMax = Double.parseDouble(cantidadMaxStr);

                // Llamamos a un método en el DAO para buscar los pedidos en ese rango
                List<Pedido> pedidos = pedidoDAO.buscarPedidosPorRango(cantidadMin, cantidadMax);

                // Pasamos los resultados al JSP
                request.setAttribute("pedidos", pedidos);
                request.getRequestDispatcher("/WEB-INF/jsp/resultadosBusqueda.jsp").forward(request, response);

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Los valores ingresados no son válidos.");
                request.getRequestDispatcher("/WEB-INF/jsp/formularioBusqueda.jsp").forward(request, response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            request.setAttribute("error", "Por favor, ingrese ambos valores de cantidad.");
            request.getRequestDispatcher("/WEB-INF/jsp/formularioBusqueda.jsp").forward(request, response);
        }
    }
}
