all : jaesprng.log
tmp/App.class : src/App.java
	if [ ! -d tmp ] ; then mkdir tmp ; fi
	javac -cp ../kiss/kiss.jar:tmp -d tmp -s src src/App.java

jaesprng.log: tmp/App.class
	/bin/rm -rf tmp/jaesprng
	mkfifo tmp/jaesprng
	java -cp ../kiss/kiss.jar:tmp kiss.util.Run - > tmp/jaesprng &
	date -u > jaesprng.log
	nice time dieharder -a -g 201 -f tmp/jaesprng | tee -a jaesprng.log
	dd count=1 bs=1024 if=tmp/jaesprng of=/dev/null # close pipe
	/bin/rm -rf tmp/jaesprng

urandom.log:
	date -u > urandom.log
	nice time dieharder -a -g 201 -f /dev/urandom | tee -a urandom.log
