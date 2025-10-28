package Controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.Internacao;
import model.InternacaoDAO;
import model.InternacaoDAOImpl;
// Não precisamos importar Paciente ou Leito, pois o Controller trabalha com os IDs
// para manter a integridade, embora a View possa passar os objetos.

/**
 * Controller responsável por gerenciar as operações de Internações.
 * A lógica aqui deve garantir que os IDs de Paciente e Leito existam.
 *
 * @author ADM
 */
public class InternacaoController {

    private final InternacaoDAO internacaoDAO;
    // Opcionalmente, pode-se injetar LeitoController para gerenciar o status do Leito
    private final LeitoController leitoController; 

    public InternacaoController() {
        this.internacaoDAO = new InternacaoDAOImpl();
        this.leitoController = new LeitoController();
    }

    /**
     * Inicia o processo de internação.
     * @param pacienteId O ID do paciente (FK).
     * @param leitoId O ID do leito a ser ocupado (FK).
     * @param motivo O motivo da internação.
     * @return true se a internação foi iniciada com sucesso.
     */
    public boolean iniciarInternacao(int pacienteId, int leitoId, String motivo) {
        try {
            if (pacienteId <= 0 || leitoId <= 0 || motivo == null || motivo.trim().isEmpty()) {
                System.err.println("Erro: IDs ou Motivo não especificados corretamente para internação.");
                return false;
            }
            
            // Lógica de negócio: Verificar se o leito está livre antes de salvar
            // Este método faria uma busca no DB para checar o status.
            if (!leitoController.verificarDisponibilidade(leitoId)) {
                System.err.println("Erro: Leito não está disponível.");
                return false;
            }

            Internacao i = new Internacao();
            // Assumindo que o Model Internacao tem setters para os IDs
            i.setPacienteId(pacienteId); 
            i.setLeitoId(leitoId);
            i.setMotivo(motivo);
            i.setDataInicio(LocalDateTime.now());
            
            internacaoDAO.salvar(i);
            
            // Regra de negócio: Mudar o status do leito para 'Ocupado'
            leitoController.atualizarStatusLeito(leitoId, "Ocupado");
            
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao iniciar internação: " + e.getMessage());
            return false;
        }
    }

    /**
     * Finaliza uma internação.
     * @param internacaoId ID da internação a ser finalizada.
     * @return true se a finalização foi bem-sucedida.
     */
    public boolean finalizarInternacao(int internacaoId) {
        try {
            Internacao i = internacaoDAO.buscarPorId(internacaoId);
            if (i == null || i.getDataFim() != null) {
                System.err.println("Erro: Internação não encontrada ou já finalizada.");
                return false;
            }
            
            i.setDataFim(LocalDateTime.now());
            internacaoDAO.atualizar(i);
            
            // Regra de negócio: Mudar o status do leito para 'Livre'
            leitoController.atualizarStatusLeito(i.getLeitoId(), "Livre");
            
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao finalizar internação: " + e.getMessage());
            return false;
        }
    }
    
   
    
    public Internacao buscarInternacao(int id) {
        try {
            return internacaoDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar internação: " + e.getMessage());
            return null;
        }
    }
}