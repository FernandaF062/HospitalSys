package Controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.Visita;
import model.VisitaDAO;
import model.VisitaDAOImpl;

public class VisitaController {

    private final VisitaDAO visitaDAO;

    public VisitaController() {
        this.visitaDAO = new VisitaDAOImpl();
    }

    public boolean registrarVisita(int idPaciente, String observacao) {
        try {
            if (idPaciente <= 0) {
                System.err.println("Erro: ID do Paciente é obrigatório.");
                return false;
            }
            
            Visita v = new Visita();
            v.setIdPaciente(idPaciente); // FK
            v.setData(LocalDateTime.now());
            v.setObservacao(observacao);
            
            visitaDAO.salvar(v);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao registrar visita: " + e.getMessage());
            return false;
        }
    }
    
    public Visita buscarVisita(int id) {
        try {
            return visitaDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar visita: " + e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Visita> listarTodasVisitas() {
        try {
            return visitaDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao listar visitas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}