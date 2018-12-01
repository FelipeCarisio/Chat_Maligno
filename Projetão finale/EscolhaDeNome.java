public class EscolhaDeNome implements Coisa
{
	private String conteudo;

	public EscolhaDeNome(String NomeSelecionado) throws Exception
	{
		if(NomeSelecionado != null)
		   conteudo = nomeSelecionado;
		else
		    throw new Exception("Nome Vazio");
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