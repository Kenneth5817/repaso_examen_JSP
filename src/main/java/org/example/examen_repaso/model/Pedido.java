package org.example.examen_repaso.model;

import java.time.LocalDate;

public class Pedido {
    private int id;
    private double total;
    private LocalDate fecha;
    private int idCliente;
    private int idComercial;
    private Cliente cliente;

    // Constructor vacío
    public Pedido() {
    }

    // Constructor con parámetros
    public Pedido(int id, double total, LocalDate fecha, int idCliente, int idComercial) {
        this.id = id;
        this.total = total;
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.idComercial = idComercial;
    }

    public Pedido(double total, LocalDate fecha, int clienteId, int comercialId) {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdComercial() {
        return idComercial;
    }

    public void setIdComercial(int idComercial) {
        this.idComercial = idComercial;
    }

    // Método toString
    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", total=" + total +
                ", fecha=" + fecha +
                ", idCliente=" + idCliente +
                ", idComercial=" + idComercial +
                '}';
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;  // Asignación del valor pasado como parámetro a la propiedad cliente
    }
}
