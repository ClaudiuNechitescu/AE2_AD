package es.florida.AE2_AD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.text.Normalizer;

import javax.swing.JOptionPane;

public class Modelo {
	private String missatge = "";

	Modelo() throws IOException {

	}

	/**
	 * Mètode que capta el text d'un fitxer
	 * 
	 * @author Claudiu Andrei Nechitescu
	 * @param fitx El nom del fitxer del qual s'obté el text
	 * @return El text obtingut
	 */
	public String getText(String fitx) throws IOException {
		try {
			File fitxer = new File(fitx);
			FileReader fr = new FileReader(fitxer);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			do {
				linea = br.readLine();
				missatge += linea != null ? linea + "\n" : "";
			} while (linea != null);
			fr.close();
			return missatge;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return missatge;
		}
	}

	/**
	 * Mètode que busca el nombre d'aparicions d'una paraula en un text
	 * 
	 * @author Claudiu Andrei Nechitescu
	 * @param buscar La paraula a buscar
	 * @param texto  Text on s'ha de buscar la paraula
	 * @return El nombre d'aparicions de la paraula
	 */
	public Integer buscar(String buscar, String texto) {
		try {
			if (buscar.isEmpty()) {
				throw new Exception("Has d'introduir una paraula a buscar");
			}
			String[] palabras = texto.split(" ");
			Integer contador = 0;
			for (String palabra : palabras) {
				palabra = palabra.toLowerCase().replaceAll("[^0-9A-Za-zÀ-ÿ '-]*", "");
				palabra = Normalizer.normalize(palabra, Normalizer.Form.NFD);
				palabra = palabra.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
				if (palabra.equals(buscar)) {
					contador++;
				}
			}
			return contador;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return 0;
		}
	}

	/**
	 * Mètode que reemplaça una paraula donada amb una altra
	 * 
	 * @author Claudiu Andrei Nechitescu
	 * @param buscar     Paraula a reemplaçar
	 * @param reemplazar Paraula nova
	 * @param texto      Text on s'ha de buscar
	 * @return El text modificat
	 */
	public String reemplazar(String buscar, String reemplazar, String texto) {
		String reemplazado = texto;

		try {
			if (reemplazar.isEmpty()) {
				throw new Exception("Has d'introduir una paraula per la qual reemplaçar");
			}

			if (buscar.isEmpty()) {
				throw new Exception("Has d'introduir una paraula a reemplaçar");
			}

			texto = texto.replaceAll("\n", "\n ");

			String[] palabras = texto.split(" ");
			//Definim caracters especials que poden interferir amb la busqueda de la paraula
			List<String> especialsprincipi = new ArrayList<String>(Arrays.asList("¡", "¿", "-", "…", "."));
			List<String> especialsfinal = new ArrayList<String>(Arrays.asList("!", "?", "-", ".", ",", "…"));
			//Definim una referència perquè es restablisca la paraula nova al iniciar el bucle
			String referencia = reemplazar;
			Integer contador = 0;
			for (int i = 0; i < palabras.length; i++) {
				String palabra = palabras[i];
				reemplazar = referencia;
				//Normalitzem la paraula, eliminant accents y signes especials
				if (Normalizer.normalize(palabra, Normalizer.Form.NFD).replaceAll("[^0-9A-Za-zÀ-ÿ '-]*", "")
						.toLowerCase().equals(buscar.toLowerCase())) {
					contador++;
					//Si la paraula acaba en algú dels caracters especials
					if (especialsfinal.contains(palabra.substring(palabra.length() - 1))) {
						//Busquem si la paraula acaba amb ... , que no es el mateix que …
						if (palabra.endsWith("...")) {
							reemplazar += "...";
						} else {
							reemplazar += palabra.charAt(palabra.length() - 1);
						}
						//Si el primer caracter es majúscula
						if (Character.isUpperCase(palabra.charAt(0))) {
							palabra = reemplazar.substring(0, 1).toUpperCase() + reemplazar.substring(1);
						} else {
							palabra = reemplazar;
						}

					}
					//Si la paraula comença amb algú dels caracters especials
					if (especialsprincipi.contains(palabra.substring(0, 1))) {
						//Si la paraula comença amb ...
						if (palabra.substring(0, 3).equals("...")) {
							//Si la primera lletra es majúscula
							if (Character.isUpperCase(palabra.charAt(3))) {
								reemplazar = reemplazar.substring(0, 1).toUpperCase() + reemplazar.substring(1);
							}
							palabra = "..." + reemplazar;

						} else {
							reemplazar = palabra.charAt(0) + reemplazar;
							if (Character.isUpperCase(palabra.charAt(1))) {
								palabra = reemplazar.substring(0, 1) + reemplazar.substring(1, 2).toUpperCase()
										+ reemplazar.substring(2);
							} else {
								palabra = reemplazar;
							}
						}
					} else {
						if (Character.isUpperCase(palabra.charAt(0))) {
							palabra = reemplazar.substring(0, 1).toUpperCase() + reemplazar.substring(1);
						} else {
							palabra = reemplazar;
						}

					}
				}
				palabras[i] = palabra;
			}
			if (contador != 0) {
				//Si s'ha trobat alguna paraula coincident
				reemplazado = String.join(" ", palabras);
				File fitxernou = new File("TextReemplazado-" + buscar + " " + referencia + "-.txt");
				//Si el fitxer no existeix el crea
				if(!fitxernou.exists()) {
					FileWriter fr = new FileWriter(fitxernou, true);
					BufferedWriter bw = new BufferedWriter(fr);
					bw.write(reemplazado);
					bw.close();
					fr.close();
					fitxernou.createNewFile();
				}
				else {
					throw new Exception("Aquest reemplaçament ja s'ha realitzat, comprova els fitxers");
				}
			} else {
				throw new Exception("La paraula " + buscar + " no existeix");
			}
			return reemplazado;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return reemplazado;
		}
	}

}
