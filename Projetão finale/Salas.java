/**
@author Felipe Carisio, Ivan Knobel, João Augusto

Classe salas
*/

import java.util.ArrayList;
import bd.daos.*;
import bd.bdos.*;

public class Salas
{
    protected ArrayList<Sala> salas;

	/**
    Construtor sem parametros
    @throws se não houver salas no banco
	*/
	 public Salas() throws Exception
	 {
	    ArrayList<bd.bdos.Sala> salasBanco = bd.daos.Salas.getSalas();
	    if(salasBanco.isEmpty())
     	   throw new Exception("Não há salas");
	    this.salas = new ArrayList<Sala>();
	    for(int i = 0; i < salasBanco.length ; i++)
	    {
	      Sala ref = new Sala(salasBanco.get(i).getNome(),salasBanco.get(i).getCapacidade());
	  	  this.salas.add(ref);
	  	}
	 }

     /**
     Getter de salas
     @throws se o parâmetro for nulo
     */
     public String[] getSalas() throws Exception
	 {
		  if(this.salas.isEmpty())
			 throw new Exception("não há salas");
		  String[] nomesSalas = new String[this.salas.size()];
		  for(int i = 0; i < this.salas.size(); i++)
			  {
				  nomesSalas[i] = this.salas.get(i).getNome();
			  }
		  return nomesSalas;
	 }

    /**
    Método que verifica se existe uma sala
    @param nom é o nome da sala a ser verificada
    @throws se não houver salas e se o parametro for nulo
    */
	public int existeSala(String nom) throws Exception
	{
	  if(nom == null)
	     throw new Exception("parametro nulo");

	  int local = -1;
	  if(this.salas.isEmpty())
			 throw new Exception("não há salas");

	  for(int i = 0; i < this.salas.size(); i++)
	  {
		  if (this.salas.get(i).getNome() == nom)
			  local = i;
	  }
	  return local;
	 }

    /**
    Getter de sala
    @param nom é o nome da sala
    @throws se o paranetro for nulo ou se a sala não existir
    */
	 public Sala getSala(String nom) throws Exception
	 {
	   if(nom == null)
	      throw new Exception("parametro nulo");

	   int local = existeSala(nom);
	   if(local > -1)
			 return this.salas.get(local);
		  else
	  throw new Exception("essa sala não existe");
	 }

    /**
    equals da classe
    */
	 public boolean equals(Object x)
	 {
	  if(x == this)
		return true;
	  if(x == null)
		return false;
	  if(x.getClass() != this.getClass())
		return false;
	  Salas ret = (Salas)x;
	  if (this.salas.size() > 0)
	  {
		  for(int i = 0; i < this.salas.size(); i++)
		  {
		   if(!ret.salas.contains(this.salas.get(i)))
			 return false;
		  }
	  }
	   return true;
	 }

    /**
    hashCode da classe
    */
	 public int hashCode()
	 {
	   int ret = 425;

	   for(int i = 0; i < this.salas.size() ; i++)
	   {
			  ret = ret * 3 + this.salas.get(i).hashCode();
	   }
		  return ret;
	 }

    /**
    toString da classe
    */
	 public String toString()
	 {
	   String ret;
	   if(this.salas.size() == 0)
			 ret = "não há nenhuma sala";
	  else
	 {
			ret = "as salas existentes são:" + "\n";
			for(int i = 0; i < this.salas.size(); i++)
			{
				  ret += this.salas.get(i).getNome() + "\n";
			}
		}
		return ret;
	 }
}