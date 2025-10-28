package Controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Paciente;
import model.PacienteDAO;
import model.PacienteDAOImpl;

public class PacienteController {

    private PacienteDAO pacienteDAO;

    public PacienteController() {
        this.pacienteDAO = new PacienteDAOImpl();
    }

    public boolean adicionarPaciente(String nome, String email, String telefone, String endereco, char sexo, Date dataNascimento) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                System.err.println("Erro: Nome do paciente é obrigatório.");
                return false;
            }
            
            Paciente p = new Paciente();
            p.setNome(nome);
            p.setEmail(email);
            p.setTelefone(telefone);
            p.setEndereco(endereco);
            p.setSexo(sexo);
            p.setDataNascimento(dataNascimento);
            
            pacienteDAO.salvar(p);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao adicionar paciente: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Paciente> listarTodosPacientes() {
        try {
            return pacienteDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao listar pacientes: " + e.getMessage());
            return new ArrayList<>(); 
        }
    }

    public boolean atualizarPaciente(int id, String nome, String email, String telefone, String endereco, char sexo, Date dataNascimento) {
        try {
            if (id <= 0 || nome == null || nome.trim().isEmpty()) {
                System.err.println("Erro: ID e Nome do paciente são obrigatórios para atualização.");
                return false;
            }
            
            Paciente p = new Paciente();
            p.setId(id);
            p.setNome(nome);
            p.setEmail(email);
            p.setTelefone(telefone);
            p.setEndereco(endereco);
            p.setSexo(sexo);
            p.setDataNascimento(dataNascimento);
            
            pacienteDAO.atualizar(p);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao atualizar paciente: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarPaciente(int id) {
        try {
            // Regra de negócio: Verificar se não há consultas, internações, etc., ativas antes de deletar
            pacienteDAO.deletar(id);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao deletar paciente: " + e.getMessage());
            return false;
        }
    }
    
    public Paciente buscarPaciente(int id) {
        try {
            return pacienteDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar paciente: " + e.getMessage());
            return null;
        }
    }
}