/**
@author Felipe Carisio, Ivan Knobel, Jo�o Augusto

Classe cuidadoraDeUsuario
*/

import bd.*;
import bd.daos.*;
import bd.bdos.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class CuidadoraDeUsuario extends Thread
{
    private Socket  conexao;
    private Salas   salas;
    private Usuario usuario;

    /**
    Construtor da classe
    @param conexao � a socket
    @param salas � o vetor de salas
    @throws se algum parametro for nulo
    */
    public CuidadoraDeUsuario (Socket conexao, Salas salas) throws Exception
    {
        if(conexao == null)
          throw new Exception("parametro de conexao nulo");
        if(salas == null)
          throw new Exception("parametro de Salas nulo");

        this.conexao=conexao;
        this.salas  =salas;
    }

    /**
    M�todo para
    1� - interagir com o usu�rio via OOS e OIS ate descobrir o nome da sala em que ele deseja entrar, eventualmente, informando sala cheia
    2� - procurar em salas a sala com o nome desejado
    3� - interagir com o usu�rio via OOS e OIS ate descobrir o nome que ele deseja usar, eventualmente, informando nome invalido ou ja usado
	4� - instanciar o Usuario, fornecendo, conexao, OOS, OIS, nome e sala
    5� - fazer varias vezes this.usuario.envia(new AvisoDeEntradaDaSala(i)), onde i � o nome de algum usuario que ja estava na sala
	6� - fazer varias vezes i.envia(new AvisoDeEntradaDaSala(usuario.getNome()), onde i � o nome de algum usuario que ja estava na sala
    7� - incluir o usuario na sala
    */
    private void preSala(ObjectOutputStream paramOOS,ObjectInputStream paramOIS )
    {
		// declarar e instanciar OOS e OIS
		ObjectOutputStream oos = paramOOS;
		ObjectInputStream ois = paramOIS;

		String[] salasExistentes = this.salas.getSalas();

		oos.writeObject(new SalasDisponiveis(salasExistentes));
		oos.flush();
		for(;;)
		 {
			 coisa recebido = ois.readObject();

			 if(recebido instanceof EscolhaDeNome)
			 {
				 String nomeUsuario = ((EscolhaDeNome)recebido).toString();
				 for(;;)
				 {
					 recebido = ois.readObject();
					 if(recebido instanceof EscolhaDeSala)
						{
							Sala salaSelecionada;
							synchronized(this.salas)
							{
							salaSelecionada = this.salas.getSala(((EscolhaDeSala)recebido).toString());
							}
							boolean estaCheia, jaExiste;
							synchronized(salaSelecionada)
							{
								estaCheia = salaSelecionada.isCheia();
								jaExiste = salaSelecionada.existeUsuario(nomeUsuario);
							}
								if(!estaCheia)
								   if(!jaExiste)
									   {
										   this.usuario = new Usuario(this.conexao, oos, ois,nomeSelecionado,salaSelecionada);
										   String[] usuarios;
										   synchronized(salaSelecionada)
										   {
										   usuarios = salaSelecionada.getUsuarios();
										   for(int i = 0; i < salaSelecionada.getLotacao(); i++)
											 {
												salaSelecionada.getUsuario(i).envia(new AvisoDeEntradaNaSala(Usuario.getNome()));
											 }
										   }
										   for(String nome : usuarios)
										   {
											   this.usuario.envia(new AvisoDeEntradaNaSala(nome));
										   }
										   synchronized(salaSelecionada)
										   {
											   salaSelecionada.incluir(this.usuario);
										   }
											return;
									   }
									 else
									 {
									 oos.writeObject(new AvisoDeUsuarioJaExiste());
									 oos.flush();
									 break;
									 }
							else
							{
								oos.writeObject(new AvisoDeSalaCheia());
								oos.flush();
							}
						}
				 }
			 }
		   }
		}

    /**
    M�todo que ser� executado ap�s o usu�rio entrar na sala e rodar� at� ele sair da sala
    */
    private void conversar()
    {
		Coisa recebido = null;
		Sala salaAtual = this.usuario.getSala();

		 do
		 {
			 recebido = usuario.recebe();
			 if(recebido instanceof Mensagem)
			 {

				 Mensagem novaMsg = (Mensagem)recebido;
				 if(novaMsg.getNome() == null)
				 {
					  synchronized(salaAtual)
					  {
						 for(int i = 0; i < salaAtual.getLotacao(); i++)
						 {
						   if(this.usuario != salaAtual.getUsuario(i))
						      salaAtual.getUsuario(i).envia(novaMsg);
						 }
					  }
				 }
				 else
				 {
					synchronized(salaAtual)
					{
					  if(salaAtual.existeUsuario(novaMsg.getNome()) > -1)
						 salaAtual.getUsuario(novaMsg.getNome()).envia(novaMsg);
					  else
					  this.usuario.envia(new AvisoUsuarioInexistente(novaMsg.getNome()));
					}
				 }
			  }
		}while (!(recebido instanceof PedidoParaSairDaSala));

        synchronized(salaAtual)
        {
              for(int i = 0; i < salaAtual.getLotacao(); i++)
			  {
			    if(this.usuario != salaAtual.getUsuario(i))
			 	  salaAtual.getUsuario(i).envia(new AvisoDeSaidaDaSala(this.usuario.getNome()));
			  }
	    }
	    salaAtual.excluir(this.usuario.getNome());
	    this.usuario.fechaTudo();
	    this.usuario = null;
	}


    /**
    M�todo run personalizado para sobrescrever o padr�o
    */
    public void run ()
    {
	 ObjectOutputStream oos = new ObjectOutputStream(conexao.getOutputStream());
     ObjectInputStream ois = new ObjectInputStream(conexao.getInputStream());
     preSala(oos,ois);
     conversar();
    }
}