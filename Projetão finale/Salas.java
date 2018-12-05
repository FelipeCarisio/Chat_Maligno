public class Salas
{
  protected ArrayList<Sala> salas;

  public Salas()
  {
	  ArrayList<Sala> = bd.Salas.getSalas();
  }

  public String[] getSalas() throws Exception
  {
	      if(this.salas.isEmpty())
	         throw new Exception("não há salas");

	  	  String[this.salas.size()] nomesSalas;
	  	  for(int i = 0, i < this.salas.size(); i++)
	  	  	  {
	  	  		  nomesSalas[i] = this.salas.get(i).getNome();
	  	      }
	      return nomesSalas;
  }

  public int existeSala(String nom) throws Exception
    {
  	  int local = -1;
  	  if(this.salas.isEmpty())
	         throw new Exception("não há salas");

  	  for(int i = 0, i < this.salas.size(); i++)
  	  {
  		  if (this.salas.get(i).getNome() == nom)
  		      local = i;
  	  }
  	  return local;
  }

  public Sala getSala(String nom)
  {
     int local = existeSala(nom);
      if(local > -1)
	 	     return this.salas.get(local);
	 	  else
	  throw new Exception("essa sala não existe");
  }
}