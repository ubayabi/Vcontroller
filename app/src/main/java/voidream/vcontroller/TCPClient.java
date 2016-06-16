package voidream.vcontroller;

import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TCPClient {

    String Address, response;
    int Port;
    Context context;

    public TCPClient(Context ini, String address, int port){
        context = ini;
        Address = address;
        Port = port;
    }

    Socket[] socket = {null};
    DataOutputStream[] dataOutputStream = {null};
    DataInputStream[] dataInputStream = {null};

    public void sendData(final String data){
        Thread send = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (socket[0]==null) {
                        connect();
                    }else {
                        dataOutputStream[0].writeUTF(data);
                        ByteArrayOutputStream byteArrayOutputStream =
                                new ByteArrayOutputStream(1024);
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        InputStream inputStream = socket[0].getInputStream();
                        while ((bytesRead = inputStream.read(buffer)) != -1){
                            dataOutputStream[0].write(buffer, 0, bytesRead);
                            response += byteArrayOutputStream.toString("UTF-8");
                        }

                        Toast.makeText(context.getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    if (dataOutputStream[0] != null) {
                        try {
                            dataOutputStream[0].flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        });send.start();
    }

    /*
    public String getData(){
        final String[] respon = {null};
        Thread get = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(socket==null) {
                        connect();
                    }else {
                        ByteArrayOutputStream byteArrayOutputStream =
                                new ByteArrayOutputStream(1024);
                        byte[] buffer = new byte[1024];
                        int bytesRead;

                        while ((bytesRead = dataInputStream[0].read(buffer)) != -1){
                            byteArrayOutputStream.write(buffer, 0, bytesRead);
                            respon[0] += byteArrayOutputStream.toString("UTF-8");
                            Toast.makeText(context.getApplicationContext(), respon[0], Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally{
                    if (dataInputStream[0] != null){
                        try {
                            dataInputStream[0].close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }); get.start();
        return respon[0];
    }
    */

    public void disconnect(){
//        if (socket[0].isConnected()){
           Thread disconnect = new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       socket[0].close();
                       if (socket[0]==null){
                           PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("connectbutton", true).commit();
                           Toast.makeText(context.getApplicationContext(), "Disonnected", Toast.LENGTH_SHORT).show();
                       }else {
                           PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("connectbutton", false).commit();
                           Toast.makeText(context.getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
                       }
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           }); disconnect.start();
//        }
    }

    public void connect(){
        Thread connect = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket[0] = new Socket(Address, Port);
                    dataOutputStream[0] = new DataOutputStream(socket[0].getOutputStream());
                    dataInputStream[0] = new DataInputStream(socket[0].getInputStream());
                    socket[0].setKeepAlive(true);
                    if (socket[0]!=null){
                        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("connectbutton", false).commit();
                        //Toast.makeText(context.getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
                    }else {
                        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("connectbutton", true).commit();
                        //Toast.makeText(context.getApplicationContext(), "Disonnected", Toast.LENGTH_SHORT).show();
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }); connect.start();
    }
}