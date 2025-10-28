package Controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.Exame;
import model.ExameDAO;
import model.ExameDAOImpl;

public class ExameController {
    
    private final ExameDAO exameDAO;

    public ExameController() {
        this.exameDAO = new ExameDAOImpl();
    }
    
    public boolean solicitarExame(String tipoExame, String tipoResultado, int idConsulta) {
        try {
            if (tipoExame == null || tipoExame.trim().isEmpty() || idConsulta <= 0) {
                System.err.println("Erro: Tipo de exame e ID da Consulta são obrigatórios.");
                return false;
            }

            Exame e = new Exame();
            e.setTipoExame(tipoExame);
            e.setData(LocalDateTime.now());
            e.setTipoResultado(tipoResultado);
            e.setIdConsulta(idConsulta); // FK
            
            exameDAO.salvar(e);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao solicitar exame: " + e.getMessage());
            return false;
        }
    }

    public Exame buscarExame(int id) {
        try {
            return exameDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar exame: " + e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Exame> listarTodosExames() {
        try {
            return exameDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao listar exames: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean deletarExame(int id) {
        try {
            // Regra de negócio: Verificar se já existe Diagnóstico vinculado antes de deletar
            exameDAO.deletar(id);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao deletar exame: " + e.getMessage());
            return false;
        }
    }
}