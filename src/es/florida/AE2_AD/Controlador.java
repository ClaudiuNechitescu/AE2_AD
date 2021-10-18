package es.florida.AE2_AD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import es.florida.AE2_AD.*;

public class Controlador {
	private Modelo modelo;
	private Vista vista;
	private ActionListener actionListenerBtnReemplazar, actionListenerBtnBuscar;
	
	Controlador(Modelo modelo, Vista vista,String ruta) throws IOException{
		this.modelo=modelo;
		this.vista=vista;
		control(ruta);
	}
	private void control(String fitx) throws IOException {
		String text = this.modelo.getText(fitx);
		this.vista.getTextAreaOriginal().setText(text);
		
		
		actionListenerBtnBuscar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String texto=vista.getTextFieldBuscar().getText();
				Integer buscar = modelo.buscar(texto, text);
				JOptionPane.showMessageDialog(null,buscar!=0?texto+" apareix "+buscar+" vegades":"Error");
			}
		};
		vista.getBtnBuscar().addActionListener(actionListenerBtnBuscar);
		actionListenerBtnReemplazar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String texto=vista.getTextFieldReemplazar().getText().toLowerCase();
				String busqueda =vista.getTextFieldBuscar().getText().toLowerCase();
				vista.getTextAreaModificado().setText(modelo.reemplazar(busqueda,texto,text));
				
			}
		};
		vista.getBtnReemplazar().addActionListener(actionListenerBtnReemplazar);
	}
}
