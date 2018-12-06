/**
@author Felipe Carisio, Ivan Knobel, João Augusto

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
        Dentro deste try será criada a sala
		*/
		try
		{
			Salas salas = new Salas();

			ServerSocket pedido = new ServerSocket(545); // repassar os parametros

			for(;;)
			{
				Socket conexao = pedido.accept();
				CuidadoraDeUsuario tratadoraDeUsuario = new CuidadoraDeUsuario(conexao, salas);
				tratadoraDeUsuario.start();
			}
		}
		catch(Exception err)
		{
			System.err.println(err.getMessage());
		}
	}
}