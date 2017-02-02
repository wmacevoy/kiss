bin/ChangeBug1.class : src/ChangeBug1.java
	javac -cp /opt/pi4j/lib/'*' -d bin src/ChangeBug1.java

bug1: bin/ChangeBug1.class
	echo "no GPIO STATE CHANGE when state changes..."
	sudo java -cp bin:/opt/pi4j/lib/'*' ChangeBug1
