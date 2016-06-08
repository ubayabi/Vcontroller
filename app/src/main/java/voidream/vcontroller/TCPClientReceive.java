package group.voiddream.com.vois;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by ryvaldie on 04/02/16.
 */
public class TCPClientReceive extends AsyncTask<Void, Void, Void> {

    String Address;
    int Port;
    String response = "";
    Context context;

    TCPClientReceive(Context ini, String addr, int port){
        context = ini;
        Address = addr;
        Port = port;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;
        try {
            if (socket!=null) {
                socket = new Socket(Address, Port);
            }
            ByteArrayOutputStream byteArrayOutputStream =
                    new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();

				/*
				 * notice:
				 * inputStream.read() will block if no data return
				 */
            while ((bytesRead = inputStream.read(buffer)) != -1){
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }
            Toast.makeText(context.getApplicationContext(), response, Toast.LENGTH_SHORT).show();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            response = "IOException: " + e.toString();
        }
        /*
        finally{
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        */
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }

}
