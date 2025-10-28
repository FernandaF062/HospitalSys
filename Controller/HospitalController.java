package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Hospital;
import model.HospitalDAO;
import model.HospitalDAOImpl;

public class HospitalController {
    
    private final HospitalDAO hospitalDAO;

    public HospitalController() {
        this.hospitalDAO = new HospitalDAOImpl();
    }
    
    public boolean cadastrarHospital(String nome, String endereco, String telefone) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                System.err.println("Erro: Nome do Hospital é obrigatório.");
                return false;
            }
            
            Hospital h = new Hospital();
            h.setNome(nome);
            h.setEndereco(endereco);
            h.setTelefone(telefone);
            
            hospitalDAO.salvar(h);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao cadastrar hospital: " + e.getMessage());
            return false;
        }
    }

    public Hospital buscarHospital(int id) {
        try {
            return hospitalDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar hospital: " + e.getMessage());
            return null;
        }
    }
    
    public boolean atualizarInformacoes(int id, String nome, String endereco, String telefone) {
        try {
            if (id <= 0 || nome == null || nome.trim().isEmpty()) {
                System.err.println("Erro: ID e Nome do Hospital são obrigatórios para atualização.");
                return false;
            }
            
            Hospital h = new Hospital();
            h.setId(id);
            h.setNome(nome);
            h.setEndereco(endereco);
            h.setTelefone(telefone);
            
            hospitalDAO.atualizar(h);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao atualizar hospital: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deletarHospital(int id) {
        try {
            // Regra de negócio: Verificar se não há funcionários ou ambulâncias vinculadas antes de deletar
            hospitalDAO.deletar(id);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao deletar hospital: " + e.getMessage());
            return false;
        }
    }
}