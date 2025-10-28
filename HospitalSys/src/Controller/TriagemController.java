package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Triagem;
import model.TriagemDAO;
import model.TriagemDAOImpl;

public class TriagemController {
    
    private final TriagemDAO triagemDAO;

    public TriagemController() {
        this.triagemDAO = new TriagemDAOImpl();
    }
    
   
    public boolean registrarTriagem(String pressaoArterial, double temperatura, double peso, double altura, String obs, int idPaciente) {
        try {
            if (idPaciente <= 0) {
                System.err.println("Erro: ID do Paciente é obrigatório.");
                return false;
            }
            
            Triagem t = new Triagem();
            t.setPressaoArterial(pressaoArterial);
            t.setTemperatura(temperatura);
            t.setPeso(peso);
            t.setAltura(altura);
            t.setObs(obs);
            t.setIdPaciente(idPaciente); // Assumindo este setter
            
            triagemDAO.salvar(t);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao registrar triagem: " + e.getMessage());
            return false;
        }
    }
    
    public Triagem buscarTriagem(int id) {
        try {
            return triagemDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar triagem: " + e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Triagem> listarTodasTriagens() {
        try {
            return triagemDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao listar triagens: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}