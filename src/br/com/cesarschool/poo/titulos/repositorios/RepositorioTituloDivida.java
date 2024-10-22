package br.com.cesarschool.poo.titulos.repositorios;

import br.com.cesarschool.poo.titulos.entidades.Acao;
import br.com.cesarschool.poo.titulos.entidades.TituloDivida;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class RepositorioTituloDivida {

	private static final String FILE_NAME = "TituloDivida.txt";

	// incluir um novo t�tulo de d�vida no arquivo NAO � PERMITIDIO
	public boolean incluir(TituloDivida tituloDivida) {
		if (buscar(tituloDivida.getIdentificador()) != null) {
			return false; // Identificador j� existe
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
			writer.write(formatTituloDivida(tituloDivida) + "\n");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// MEtodo para alterar um t�tulo de d�vida existente
	public boolean alterar(TituloDivida tituloDivida) {
		List<String> linhas = new ArrayList<>();
		boolean encontrado = false;

		// L� todas as linhas do arquivo e substitui a linha com o identificador correspondente
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				String[] dados = linha.split(";");
				int id = Integer.parseInt(dados[0]);

				if (id == tituloDivida.getIdentificador()) {
					linhas.add(formatTituloDivida(tituloDivida)); // Substitui a linha
					encontrado = true;
				} else {
					linhas.add(linha); // Mant�m as outras linhas
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// Se o identificador foi encontrado, reescreve o arquivo com as altera��es
		if (encontrado) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
				for (String linha : linhas) {
					writer.write(linha + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		return false; // Identificador n�o encontrado
	}

	// Metodo para excluir um t�tulo de d�vida do arquivo
	public boolean excluir(int identificador) {
		List<String> linhas = new ArrayList<>();
		boolean encontrado = false;

		// L� todas as linhas e remove a linha com o identificador correspondente
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				String[] dados = linha.split(";");
				int id = Integer.parseInt(dados[0]);

				if (id == identificador) {
					encontrado = true; // Identificador encontrado, n�o adiciona a linha
				} else {
					linhas.add(linha); // Mant�m as outras linhas
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		// Reescreve o arquivo com as linhas restantes se o identificador foi encontrado
		if (encontrado) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
				for (String linha : linhas) {
					writer.write(linha + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		return false; // Identificador n�o encontrado
	}

	// Metodo para buscar um t�tulo de d�vida por identificador
	public TituloDivida buscar(int identificador) {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String linha;
			while ((linha = reader.readLine()) != null) {
				String[] dados = linha.split(";");
				int id = Integer.parseInt(dados[0]);

				if (id == identificador) {
					// Retorna um objeto TituloDivida a partir dos dados lidos
					return new TituloDivida(id, dados[1], LocalDate.parse(dados[2]), Double.parseDouble(dados[3]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null; // Identificador n�o encontrado
	}

	// Pra ficar mais facil de manipular os dados que sao pegos, eu fiz um metodo pra isso (gpt sugeriu kkkkkkkkkk)
	private String formatTituloDivida(TituloDivida tituloDivida) {
		return tituloDivida.getIdentificador() + ";" + tituloDivida.getNome() + ";"
				+ tituloDivida.getDataDeValidade() + ";" + tituloDivida.getTaxaJuros();
	}
}
