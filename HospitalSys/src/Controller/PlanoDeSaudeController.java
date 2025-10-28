package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import model.PlanoDeSaude;
import model.PlanoDeSaudeDAO;
import model.PlanoDeSaudeDAOImpl;

public class PlanoDeSaudeController {
    
    private final PlanoDeSaudeDAO planoDeSaudeDAO;

    public PlanoDeSaudeController() {
        this.planoDeSaudeDAO = new PlanoDeSaudeDAOImpl();
    }
    
    public boolean cadastrarPlano(String nome, String tipo, String cobertura) {
        try {
            if (nome == null || nome.trim().isEmpty() || tipo == null) {
                System.err.println("Erro: Nome e Tipo do plano são obrigatórios.");
                return false;
            }
            
            PlanoDeSaude plano = new PlanoDeSaude();
            plano.setNome(nome);
            plano.setTipo(tipo); // Deve ser um valor válido ENUM: ('PublicoSus', 'Particular')
            plano.setCobertura(cobertura);
            
            planoDeSaudeDAO.salvar(plano);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao cadastrar plano de saúde: " + e.getMessage());
            return false;
        }
    }
    
    public ArrayList<PlanoDeSaude> listarTodosPlanos() {
        try {
            return planoDeSaudeDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao listar planos de saúde: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean atualizarPlano(int id, String nome, String tipo, String cobertura) {
        try {
            if (id <= 0 || nome == null || nome.trim().isEmpty()) {
                System.err.println("Erro: ID e Nome são obrigatórios para atualização.");
                return false;
            }
            PlanoDeSaude plano = new PlanoDeSaude();
            plano.setId(id);
            plano.setNome(nome);
            plano.setTipo(tipo);
            plano.setCobertura(cobertura);
            
            planoDeSaudeDAO.atualizar(plano);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao atualizar plano de saúde: " + e.getMessage());
            return false;
        }
    }
}