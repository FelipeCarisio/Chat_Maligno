package bd.bdos;
public class Sala
{
	protected int capacidade;
	protected String nome;
	protected int codigo;

	public Sala(int cap, int cod, String nam)
	{
		this.capacidade = cap;
		this.codigo = cod;
		this.nome = nam;
	}

	public String getNome()
	{
		return this.nome;
	}
	public int getCapacidade()
	{
		return this.capacidade;
	}
	public int getCodigo()
	{
		return this.codigo;
	}

	public String toString()
	{
		String ret = "a Sala " + this.nome + " possui uma capacidade de " + this.capacidade + " e seu codigo �: " + this.codigo;
		return ret;
	}

	public int hashCode()
	{
		int ret = this.capacidade + this.codigo;
		ret = ret * 11 + this.nome.hashCode();
		return ret;
	}

	public boolean equals(Object x)
	{
		if(x == this)
		  return true;
		if(x == null)
		   return false;
		if(x.getClass() != this.getClass())
		   return false;

		Sala ret = (Sala)x;

		if(ret.hashCode() != this.hashCode())
		  return false;

		return true;
	}



	// os setters n�o s�o nescess�rios visto que n�o iremos criar nenhuma sala nova, apenas recuperar salas j� existentes
	//como n�o iremos modificar as variaveis, o construtor de clone e o metodo clone tamb�m s�o dispenss�veis
}