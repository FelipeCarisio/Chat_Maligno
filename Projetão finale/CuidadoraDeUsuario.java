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

    public CuidadoraDeUsuario (Socket conexao, Salas salas) throws Exception
    {
        // validar se conexao e/ou salas ==null, lançando exceção
        if(conexao == null)
          throw new Exception("parametro de conexao nulo");
        if(salas == null)
          throw new Exception("parametro de Salas nulo");

        this.conexao=conexao;
        this.salas  =salas;
    }

    private void preSala(ObjectOutputStream paramOOS,ObjectInputStream paramOIS )
    {
		// declarar e instanciar OOS e OIS
		        ObjectOutputStream oos = paramOOS;
		        ObjectInputStream ois = paramOIS;

		        // interagir com o usr via OOS e OIS ate descobrir o nome da sala em que ele deseja entrar, eventualmente, informando sala cheia
		       // procurar em salas a sala com o nome desejado
	          // interagir com o usr via OOS e OIS ate descobrir o nome que ele deseja usar, eventualmente, informando nome invalido ou ja usado
	         // instanciar o Usuario, fornecendo, conexao, OOS, OIS, nome e sala
	        // fazer varias vezes this.usuario.envia(new AvisoDeEntradaDaSala(i)), onde i é o nome de algum usuario que ja estava na sala
	       // fazer varias vezes i.envia(new AvisoDeEntradaDaSala(usuario.getNome()), onde i é o nome de algum usuario que ja estava na sala
          // incluir o usuario na sala
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

    private void conversar()
    {
		Coisa recebido =null;
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
		        }
        while (!(recebido instanceof PedidoParaSairDaSala));
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

    public void run ()
    {
	 ObjectOutputStream oos = new ObjectOutputStream(conexao.getOutputStream());
     ObjectInputStream ois = new ObjectInputStream(conexao.getInputStream());
     preSala(oos,ois);
     conversar();
    }
}