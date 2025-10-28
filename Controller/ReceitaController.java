package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Receita;
import model.ReceitaDAO;
import model.ReceitaDAOImpl;

public class ReceitaController {
    
    private final ReceitaDAO receitaDAO;

    public ReceitaController() {
        this.receitaDAO = new ReceitaDAOImpl();
    }
    
    public boolean emitirReceita(String dosagem, String frequencia, String duracao, int idDiagnostico, int idMedicamento) {
        try {
            if (idDiagnostico <= 0 || idMedicamento <= 0) {
                 System.err.println("Erro: IDs de Diagnóstico e Medicamento são obrigatórios.");
                 return false;
            }
            
            Receita r = new Receita();
            r.setDosagem(dosagem);
            r.setFrequencia(frequencia);
            r.setDuracao(duracao);
            r.setIdDiagnostico(idDiagnostico); // FK
            r.setIdMedicamento(idMedicamento);   // FK
            
            receitaDAO.salvar(r);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao emitir receita: " + e.getMessage());
            return false;
        }
    }
    
    public Receita buscarReceita(int id) {
        try {
            return receitaDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar receita: " + e.getMessage());
            return null;
        }
    }
    
    public boolean deletarReceita(int id) {
        try {
            receitaDAO.deletar(id);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao deletar receita: " + e.getMessage());
            return false;
        }
    }
}