package chatcliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
/**
 *
 * @author dani_
 */
public class ChatCliente {

    static Socket clienteSocket = new Socket();
    static Scanner entrada = new Scanner(System.in);
    static boolean salir=false;

    public static void main(String[] args) {
        try {
            //Pedimos los datos
            System.out.println("Inserte la IP del servidor:");
            String ip=entrada.nextLine();
            System.out.println("Inserte el puerto del servidor:");
            int puerto = Integer.parseInt(entrada.nextLine());
            System.out.println("Inserte Nickname:");
            String nickname=entrada.nextLine();
            
            //Establecemos conexión
            InetSocketAddress addr = new InetSocketAddress(ip, puerto);
            clienteSocket.connect(addr);
            
            //Abrimos conexión entrada y salida
            InputStream is = clienteSocket.getInputStream();
            OutputStream os = clienteSocket.getOutputStream();
            
            //Enviamos el nickname
            os.write(nickname.getBytes());
            
            Recibir hilo=new Recibir(is);
            hilo.start();
            
            while(salir==false){              
                String mensaje=entrada.nextLine();
                os.write(mensaje.getBytes());
                if(mensaje.equals("/bye"))                    
                    salir=true;
            }
            os.close();
            is.close();
            hilo.stop();
            
        } catch (IOException ex) {
            System.out.println("Error de conexión.");
        }finally{
            try {
                clienteSocket.close();
            } catch (IOException ex) {
                System.out.println("Error al cerrar conexión.");
            }
        }
    }

}

class Recibir extends Thread {
    InputStream is;
    public Recibir(InputStream is){
        this.is=is;
    }
    
    @Override
    public void run() {
        
        while(true){
            try {
                //Recibimos mensajes
                byte[] recibido = new byte[500];
                is.read(recibido);
                String mensaje=new String(recibido);
                System.out.println(mensaje);
            } catch (IOException ex) {
                System.out.println("Error al recibir mensajes.");
            }
        }
        
    }
}
