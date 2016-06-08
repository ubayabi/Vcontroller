package voidream.vcontroller;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

/**
 * Created by laptop-rnd1 on 26/01/16.
 */
public class MqttPublisher {

    Context context;
    public MqttPublisher(Context ini){
        context = ini;
    }

    public void publishMqttMessage(String message){
        // Ambil Device ID
        Md5 hash_md5 = new Md5();
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = hash_md5.md5(android_id).toUpperCase();
        final String clientId = deviceId.substring(deviceId.length()-20) + "p";
        final String TOPIC = "/tes";//+clientId
        String BROKER_URL = "tcp://192.168.2.60:1883";
        final MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes());
        try {
            final MqttClient mqttClientPublish = new MqttClient(BROKER_URL, clientId, new MemoryPersistence());
            final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setKeepAliveInterval(30);
            mqttConnectOptions.setWill(mqttClientPublish.getTopic("Error"), "something went wrong!".getBytes(), 1, true);
            //untuk connect ke geeknesia.com
            //mqttConnectOptions.setUserName(ini.getString(R.string.user_name_mqtt));
            //mqttConnectOptions.setPassword(ini.getString(R.string.password_mqtt).toCharArray());
            Thread connect = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mqttClientPublish.connect(mqttConnectOptions);
                        mqttClientPublish.subscribe(TOPIC);
                        MqttTopic mqttTopic = mqttClientPublish.getTopic(TOPIC);
                        mqttTopic.publish(mqttMessage);
                        mqttClientPublish.disconnect();
                        mqttClientPublish.unsubscribe(TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            });
            connect.start();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
