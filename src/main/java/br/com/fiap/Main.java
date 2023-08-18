package br.com.fiap;

import br.com.fiap.domain.entity.Area;
import br.com.fiap.domain.entity.Documento;
import br.com.fiap.domain.entity.Pessoa;
import br.com.fiap.domain.entity.TipoDocumento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("maria-db");
        EntityManager manager = factory.createEntityManager();

//        addArea(manager);

//        addTipoDocumento(manager);

//        addPessoa(manager);

//        addDocumento(manager);


        manager.close();
        factory.close();
    }

    private static void addDocumento(EntityManager manager) {
        List<Pessoa> pessoas = manager.createQuery("FROM Pessoa ").getResultList();
        Pessoa pessoaSelecionada = (Pessoa) JOptionPane.showInputDialog(
                null,
                "Selecione uma pessoa",
                "Seleção de Pessoas",
                JOptionPane.QUESTION_MESSAGE,
                null,
                pessoas.toArray(),
                pessoas.get(0)
        );

        List<TipoDocumento> tipos = manager.createQuery("FROM TipoDocumento ").getResultList();

        TipoDocumento tipoSelecionado = (TipoDocumento) JOptionPane.showInputDialog(
                null,
                "Selecione um Tipo de Documento",
                "Seleção de Tipo de Documento",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos.toArray(),
                tipos.get( 0 )
        );

        String numero = JOptionPane.showInputDialog("Número do documento");

        var doc = new Documento();
        doc.setPessoa(pessoaSelecionada).setTipo(tipoSelecionado).setNumero(numero);

        manager.getTransaction();
        manager.persist(doc);
        manager.getTransaction().commit();

        System.out.println(doc);
    }

    private static void addPessoa(EntityManager manager) {
        boolean salvou = false;
        boolean dataValida = false;

        do {
            var pessoa = new Pessoa();
            String nome = JOptionPane.showInputDialog("Nome da pessoa");
            String nascimento = JOptionPane.showInputDialog("Informe a data de nascimento no formato DD/MM/YYYY");

            int dia = Integer.parseInt(nascimento.substring(0, 2));
            int mes = Integer.parseInt(nascimento.substring(3, 5));
            int ano = Integer.parseInt(nascimento.substring(6, 10));

            pessoa.setNome(nome).setNascimento(LocalDate.of(ano, mes, dia));

            try {
                manager.getTransaction().begin();
                manager.persist(pessoa);
                manager.getTransaction().commit();

                System.out.println(pessoa);

                salvou = true;
            } catch (Exception ex) {
                String erro = """
                        ERRO!

                        NÃO FOI POSSIVEL SALVAR OS DADOS: """ + ex.getMessage() + """

                        MOTIVO: """ + ex.getLocalizedMessage();

                JOptionPane.showMessageDialog(null, erro);


            }
        } while (!salvou);
    }

    private static void addTipoDocumento(EntityManager manager) {
        boolean salvou = false;

        do {
            var tp = new TipoDocumento();
            String nome = JOptionPane.showInputDialog("Nome do Tipo de Documento");
            String descricao = JOptionPane.showInputDialog("Descrição");

            tp.setNome(nome).setDescricao(descricao);

            try {
                manager.getTransaction().begin();
                manager.persist(tp);
                manager.getTransaction().commit();

                System.out.println(tp);

                salvou = true;
            } catch (Exception ex) {
                String erro = """
                        ERRO!

                        NÃO FOI POSSIVEL SALVAR OS DADOS: """ + ex.getMessage() + """

                        MOTIVO: """ + ex.getLocalizedMessage();

                JOptionPane.showMessageDialog(null, erro);
            }
        } while (!salvou);
    }

    private static void addArea(EntityManager manager) {
        boolean salvou = false;

        do {
            var area = new Area();
            String nome = JOptionPane.showInputDialog("Nome da área");
            String descricao = JOptionPane.showInputDialog("Descrição");

            area.setNome(nome).setDescricao(descricao);

            try {
                manager.getTransaction().begin();
                manager.persist(area);
                manager.getTransaction().commit();

                System.out.println(area);

                salvou = true;
            } catch (Exception ex) {
                String erro = """
                        ERRO!

                        NÃO FOI POSSIVEL SALVAR OS DADOS: """ + ex.getMessage() + """

                        MOTIVO: """ + ex.getLocalizedMessage();

                JOptionPane.showMessageDialog(null, erro);


            }
        } while (!salvou);
    }


}