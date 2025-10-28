package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Leito;
import model.LeitoDAO;
import model.LeitoDAOImpl;

public class LeitoController {
    
    private final LeitoDAO leitoDAO;

    public LeitoController() {
        this.leitoDAO = new LeitoDAOImpl();
    }
    
    public boolean cadastrarLeito(String numero, String tipo, String status) {
        try {
            if (numero == null || numero.trim().isEmpty() || status == null) {
                System.err.println("Erro: Número e Status do leito são obrigatórios.");
                return false;
            }
            
            Leito l = new Leito();
            l.setNumero(numero);
            l.setTipo(tipo);
            l.setStatus(status);
            
            leitoDAO.salvar(l);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao cadastrar leito: " + e.getMessage());
            return false;
        }
    }
    
    public Leito buscarLeito(int id) {
        try {
            return leitoDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar leito: " + e.getMessage());
            return null;
        }
    }

    public boolean atualizarStatusLeito(int id, String novoStatus) {
        try {
            if (id <= 0 || novoStatus == null || novoStatus.trim().isEmpty()) {
                System.err.println("Erro: ID e novo Status do leito são obrigatórios.");
                return false;
            }
            
            // Buscar o leito, atualizar apenas o status, e salvar (o DAO cuida do UPDATE)
            Leito l = leitoDAO.buscarPorId(id);
            if (l != null) {
                l.setStatus(novoStatus);
                leitoDAO.atualizar(l);
                return true;
            } else {
                System.err.println("Erro: Leito não encontrado para atualização.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao atualizar status do leito: " + e.getMessage());
            return false;
        }
    }
    
    public ArrayList<Leito> listarLeitosDisponiveis() {
        try {
          
            return leitoDAO.listarPorStatus("Livre"); 
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao listar leitos disponíveis: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}