public class AvisoDeEntradaNaSala implements Coisa
{
	private String conteudo;

		 	public AvisoDeEntradaNaSala(String nome) throws Exception
		 	{
		 		if(nome != null)
		 		   conteudo = "O usuário " + nome + " entrou na sala";
		 		else
		 		    throw new Exception("nome Vazio");
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

                AvisoDeEntradaNaSala ret = (AvisoDeEntradaNaSala)x;
		 		if(this.conteudo != ret.conteudo)
		 		return false;

		 		return true;
	        }
}