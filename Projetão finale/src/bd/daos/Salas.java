/**
@author Felipe Carisio, Ivan Knobel, Jo�o Augusto
DAO do projeto
*/

package bd.daos;

import java.util.ArrayList;
import java.sql.*;
import bd.bdos.*;
import bd.*;
import bd.core.*;

public class Salas
{
	/**
	M�todo que devolve um ArrayList de salas
	*/
    public static ArrayList<Sala> getSalas() throws Exception
    {
        Sala sala = null;
        ArrayList<Sala> salas = null;

        try
        {
            String sql;
            salas = new ArrayList<Sala>();

            sql = "SELECT * " +
                  "FROM SALAS";

            BDSQLServer.COMANDO.prepareStatement (sql);

            //BDSQLServer.COMANDO.setInt (1, codigo);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            if (!resultado.first())
                throw new Exception ("Nao existem salas");

            do
            {
                sala = new Sala (resultado.getInt("CAPACIDADE"), resultado.getInt("CODIGO"),resultado.getString("NOME"));
                salas.add(sala);
            }while (resultado.next());

        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar salas");
        }

        return salas;
    }
}