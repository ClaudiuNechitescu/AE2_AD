package es.florida.AE2_AD;

import java.io.IOException;

public class Principal {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Vista vista = new Vista();
		Modelo modelo = new Modelo();
		Controlador controlador = new Controlador(modelo, vista,"AE02_T1_2_Streams_Groucho.txt");
	}

}
