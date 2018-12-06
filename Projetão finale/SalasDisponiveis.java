public class SalasDisponiveis implements Coisa
{
	 private String conteudo;

	 	public SalasDisponiveis(String[] salasDisponiveis) throws Exception
	 	{
	 		if(salasDisponiveis != null)
	 		{
	 		   for(String i : salasDisponiveis)
	 		   this.conteudo += i + "\n";
		    }
	 		else
	 		    throw new Exception("Não há de onde se pegar salas");
	 	}

	 	public int hashCode()
	 	{
	 		int ret = 3 * 13 + conteudo.hashCode();
	 		return ret;
	 	}

	 	public String toString()
	 	{
	 		return this.conteudo;
	 	}

	 	public boolean equals(Object x)
	 	{
	 		if(x == this)
	 		  return true;
	 		if(x == null)
	 		  return false;
	 		if(x.getClass() != this.getClass())
	 		return false;

            SalasDisponiveis ret = (SalasDisponiveis)x;
	 		if(this.conteudo != ret.conteudo)
	 		return false;

	 		return true;
	}
}