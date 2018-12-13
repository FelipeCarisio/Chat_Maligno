import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.util.ArrayList;

public class Cliente //instancia janela
{
	public static void main(String[] args)
	{
		try
		{
			for(;;)
			{
				Socket conexao = new Socket("177.220.18.107",12321);   //ip e porta
				//colocar um receptor pra enviar pra janela
				JanelaChat janela = new JanelaChat(conexao);


				ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
				Coisa recebido = null;
				for(;;)
				{
					recebido = (Coisa)receptor.readObject();

					if(recebido instanceof SalasDisponiveis)
					   janela.exibirSalas(((SalasDisponiveis)recebido).getSalas());

						else if(recebido instanceof AvisoDeSalaCheia)
							janela.jogaErro("A sala escolhida está cheia!");

							else if(recebido instanceof AvisoDeNomeJaExiste)
								janela.jogaErro("A sala escolhida é inválida!");

								else
									break;
				}

				recebido = (Coisa)receptor.readObject();

				if(recebido instanceof UsuarioDisponivel)
				{
					janela.iniciaConversa();

					for(;;)
					{
						recebido = (Enviavel)receptor.readObject();

						if(recebido instanceof Mensagem)
						{
							if(((Mensagem)recebido).getDestinatario().equals(""))
							{
								System.out.println(((Mensagem)recebido).getRemetente());
								janela.mostra(((Mensagem)recebido).getMensagem(), ((Mensagem)recebido).getRemetente());
							}
							else
							{
								System.out.println("Privada");
								janela.mostraPriv(((Mensagem)recebido).getMensagem(), ((Mensagem)recebido).getRemetente());
								System.out.println("Enviada para mostrar");
							}
						}

						else if(recebido instanceof AvisoDeEntradaNaSala || recebido instanceof AvisoDeSaidaDaSala )
						{
							janela.exibeAvisoMovimento(((AvisoDeEntradaNaSala)recebido).toString());
						}

						else if(recebido instanceof PedidoParaSairDaSala)
							break;
					}

					janela.fecharTudo();
					receptor.close();
				}
			}

	    }
	    catch(Exception err)
	    {
			System.err.println(err.getMessage());
		}
	}
}