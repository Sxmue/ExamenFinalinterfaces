package com.example.examenfinaldesarrollointerfaces;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ExamenView implements Initializable {

    HashMap hm;

    Connection c;

    JasperPrint jasperPrint;

    @javafx.fxml.FXML
    private TextField txtNombre;
    @javafx.fxml.FXML
    private TextField txtPeso;
    @javafx.fxml.FXML
    private TextField txtEdad;
    @javafx.fxml.FXML
    private TextField txtTalla;
    @javafx.fxml.FXML
    private ComboBox<String> comboActividad;
    @javafx.fxml.FXML
    private TextArea lblObservaciones;
    @javafx.fxml.FXML
    private Button btnGuardar;
    @javafx.fxml.FXML
    private Label lblMensaje;
    @javafx.fxml.FXML
    private RadioButton radioHombre;
    @javafx.fxml.FXML
    private ToggleGroup sexo;

    private Integer ger;
    private Double get;
    @javafx.fxml.FXML
    private RadioButton radioMujer;
    @javafx.fxml.FXML
    private Button btnInforme;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboActividad.getItems().add("Sedentario");
        comboActividad.getItems().add("Moderado");
        comboActividad.getItems().add("Activo");
        comboActividad.getItems().add("Muy Activo");
        comboActividad.getSelectionModel().selectFirst();




    }

    @javafx.fxml.FXML
    public void guardar(ActionEvent actionEvent) {

        if(txtNombre.getText() != null &&
        txtEdad.getText() != null &&
        txtPeso != null &&
        txtTalla.getText() != null &&
        sexo.getSelectedToggle() != null) {

            calcularGER();
            calcularGET();

            lblMensaje.setText("El cliente " + txtNombre.getText() + " tiene un GER de " + Math.round(ger) + " y un GET de " + Math.round(get));
        } else {
            lblMensaje.setText("Faltan cambios por rellenar !!!!!");


        }

    }

    private void calcularGET() {
        String actividad = comboActividad.getSelectionModel().getSelectedItem();

        if (sexo.getSelectedToggle().equals(radioHombre)) {

            switch (actividad) {
                case "Sedentario":
                    get = ger * 1.3;
                    break;
                case "Moderado":
                    get = ger * 1.6;
                    break;
                case "Activo":
                    get = ger * 1.7;
                    break;
                case "Muy Activo":
                    get = ger * 2.1;
                    break;
            }
        }else {

            switch (actividad) {
                case "Sedentario":
                    get = ger * 1.3;
                    break;
                case "Moderado":
                    get = ger * 1.5;
                    break;
                case "Activo":
                    get = ger * 1.6;
                    break;
                case "Muy Activo":
                    get = ger * 1.9;
                    break;
            }

        }
    }

    private void calcularGER() {
        if(sexo.getSelectedToggle().equals(radioHombre)) {

            double peso = Double.parseDouble(txtPeso.getText());

            int talla = (int) Double.parseDouble(txtTalla.getText());

            double edad = Double.parseDouble(txtEdad.getText());

            ger = (int) ((66.473 + (13.751* peso)) + (5.0033 * talla) - (6.755 * edad));
            lblMensaje.setText("El cliente " + txtNombre.getText() + " tiene un GER de " + ger);

        }else{

            double peso = Double.parseDouble(txtPeso.getText());

            int talla = (int) Double.parseDouble(txtTalla.getText());

            double edad = Double.parseDouble(txtEdad.getText());

            ger = (int) ((655.0955+ (9.463 * peso)) + (1.8486 * talla) - (4.6756 * edad));

        }
    }

    @javafx.fxml.FXML
    public void exportar(ActionEvent actionEvent) throws JRException, SQLException {

        c = DriverManager.getConnection("jdbc:mysql://localhost/ad", "root", "");

        hm = new HashMap<>();


        try {
            jasperPrint = JasperFillManager.fillReport("src/main/resources/ExamenFinal.jasper",hm,c);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

        JRViewer viewer = new JRViewer(jasperPrint);
        JFrame frame = new JFrame("informe");
        frame.getContentPane().add(viewer);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);

        JRPdfExporter exp = new JRPdfExporter();
        exp.setExporterInput(new SimpleExporterInput(jasperPrint));
        exp.setExporterOutput(new SimpleOutputStreamExporterOutput("Informe.pdf"));
        exp.setConfiguration(new SimplePdfExporterConfiguration());
        exp.exportReport();



    }
}
