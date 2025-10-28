package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Medico;
import model.MedicoDAO;
import model.MedicoDAOImpl;

public class MedicoController {
    
    private final MedicoDAO medicoDAO;

    public MedicoController() {
        this.medicoDAO = new MedicoDAOImpl();
    }
    
    public boolean cadastrarMedico(String nome, String especialidade, String crm, String telefone, String email) {
        try {
            if (nome == null || crm == null || nome.trim().isEmpty() || crm.trim().isEmpty()) {
                System.err.println("Erro: Nome e CRM do médico são obrigatórios.");
                return false;
            }
            Medico m = new Medico();
            m.setNome(nome);
            m.setCrm(crm);
            m.setEspecialidade(especialidade);
            m.setTelefone(telefone);
            m.setEmail(email);
            
            medicoDAO.salvar(m);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao cadastrar médico: " + e.getMessage());
            return false;
        }
    }

    public Medico buscarMedico(int id) {
        try {
            return medicoDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar médico: " + e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Medico> listarTodosMedicos() {
        try {
            return medicoDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao listar médicos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean deletarMedico(int id) {
        try {
            // Regra de negócio: Verificar se o médico não tem consultas ou vínculos ativos antes de deletar
            medicoDAO.deletar(id);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao deletar médico: " + e.getMessage());
            return false;
        }
    }
}