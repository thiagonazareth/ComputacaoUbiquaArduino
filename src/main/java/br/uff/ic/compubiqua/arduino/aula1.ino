#include <Javino.h>

#include <HCSR04.h>

int LDR = A0;

UltraSonicDistanceSensor distanceSensor(4, 5);  // Initialize sensor that uses digital pins 13 and 12.
Javino javino;

void setup () {
    Serial.begin(9600); 
    pinMode(LDR, INPUT);
    // We initialize serial connection so that we could print values from sensor.
}

void loop () {

    String msg = "";
    if(javino.availablemsg()){
      msg = javino.getmsg();
    }

    
    if(msg == "ler"){ 
      int luz = analogRead(LDR);
      float dist = distanceSensor.measureDistanceCm();
       javino.sendmsg(String(dist) + " " + String(luz));
    }

    

    

    
     
    // Every 500 miliseconds, do a measurement using the sensor and print the distance in centimeters.
    
    delay(500);
}
