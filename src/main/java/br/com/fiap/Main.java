package br.com.fiap;

import br.com.fiap.domain.entity.Area;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle");
        EntityManager manager = factory.createEntityManager();

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
            } catch (Exception ex) {
                String erro = """
                        ERRO!
                                            
                        NÃO FOI POSSIVEL SALVAR OS DADOS: """ + ex.getMessage() + """
                                            
                        MOTIVO: """ + ex.getLocalizedMessage();

                JOptionPane.showMessageDialog(null, erro);

                salvou = true;
            }
        } while (!salvou);

        manager.close();
        factory.close();
    }
}