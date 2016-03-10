package app.cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import app.Mensagem;
import app.ObservablePattern.ClienteObserver;
import app.janelas.TelaApp;

public class Cliente {
	
	private TelaApp janela;
	private ClienteListener listener;
	
	public Cliente(int porta) {
		super();
		this.janela = new TelaApp();
		this.listener = new ClienteListener(porta, janela);
		Thread tJanela = new Thread(janela);
		tJanela.start();
		Thread tListener = new Thread(listener);
		tListener.start();
		//conectando com o server
		conexaoServer("200.239.139.205");
	}
	
	public void conexaoServer(String ipServidor){
		try {
			//Porta do servidor 6000
			Socket socket = new Socket(ipServidor, 6000);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(new Mensagem(0,new ClienteObserver(InetAddress.getLocalHost().getHostAddress())));
			out.flush();
			out.close();
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new Cliente(6001);
		
		InetAddress i;
		try {
			i = InetAddress.getLocalHost();
			System.err.println(i.getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	     
	}
	
}
