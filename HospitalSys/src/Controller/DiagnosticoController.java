package Controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.Diagnostico;
import model.DiagnosticoDAO;
import model.DiagnosticoDAOImpl;

public class DiagnosticoController {
    
    private final DiagnosticoDAO diagnosticoDAO;

    public DiagnosticoController() {
        this.diagnosticoDAO = new DiagnosticoDAOImpl();
    }
    
    public boolean registrarDiagnostico(String descricaoMedica, int idExame) {
        try {
            if (descricaoMedica == null || descricaoMedica.trim().isEmpty() || idExame <= 0) {
                System.err.println("Erro: Descrição e ID do Exame são obrigatórios.");
                return false;
            }
            
            Diagnostico d = new Diagnostico();
            d.setDescricaoMed(descricaoMedica);
            d.setData(LocalDateTime.now());
            d.setIdExame(idExame); // FK
            
            diagnosticoDAO.salvar(d);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao registrar diagnóstico: " + e.getMessage());
            return false;
        }
    }

    public Diagnostico buscarDiagnostico(int id) {
        try {
            return diagnosticoDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar diagnóstico: " + e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Diagnostico> listarTodosDiagnosticos() {
        try {
            return diagnosticoDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao listar diagnósticos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean deletarDiagnostico(int id) {
        try {
            // Regra de negócio: Verificar se existe Receita vinculada antes de deletar
            diagnosticoDAO.deletar(id);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao deletar diagnóstico: " + e.getMessage());
            return false;
        }
    }
}