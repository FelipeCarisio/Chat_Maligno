public class EscolhaDeSala implements Coisa
{
 private String conteudo;

 	public EscolhaDeSala(String salaSelecionada) throws Exception
 	{
 		if(salaSelecionada != null)
 		   conteudo = salaSelecionada;
 		else
 		    throw new Exception("nome da sala Vazio");
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

        EscolhaDeSala ret = (EscolhaDeSala)x;
 		if(this.conteudo != ret.conteudo)
 		return false;

 		return true;
	}
}