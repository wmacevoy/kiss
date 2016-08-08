lib :
	$(MAKE) -C ../.. lib
this :
	javac -cp "../../target/classes:target/classes" -d "target/classes" -s "src/main/java" $$(find "src/main/java" -regex '[^._].*\.java$$')
run :
	if [ ! -d tmp ] ; then mkdir tmp ; fi
	cd tmp; java -cp "../../../target/classes:../target/classes" kiss.util.Run

