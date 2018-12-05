package bd.daos;

import java.sql.*;
import bd.*;
import bd.core.*;
import bd.dbos.*;

public class Salas //DAO
{
    public static ArrayList<Sala> getSalas()
    {
        Sala sala = null;
        ArrayList<Sala> salas = null;

        try
        {
            String sql;
            salas = new ArrayList<Sala>;

            sql = "SELECT * " +
                  "FROM SALAS";
                  
            BDSQLServer.COMANDO.prepareStatement (sql);

            //BDSQLServer.COMANDO.setInt (1, codigo);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            if (!resultado.first())
                throw new Exception ("Nao existem salas");

            do
            {
                sala = new Sala (resultado.getString("NOME"), resultado.getString("CAPACIDADE"));
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