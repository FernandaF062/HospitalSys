package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import model.FichaMedica;
import model.FichaMedicaDAO;
import model.FichaMedicaDAOImpl;

public class FichaMedicaController {
    
    private final FichaMedicaDAO fichaMedicaDAO;

    public FichaMedicaController() {
        this.fichaMedicaDAO = new FichaMedicaDAOImpl();
    }
    
    public boolean salvarFichaMedica(String histClinico, String alergias, String observacoes, int idPaciente) {
        try {
            if (idPaciente <= 0) {
                System.err.println("Erro: ID do Paciente é obrigatório.");
                return false;
            }

            FichaMedica f = new FichaMedica();
            f.setHistClinico(histClinico);
            f.setAlergias(alergias);
            f.setObservacoes(observacoes);
            f.setIdPaciente(idPaciente); // FK (UNIQUE)
            
            fichaMedicaDAO.salvar(f); // Deve ser um salvar/atualizar condicional no DAO
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao salvar ficha médica: " + e.getMessage());
            return false;
        }
    }

    public FichaMedica buscarFichaPorPaciente(int idPaciente) {
        try {
            // Assumindo um método específico no DAO para buscar pela FK
            return fichaMedicaDAO.buscarPorPacienteId(idPaciente); 
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar ficha médica: " + e.getMessage());
            return null;
        }
    }
    
    public boolean deletarFichaMedica(int id) {
        try {
            fichaMedicaDAO.deletar(id);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao deletar ficha médica: " + e.getMessage());
            return false;
        }
    }
}