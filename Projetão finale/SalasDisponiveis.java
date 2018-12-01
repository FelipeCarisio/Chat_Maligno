public class SalasDisponiveis implements Coisa
{
	 private String conteudo;

	 	public EscolhaDeSala(ArrayList<sala> salasDisponiveis) throws Exception
	 	{
	 		if(salasDisponiveis != null)
	 		{
	 		   for(int i = 0; i < salasDisponiveis.size();i++)
	 		   conteudo = salasDisponiveis.get(i).getNome() + "\n";
		    }
	 		else
	 		    throw new Exception("Não há de onde se pegar salas");
	 	}

	 	public int hashCode()
	 	{
	 		int ret = 3 * 13 + conteudo.hashcode();
	 		return ret;
	 	}

	 	public String toString()
	 	{
	 		return this.conteudo();
	 	}

	 	public boolean equals(Object x)
	 	{
	 		if(x == this)
	 		  return true;
	 		if(x == null)
	 		  return false;
	 		if(x.getClass() != this.getClass())
	 		return false;

	 		if(this.conteudo != (EscolhaDeNome)x.conteudo)
	 		return false;

	 		return true;
	}
}