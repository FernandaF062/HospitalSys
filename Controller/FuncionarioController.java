package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Funcionario;
import model.FuncionarioDAO;
import model.FuncionarioDAOImpl;

public class FuncionarioController {
    
    private final FuncionarioDAO funcionarioDAO;

    public FuncionarioController() {
        this.funcionarioDAO = new FuncionarioDAOImpl();
    }
    
    public boolean cadastrarFuncionario(String nome, String cargo, String cpf, String telefone, String email, int idHospital) {
        try {
            if (nome == null || cpf == null || idHospital <= 0) {
                System.err.println("Erro: Nome, CPF e ID do Hospital são obrigatórios.");
                return false;
            }
            
            Funcionario f = new Funcionario();
            f.setNome(nome);
            f.setCargo(cargo);
            f.setCpf(cpf);
            f.setTelefone(telefone);
            f.setEmail(email);
            f.setIdHospital(idHospital); // FK
            
            funcionarioDAO.salvar(f);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao cadastrar funcionário: " + e.getMessage());
            return false;
        }
    }

    public Funcionario buscarFuncionario(int id) {
        try {
            return funcionarioDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar funcionário: " + e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Funcionario> listarTodosFuncionarios() {
        try {
            return funcionarioDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao listar funcionários: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean deletarFuncionario(int id) {
        try {
            funcionarioDAO.deletar(id);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao deletar funcionário: " + e.getMessage());
            return false;
        }
    }
}