import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SquareServer {

    //runner
    public static void main(String[] args) throws Exception {
        try (ServerSocket listener = new ServerSocket(44444)) {
            System.out.println("The server is running...");
            ExecutorService pool = Executors.newFixedThreadPool(10); //a pool pf workers ready to work for us
            while (true) {
                pool.execute(new Squared(listener.accept())); //pass through the socket that we created
            }
        }
    }

    //implementation
    private static class Squared implements Runnable {
        private Socket socket;

        Squared(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try{
                Scanner in = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //"true" is whether or not this is going to flush the info automatically after its sent
                while(in.hasNextInt()){
                    int num = in.nextInt();
                    int squared = num*num;
                    out.println(squared);
                    //out.println(in.nextInt() * in.nextInt());
                    //out.println(in.nextInt()*(in.nextInt())); //square the number
                }
            } catch(Exception e){
                System.out.println("Error message");
            } finally {
                try{
                    socket.close();
                } catch(IOException e){

                }
            }
        }
    }
}
