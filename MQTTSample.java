import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.lang.*;

public class MQTTSample {
    public static void main(String[] args) throws IOException, InterruptedException{



        String topic        = "finntaite";
        String content      = "This for Assignment";
        int qos             = 1;
        String broker       = "tcp://m21.cloudmqtt.com:12961";
        int noOfLines = 30;
        String[] readInLines = new String[noOfLines];

        //MQTT client id to use for the device. "" will generate a client id automatically
        String clientId     = "ClientId";

		while(true){

			Scanner fileInput = new Scanner(new File("sysFile.txt"));
        	PrintStream fileOut = new PrintStream(new File("Results.txt"));

            Runtime.getRuntime().exec("bash getBash.sh"); //Runs Bash file

            TimeUnit.SECONDS.sleep(1); //1 second delay

            //Reads in file lines to a String array
            for(int index = 0; index < noOfLines; index++){
                    readInLines[index] = fileInput.nextLine();
            }



            String mess1 = readInLines[5]; //Sets the outgoing message to the vale of the array location

            //Outputs Bash file results
            for(int index = 0; index < noOfLines; index++){
                    fileOut.println(readInLines[index]);
            }

            TimeUnit.SECONDS.sleep(4); //A 4 second delay

            MemoryPersistence persistence = new MemoryPersistence();

	    	float val = Float.parseFloat(readInLines[5]);

            System.out.println("CPU temp = " +val);

            fileInput.close();
	    fileOut.close();

			if(val > 10.8){
				try {
					MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
//------------------------
					mqttClient.setCallback(new MqttCallback() {
					public void messageArrived(String topic, MqttMessage msg)
						throws Exception {
							System.out.println("Recived:" + topic);
							System.out.println("Recived:" + new String(msg.getPayload()));
						}

						public void deliveryComplete(IMqttDeliveryToken arg0) {
							System.out.println("Delivary complete");
						}

						public void connectionLost(Throwable arg0) {
							// TODO Auto-generated method stub
						}
					});
//------------------------
					MqttConnectOptions connOpts = new MqttConnectOptions();
					connOpts.setCleanSession(true);
					connOpts.setUserName("wwysgubo");
					connOpts.setPassword(new char[]{'S', 'r', 'N', 'V', 'D', 'k', '3', 'T', 'R', 'N', 'S', 'x'});
					mqttClient.connect(connOpts);
					MqttMessage message = new MqttMessage(mess1.getBytes());
					message.setQos(qos);
					System.out.println("Publish message: " + message);
					mqttClient.subscribe(topic, qos);
					mqttClient.publish(topic, message);
					//mqttClient.disconnect();
					//System.exit(0);
				} catch(MqttException me) {
					System.out.println("reason "+me.getReasonCode());
					System.out.println("msg "+me.getMessage());
					System.out.println("loc "+me.getLocalizedMessage());
					System.out.println("cause "+me.getCause());
					System.out.println("excep "+me);
					me.printStackTrace();
				}//End Catch
			}//End if
        }//End While

    }//End Main
}//End Class