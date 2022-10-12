from time import sleep
import time
import RPi.GPIO as gpio
import sys
import pyrebase
import picamera
import random



config = {
    "apiKey": "AIzaSyDnKnN0ffTmaN6TvLdpETrY9c0u-oQuEe0",
    "authDomain": "raspi-abd4f.firebaseapp.com",
    "databaseURL": "https://raspi-abd4f-default-rtdb.firebaseio.com",     #Einloggen in die Datenbank
    "projectId": "raspi-abd4f",                                           #eigentlich sollten diese Keys aus Sicherheits-
    "storageBucket": "raspi-abd4f.appspot.com",                           #gründen nicht im Code stehen
}                                                                         #Kann man leider nicht verstecken

firebase = pyrebase.initialize_app(config)
db = firebase.database()

gpio.setmode(gpio.BOARD)

EnableA = 13                    
EnableB = 15
In1 = 3
In2 = 5
In3 = 7
In4 = 11

trig = 18
echo = 22
                                  # Alle Pins belegen
gpio.setup(EnableA,gpio.OUT)
gpio.setup(EnableB,gpio.OUT)


gpio.setup(In1,gpio.OUT)
gpio.setup(In2,gpio.OUT)
gpio.setup(In3,gpio.OUT)
gpio.setup(In4,gpio.OUT)

gpio.setup(echo, gpio.IN)
gpio.setup(trig, gpio.OUT)

pwmA=gpio.PWM(EnableA,100)
pwmB=gpio.PWM(EnableB,100)    


def abstandmessen():
        gpio.output(trig, True)
        time.sleep(0.00001)
        gpio.output(trig, False)

        while gpio.input(echo) == 0:
                pass

        start = time.time()
                                                  # Funktion für Ultraschallsensor, um Abstand zu messen
        while gpio.input(echo) == 1:
                pass

        ende = time.time()
        entfernung = int(((ende - start) * 34300) / 2)
        print("Entfernung:", entfernung, "cm")

        file2 = db.child("car")
        file2.update({'entfernung' : entfernung})

        if entfernung < 15:
		          print("zurück")
              back()


def fotoMachen():
        camera = picamera.PiCamera()
        camera.rotation = 180
        camera.capture('/tmp/picture.jpg')
        camera.close()

        storage = firebase.storage()                       # Funktion, um Fotos zu machen und in das Storage der 
        storage.child("image").put("/tmp/picture.jpg")     # Datenbank zu senden

        db.child("car").set({'message': 'Stehen'})


def kreisDrehen():
      rechts()                # Funktion für Kreis drehen
      time.sleep(5)


def autonom():
      func = random.choice([vor(), back(), rechts(), links()])
      func()                  # aus dieser Liste wird zufällig eine Funktion ausgewählt und für 10s ausgeführt
      time.sleep(10)


def vor():
      pwmA.start(100)
      gpio.output(In3, True)
      gpio.output(In4, False)
      pwmB.start(100)         # Funktion für Vorwärtsfahren
      gpio.output(In1, True)
      gpio.output(In2, False)
      abstandmessen()         # am Ende wird jedes Mal der Abstand gemessen


def back():
      pwmA.start(100)
      gpio.output(In3, False)
      gpio.output(In4, True)
      pwmB.start(100)         # Funk. für rückwärtsfahren
      gpio.output(In1, False)
      gpio.output(In2, True)
      abstandmessen()


def links():
      pwmA.start(100)
      gpio.output(In1, True)
      gpio.output(In2, False)
      pwmB.start(100)         # Funk. für nach links fahren
      gpio.output(In3, False)
      gpio.output(In4, True)
      abstandmessen()


def rechts():
      pwmA.start(100)
      gpio.output(In3, True)
      gpio.output(In4, False)
      pwmB.start(100)         # Funk. für nach rechts fahren
      gpio.output(In1, False)
      gpio.output(In2, True)
      abstandmessen()


def stehen():
      pwmA.start(100)
      gpio.output(In3, False)
      gpio.output(In4, False)
      pwmB.start(100)         # Funk. für stehen bleiben
      gpio.output(In1, False)
      gpio.output(In2, False)
      abstandmessen()

while True:
        try:

                file = db.child("car").get().val()["message"]   # auslesen der Nachricht aus der Datenbank
                if file == "Vor":
                        vor()                  # Wenn in der DB "Vor" steht, wird Funk. vor() ausgeführt
                        print("vor")

                elif file == "Zuruck":
                        back()
                        print("zuruck")

                elif file == "Rechts":
                        links()
                        print("rechts")

                elif file == "Links":
                        rechts()
                        print("links")

                elif file == "Stehen":
                        stehen()
                        print("stehen")

                elif file == "Foto":
                         fotoMachen()

                elif file == "Kreis_drehen":
                         rechts()

                elif file == "autonom":
                         autonom()

        except KeyboardInterrupt:   # wenn STRG+C gedrückt wird, wird Programm beendet und alle GPIO Pins zurückgesetzt
                gpio.cleanup()
                sys.exit()




