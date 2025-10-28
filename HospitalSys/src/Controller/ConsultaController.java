package Controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import model.Consulta;
import model.ConsultaDAO;
import model.ConsultaDAOImpl;

public class ConsultaController {
    
    private final ConsultaDAO consultaDAO;

    public ConsultaController() {
        this.consultaDAO = new ConsultaDAOImpl();
    }
    
    public boolean agendarConsulta(LocalDate data, LocalTime hora, String diagnosticoGeral, int idPaciente, int idMedico) {
        try {
            if (data == null || hora == null || idPaciente <= 0 || idMedico <= 0) {
                System.err.println("Erro: Dados de agendamento incompletos.");
                return false;
            }
            
            Consulta c = new Consulta();
            c.setData(data);
            c.setHora(hora);
            c.setDiagnosticoGeral(diagnosticoGeral);
            c.setIdPaciente(idPaciente); // FK
            c.setIdMedico(idMedico);     // FK
            
            consultaDAO.salvar(c);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao agendar consulta: " + e.getMessage());
            return false;
        }
    }

    public Consulta buscarConsultaPorId(int id) {
        try {
            return consultaDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar consulta: " + e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Consulta> listarTodasConsultas() {
        try {
            return consultaDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao listar consultas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean deletarConsulta(int id) {
        try {
            consultaDAO.deletar(id);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao deletar consulta: " + e.getMessage());
            return false;
        }
    }
}