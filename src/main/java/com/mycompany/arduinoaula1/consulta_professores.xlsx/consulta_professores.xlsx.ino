#include <Javino.h>

#include <HCSR04.h>


UltraSonicDistanceSensor distanceSensor(4, 5);  // Initialize sensor that uses digital pins 13 and 12.
Javino javino;
boolean ligado = false;

void setup () {
    Serial.begin(9600);  // We initialize serial connection so that we could print values from sensor.
}

void loop () {

    String msg = "";
    if(javino.availablemsg()){
      msg = javino.getmsg();
      Serial.print(msg);
    }

    if(msg == "on"){ 
       ligado = true;
    }

    

    if(msg == "off"){ 
       ligado = false;
    }

    if(ligado){
      javino.sendmsg(String(distanceSensor.measureDistanceCm()));
    }
    
     
    // Every 500 miliseconds, do a measurement using the sensor and print the distance in centimeters.
    
    delay(500);
}
