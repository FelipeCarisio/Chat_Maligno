/**
@author Felipe Carisio, Ivan Knobel, Jo�o Augusto

Classe servidor
*/

import java.io.*;
import java.net.*;

public class Servidor
{
	/**
	Main da classe
	*/
	public static void main(String[] args)
	{

            /**
            Dentro deste try ser� criada a sala
            */
		try
		{
			Salas salas = new Salas();

			ServerSocket pedido = new ServerSocket(12321); // repassar os parametros

                        System.out.println("Funcionando!");
                        
			for(;;)
			{
				Socket conexao = pedido.accept();
				CuidadoraDeUsuario tratadoraDeUsuario = new CuidadoraDeUsuario(conexao, salas);
				tratadoraDeUsuario.start();
			}
		}
		catch(Exception err)
		{
			System.err.println(err);
		}
	}
}