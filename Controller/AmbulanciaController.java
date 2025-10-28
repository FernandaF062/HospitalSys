package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Ambulancia;
import model.AmbulanciaDAO;
import model.AmbulanciaDAOImpl;
import model.Hospital;

public class AmbulanciaController {

    private final AmbulanciaDAO ambulanciaDAO;

    public AmbulanciaController() {
        this.ambulanciaDAO = new AmbulanciaDAOImpl();
    }
    
    public boolean cadastrarAmbulancia(String placa, String modelo, int ano, int idHospital) {
        try {
            if (placa == null || placa.trim().isEmpty() || idHospital <= 0) {
                System.err.println("Erro: Placa e ID do Hospital são obrigatórios.");
                return false;
            }
            Ambulancia a = new Ambulancia();
            a.setPlaca(placa);
            a.setModelo(modelo);
            a.setAno(ano);
            a.setIdHospital(idHospital); // FK
            ambulanciaDAO.salvar(a);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao cadastrar ambulância: " + e.getMessage());
            return false;
        }
    }

    public Ambulancia buscarAmbulancia(int id) {
        try {
            return ambulanciaDAO.buscarPorId(id);
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao buscar ambulância: " + e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Ambulancia> listarTodasAmbulancias() {
        try {
            return ambulanciaDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao listar ambulâncias: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public boolean deletarAmbulancia(int id) {
        try {
            ambulanciaDAO.deletar(id);
            return true;
        } catch (SQLException e) {
            System.err.println("Falha no Controller ao deletar ambulância: " + e.getMessage());
            return false;
        }
    }
}