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
        if(Salas == null)
          throw new Exception("parametro de Salas nulo");

        this.conexao=conexao;
        this.salas  =salas;
    }

    private void preSala(ObjectOutputStream paramOOS,ObjectInputStream paramOIS )
    {
		// declarar e instanciar OOS e OIS
		        ObjectOutputStream oos = paramOOS);
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
									Sala salaSelecionada
									synchronized(this.salas)
									{
									salaSelecionada = this.salas.getSala(((EscolhaDeSala)recebido).toString())
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
											            salaSelecionada.getUsuario(i).envia(new AvisoDeEntrada(Usuario.getNome()));
													 }
											       }
											       for(String nome : usuarios)
											       {
													   this.usuario.envia(new AvisoDeEntrada(nome));
												   }
		                                           synchronized(salaSelecionada)
		                                           {
													   salaSelecionada.incuir(usuario);
												   }
		                                            return;
											   }
											 else
											 break;
									else

								}
						 }
				     }
			       }
             	}

    public void run ()
    {
	 ObjectOutputStream oos = new ObjectOutputStream(conexao.getOutputStream());
     ObjectInputStream ois = new ObjectInputStream(conexao.getInputStream);
	 Coisa recebidoRun =null;
     do
     {

       preSala(oos,ois);
        do
        {
         recebido = usuario.recebe();
              if(
            // receber mensagens, avisos de entrada na e de saida da sala
            // se for mensagem, pega nela o destinatario, acha o destinatario na sala e manda para ele a mensagem
        }
        while (!(recebido instanceof PedidoParaSairDaSala));

        // remover this.usuario da sala
        // mandar new AvisoDeSaidaDaSala(this.usuario.getNome()) para todos da sala
        this.usuario.fechaTudo();
      }while(!(recebido instanceof PedidoParaEncerrar))
    }
}