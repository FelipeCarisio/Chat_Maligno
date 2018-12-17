import enviavel.AvisoDeSaidaDaSala;
import enviavel.UsuarioDisponivel;
import enviavel.AvisoDeNomeJaExiste;
import enviavel.PedidoParaSairDaSala;
import enviavel.AvisoDeEntradaNaSala;
import enviavel.AvisoDeSalaCheia;
import enviavel.SalasDisponiveis;
import enviavel.Mensagem;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.util.ArrayList;
import enviavel.*;

public class Cliente //instancia janela
{
	public static void main(String[] args)
	{
            try
            {
                for(;;)
                {
                    Socket conexao = new Socket("localhost",12321);   //ip e porta
                    //colocar um receptor pra enviar pra janela
                    
                    ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
                    ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
                    JanelaChat janela = new JanelaChat(conexao, transmissor, receptor);

                    
                    Coisa recebido = null;
                    
                    for(;;)
                    {
                        recebido = (Coisa)receptor.readObject();

                        if(recebido instanceof SalasDisponiveis)
                           janela.exibirSalas(((SalasDisponiveis)recebido).getSalas());
                        
                            if(recebido instanceof AvisoDeSalaCheia)
                               janela.jogaErro("A sala escolhida est� cheia!");
                            else 
                                if(recebido instanceof AvisoDeNomeJaExiste)
                                   janela.jogaErro("A sala escolhida � inv�lida!");
                                else
                                    while(recebido instanceof AvisoDeEntradaNaSala)
                                    {
                                        janela.exibeAvisoMovimento(((AvisoDeEntradaNaSala)recebido).toString());
                                        recebido = (Coisa)receptor.readObject();
                                    }
                                    if(recebido instanceof UsuarioDisponivel)
                                    break;
                    }
                   
                        janela.iniciaConversa();

                        for(;;)
                        {
                            recebido = (Coisa)receptor.readObject();

                            if(recebido instanceof Mensagem)
                            {
                                    if(((Mensagem)recebido).getDestinatario() == null)
                                    {
                                            System.out.println(((Mensagem)recebido).getEmissor());
                                            janela.mostra(((Mensagem)recebido).getConteudo(), ((Mensagem)recebido).getEmissor());
                                    }
                                    else
                                    {
                                            System.out.println("Privada");
                                            janela.mostra(((Mensagem)recebido).getConteudo(),((Mensagem)recebido).getEmissor(), "Voce");
                                            System.out.println("Enviada para mostrar");
                                    }
                            }

                            else 
                                if(recebido instanceof AvisoUsuarioInexistente)
                                {
                                    janela.jogaErro(((AvisoUsuarioInexistente)recebido).toString());
                                }
                            else
                                if(recebido instanceof AvisoDeEntradaNaSala )
                                {
                                 janela.exibeAvisoMovimento(((AvisoDeEntradaNaSala)recebido).toString());
                                }
                            else
                                 if(recebido instanceof AvisoDeSaidaDaSala )
                                 {
                                  janela.exibeAvisoMovimento(((AvisoDeSaidaDaSala)recebido).toString());
                                 }

                            else 
                                if(recebido instanceof PedidoParaSairDaSala)
                                   break;
                        }

                        janela.fechaTudo();
                        receptor.close();
                    
                }

	    }
	    catch(Exception err)
	    {
		System.err.println(err.getMessage());
	    }
	}
}