package br.com.webacademy;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DiscenteDAO discenteDAO = new DiscenteDAO();

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 0 -> salvarDiscente();
                case 1 -> buscarTodosDiscentes();
                case 2 -> buscarDiscentePorId();
                case 3 -> atualizarDiscente();
                case 4 -> excluirDiscente();
                case 5 -> System.exit(0);
                default -> System.out.println("Opção Inválida");
            }
        } while (opcao != 5);
    }

    private static void exibirMenu() {
        System.out.println("\n### Menu de Operações ###");
        System.out.println("0. Salvar novo discente");
        System.out.println("1. Buscar todos discentes");
        System.out.println("2. Buscar discente por ID");
        System.out.println("3. Atualizar discente");
        System.out.println("4. Excluir discente");
        System.out.println("5. Sair do programa");
        System.out.print("Escolha uma opção: ");
    }

    private static void salvarDiscente() {
        System.out.println("\n### Criar Novo Discente ###");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("PeriodoAtual: ");
        int periodoAtual = Integer.parseInt(scanner.nextLine());
        System.out.print("Matricula: ");
        Double matricula = Double.parseDouble(scanner.nextLine());

        Discente discente = new Discente(nome, periodoAtual, matricula);
        try {
            discenteDAO.salvar(discente);
            System.out.println("Discente criado com sucesso!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void buscarTodosDiscentes() {
        System.out.println("\n### Buscar Todos os Discentes ###");
        try {
            List<Discente> discentes = discenteDAO.buscarTodos();
            if (discentes != null && !discentes.isEmpty()) {
                System.out.println("Lista de Discentes:");
                for (Discente d : discentes) {
                    System.out.printf("ID: %d, Nome: %s, PeriodoAtual: %d, Matricula: %.0f%n",
                            d.id(), d.nome(), d.periodoAtual(), d.matricula());
                }
            } else {
                System.out.println("Nenhum discente encontrado.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void buscarDiscentePorId() {
        System.out.println("\n### Buscar Discente por ID ###");
        System.out.print("Digite o ID do discente: ");
        Long id = Long.parseLong(scanner.nextLine());
        try {
            Discente d = discenteDAO.buscarPorId(id);
            if (d != null) {
                System.out.println("Discente encontrado:");
                System.out.printf("ID: %d, Nome: %s, PeriodoAtual: %d, Matricula: %.0f%n",
                        d.id(), d.nome(), d.periodoAtual(), d.matricula());
            } else {
                System.out.println("Discente não encontrado.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void atualizarDiscente() {
        System.out.println("\n### Atualizar Discente ###");
        System.out.print("Digite o ID do discente que deseja atualizar: ");
        Long id = Long.parseLong(scanner.nextLine());
        try {
            Discente dExistente = discenteDAO.buscarPorId(id);
            if (dExistente != null) {
                System.out.print("Novo nome (atual: " + dExistente.nome() + "): ");
                String nome = scanner.nextLine();
                System.out.print("Novo periodoAtual (atual: " + dExistente.periodoAtual() + "): ");
                int periodoAtual = Integer.parseInt(scanner.nextLine());
                System.out.print("Nova matricula (atual: " + String.format("%.0f", dExistente.matricula()) + "): ");
                Double matricula = Double.parseDouble(scanner.nextLine());

                discenteDAO.atualizar(new Discente(id, nome, periodoAtual, matricula));
                System.out.println("Discente atualizado com sucesso!");
            } else {
                System.out.println("Discente não encontrado.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void excluirDiscente() {
        System.out.println("\n### Excluir Discente ###");
        System.out.print("Digite o ID do discente que deseja excluir: ");
        Long id = Long.parseLong(scanner.nextLine());
        try {
            if (discenteDAO.buscarPorId(id) != null) {
                discenteDAO.excluir(id);
                System.out.println("Discente excluído com sucesso!");
            } else {
                System.out.println("Discente não encontrado.");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}