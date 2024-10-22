package br.com.cesarschool.poo.titulos.repositorios;


import br.com.cesarschool.poo.titulos.entidades.EntidadeOperadora;
import java.io.*;
import java.util.*;

public class RepositorioEntidadeOperadora {

    private final String arquivo = "EntidadeOperadora.txt";

    // Create - Adiciona uma nova EntidadeOperadora
    public boolean create(EntidadeOperadora entidade) {
        if (read(entidade.getIdentificador()) != null) {
            return false; // Se a entidade já existe, não adiciona
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(entidade.getIdentificador() + ";" +
                    entidade.getNome() + ";" +
                    entidade.getAutorizadoAcao() + ";" + // Agora é boolean
                    entidade.getSaldoAcao() + ";" +
                    entidade.getSaldoTituloDivida());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read - Lê e retorna uma EntidadeOperadora pelo identificador
    public EntidadeOperadora read(long identificador) {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (Long.parseLong(dados[0]) == identificador) {
                    return new EntidadeOperadora(
                            Long.parseLong(dados[0]),
                            dados[1],
                            Boolean.parseBoolean(dados[2]), // Conversão para boolean
                            Double.parseDouble(dados[3]),
                            Double.parseDouble(dados[4])
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Se a entidade não for encontrada
    }

    // Read - Imprime todas as EntidadeOperadora no arquivo
    public void readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update - Atualiza uma EntidadeOperadora existente
    public boolean update(EntidadeOperadora entidadeAtualizada) {
        List<EntidadeOperadora> entidades = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                long id = Long.parseLong(dados[0]);
                if (id == entidadeAtualizada.getIdentificador()) {
                    entidades.add(entidadeAtualizada); // Atualiza a entidade
                    found = true;
                } else {
                    entidades.add(new EntidadeOperadora(
                            id, dados[1], Boolean.parseBoolean(dados[2]), // Conversão para boolean
                            Double.parseDouble(dados[3]), Double.parseDouble(dados[4])
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (found) {
            writeAll(entidades); // Reescreve o arquivo com a lista atualizada
            return true;
        }
        return false; // Se a entidade não foi encontrada
    }

    // Delete - Remove uma EntidadeOperadora existente
    public boolean delete(long identificador) {
        List<EntidadeOperadora> entidades = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                long id = Long.parseLong(dados[0]);
                if (id != identificador) {
                    entidades.add(new EntidadeOperadora(
                            id, dados[1], Boolean.parseBoolean(dados[2]), // Conversão para boolean
                            Double.parseDouble(dados[3]), Double.parseDouble(dados[4])
                    ));
                } else {
                    found = true; // Se encontrou a entidade, não a adiciona na nova lista
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (found) {
            writeAll(entidades); // Reescreve o arquivo sem a entidade deletada
            return true;
        }
        return false; // Se a entidade não foi encontrada
    }

    // Metodo auxiliar para reescrever todas as entidades no arquivo
    private void writeAll(List<EntidadeOperadora> entidades) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (EntidadeOperadora entidade : entidades) {
                writer.write(entidade.getIdentificador() + ";" +
                        entidade.getNome() + ";" +
                        entidade.getAutorizadoAcao() + ";" + // Agora é boolean
                        entidade.getSaldoAcao() + ";" +
                        entidade.getSaldoTituloDivida());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}