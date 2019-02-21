package chatcliente;

import static chatcliente.Ventana.estado;
import static chatcliente.Ventana.salida;
import java.io.IOException;
import java.io.InputStream;

/**
 * Cliente de Chat con una ventana gráfica
 * @author dani_
 */
public class ChatCliente {

    public static void main(String[] args) {
        Ventana ventana = new Ventana();
        ventana.setVisible(true);
    }
}

/**
 * Hilo para estar escuchando mensajes todo el tiempo, desde que se conecta
 * al servidor hasta que finaliza el programa
 * @author dani_
 */
class Recibir extends Thread {

    InputStream is;

    public Recibir(InputStream is) {
        this.is = is;
    }

    @Override
    public void run() {

        while (true) {
            try {
                //Recibimos mensajes
                byte[] recibido = new byte[500];
                is.read(recibido);
                String mensaje = new String(recibido);
                salida.setText(salida.getText()+"\n"+mensaje);
                estado.setText("Conectado");
            } catch (IOException ex) {
                estado.setText("El servidor está cerrado.");
            }
        }

    }
}
