package voidream.vcontroller;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class MqttService extends Service {

    public static MqttClient mqttClient;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onStart(Intent intent, int startId) {
        StrictMode.enableDefaults();
        // Ambil data array
        // 0 = url,
        String[] data_intent = intent.getStringArrayExtra("data_mqtt");

        // Ambil Device ID
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = Md5.md5(android_id).toUpperCase();
        Log.i("device id=", deviceId);
        final String clientId = deviceId.substring(deviceId.length()-20);

        // Ambil url dan topic
        String broker_url = data_intent[0];
        String topic = this.getResources().getString(R.string.set_topic, deviceId, data_intent[1]);

        try {
            mqttClient = new MqttClient(broker_url, clientId, new MemoryPersistence());
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setKeepAliveInterval(30);
            mqttConnectOptions.setWill(mqttClient.getTopic("Error")
                    , "something went wrong!".getBytes(), 1, true);
            //untuk connect ke broker sendiri
            if (data_intent.length>2) {
                mqttConnectOptions.setUserName(data_intent[3]);
                mqttConnectOptions.setPassword(data_intent[4].toCharArray());
                mqttConnectOptions.setCleanSession(true);
            }
            mqttClient.connect(mqttConnectOptions);
            mqttClient.subscribe(topic);

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {

                }

                @Override
                public void messageArrived(final MqttTopic mqttTopic
                        , MqttMessage mqttMessage) throws Exception {
                    final String message = mqttMessage.toString();

                }

                @Override
                public void deliveryComplete(MqttDeliveryToken mqttDeliveryToken) {

                }
            });

        } catch (MqttException e) {
            Toast.makeText(getApplicationContext()
                    , this.getResources().getString
                            (R.string.mqtt_error_toast, e.getMessage())
                    , Toast.LENGTH_LONG).show();
            Log.e(MqttService.class.getSimpleName(), e.getMessage());
        }

        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        try {
            mqttClient.disconnect(0);
        } catch (MqttException e) {
            Toast.makeText(getApplicationContext()
                    , this.getResources().getString
                            (R.string.mqtt_error_toast, e.getMessage())
                    , Toast.LENGTH_LONG).show();
            Log.e(MqttService.class.getSimpleName(), e.getMessage());
        }
    }

}