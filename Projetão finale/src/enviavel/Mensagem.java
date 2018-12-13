/**
@author Felipe Carisio, Ivan Knobel, Jo�o Augusto
*/
package enviavel;

import enviavel.Coisa;

public class Mensagem implements Coisa
{
    private String conteudo;
    private String emissor, remetente = null;



    /**
    Construtor da classe
    @param msg � o conte�do da mensagem
    @param nom � o nome da mensagem
    @throws lan�a exce��o se algum dos par�metros for nulo
    */
    public Mensagem(String msg, String nom)throws Exception
    {
        if(msg != null && nom != null)
        {
           this.conteudo = msg;
           this.emissor = nom;
        }
        else
            throw new Exception("Mensagem mal estruturada sem conteudo");
    }

    /**
    Construtor com apenas conteudo
    @param msg � o conte�do da mensagem
    @throws Exception lan�a exce��o se o par�metro for nulo
    */
    public Mensagem(String msg, String nom, String reme)throws Exception
    {
        if(msg != null || nom != null || reme != null)
        {
            this.conteudo = msg;
            this.emissor = nom;
            this.remetente = reme;
        }
        else
            throw new Exception("Mensagem mal estruturada sem conteudo");
    }

    /**
    Getter de nome
    */
    public String getEmissor()
    {
        return this.emissor;
    }
    
    /**
    Getter de nome
    */
    public String getConteudo()
    {
        return this.conteudo;
    }
    
    /**
    Getter de nome
    */
    public String getRemetente()
    {
        return this.remetente;
    }

    /**
    hashCode da classe
    */
    public int hashCode()
    {
        int ret = 3 * 13 + conteudo.hashCode();
        return ret;
    }

    /**
    toString da classe
    */
    public String toString()
    {
        String reme = "";
        if(this.remetente != null)
           reme = "para: " + this.remetente;
        
        return this.emissor + ": " + reme + ": " + this.conteudo;
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

            Mensagem ret = (Mensagem)x;
            if(this.conteudo != ret.conteudo)
            return false;

            return true;
    }

}