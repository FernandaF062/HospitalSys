package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Medicamento;
import model.MedicamentoDAO;
import model.MedicamentoDAOImpl;

public class MedicamentoController {
    
    private final MedicamentoDAO medicamentoDAO;

    public MedicamentoController() {
        this.medicamentoDAO = new MedicamentoDAOImpl();
    }
    
    public boolean cadastrarMedicamento(String nome, String fabricante, String descricao) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                System.err.println("Erro: Nome do medicamento é obrigatório.");
                return false;
            }
            
            Medicamento m = new Medicamento();
            m.setNome(nome);
            m.setFabricante(fabricante);
            m.setDescricao(descricao);
            
            medicamentoDAO.salvar(m);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao cadastrar medicamento: " + e.getMessage());
            return false;
        }
    }

    public Medicamento buscarMedicamento(int id) {
        try {
            return medicamentoDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar medicamento: " + e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Medicamento> listarTodosMedicamentos() {
        try {
            return medicamentoDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao listar medicamentos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean deletarMedicamento(int id) {
        try {
            // Regra de negócio: Verificar se existe Receita vinculada antes de deletar
            medicamentoDAO.deletar(id);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao deletar medicamento: " + e.getMessage());
            return false;
        }
    }
}