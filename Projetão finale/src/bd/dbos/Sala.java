/**
@author Felipe Carisio, Ivan Knobel, João Augusto

DBO do projeto
*/

package bd.bdos;
public class Sala
{
	protected int capacidade;
	protected String nome;
	protected int codigo;

    /**
    Construtor

    @param cap é a capacidade da sala
    @param cod é a código da sala
    @param nam é o nome da sala
    */
	public Sala(int cap, int cod, String nam)
	{
		this.capacidade = cap;
		this.codigo = cod;
		this.nome = nam;
	}

    /**
    Getter do nome
    */
	public String getNome()
	{
		return this.nome;
	}

	/**
	Getter da capacidade
    */
	public int getCapacidade()
	{
		return this.capacidade;
	}

    /**
	Getter do código
    */
	public int getCodigo()
	{
		return this.codigo;
	}

    /**
    Método toString da classe
    */
	public String toString()
	{
		String ret = "a Sala " + this.nome + " possui uma capacidade de " + this.capacidade + " e seu codigo é: " + this.codigo;
		return ret;
	}

    /**
	Método hashCode da classe
    */
	public int hashCode()
	{
		int ret = this.capacidade + this.codigo;
		ret = ret * 11 + this.nome.hashCode();
		return ret;
	}

    /**
	Método equals da classe
    */
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

	// os setters não são nescessários visto que não iremos criar nenhuma sala nova, apenas recuperar salas já existentes
	//como não iremos modificar as variaveis, o construtor de clone e o metodo clone também são dispenssáveis
}