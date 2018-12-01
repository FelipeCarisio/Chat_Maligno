public class CuidadoraDeUsuario extends Thread
{
    private Usuario usuario;

    public CuidadoraDeUsuario (Socket conexao, Salas salas) throws Exception
    {
		Sala sala;
		String nome;
		ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
		ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
        // declarar e instanciar OOS e OIS
        transmissor.writeObject(new SalasDisponiveis());
        for(;;)
        {
		  /*pedir sala*/
		  if(receptor.readObject() instanceof EscolhaDeSala)
		  {
			if(salas.existe(EscolhaDeSala.toString())
			{
			 Sala salaTeste = Salas.getSala(EscolhaDeSala.toString());
		     if(!salaTeste.estaCheia())
		     {
			    sala = salaTeste;
		        break();
			 }
		    }
		  }
		}
        // interagir com o usr via OOS e OIS ate descobrir o nome da sala em que ele deseja entrar, eventualmente, informando sala cheia
        // procurar em salas a sala com o nome desejado
        /*pedir nome*/
       for(;;)
	            {
	   		  /*pedir sala*/
	   		     if(receptor.readObject() instanceof EscolhaDeNome)
	   		     {
			    	String nomeTeste = EscolhaDeNome.toString();
	   			    if(nomeTeste.length > 5 || nomeTeste.length < 51)
	   			  {
	   		        if(!sala.existe(nomeTeste))
	   		       {
	   			    nome = nomeTeste;
	   		        break();
	   			   }
	   		      }
	   		     }
		        }

        // interagir com o usr via OOS e OIS ate descobrir o nome que ele deseja usar, eventualmente, informando nome invalido ou ja usado
        this.usuario = new Usuario(conexao,transmissor,receptor,nome,sala);
        // instanciar o Usuario, fornecendo, conexao, OOS, OIS, nome e sala
        synchronized(sala)
        {
        for(int i = 0;i<sala.GetLotacao();i++)
        {
			this.usuario.envia(new AvisoDeEntradaDeSala(sala.selecionaUsuario(i).getNome());
		}
        // fazer varias vezes this.usuario.envia(new AvisoDeEntradaDaSala(i)), onde i é o nome de algum usuario que ja estava na sala
        for(int i = 0;i<sala.GetLotacao();i++)
		        {
					sala.selecionaUsuario(i).envia(new AvisoDeEntradaDeSala(nome);
	        	}
        // fazer varias vezes i.envia(new AvisoDeEntradaDaSala(usuario.getNome()), onde i é o nome de algum usuario que ja estava na sala
        sala.incluir(this.usuario);
	    }
        // incluir o usuario na sala
    }

    public void run ()
    {
        Enviavel recebido=null;

        do
        {
            // receber mensagens, avisos de entrada na e de saida da sala
            // se for mensagem, pega nela o destinatario, acha o destinatario na sala e manda para ele a mensagem
        }
        while (!(recebido instanceof PedidoParaSairDaSala));

        // remover this.usuario da sala
        // mandar new AvisoDeSaidaDaSala(this.usuario.getNome()) para todos da sala
        this.usuario.fechaTudo();
    }
}