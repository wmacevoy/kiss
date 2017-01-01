.PHONY: all
all : lib this run

.PHONY: lib
lib:
	$(MAKE) -C ../.. lib

.PHONY: this
this:
	if [ ! -d target/classes ] ; then mkdir -p target/classes ; fi
	javac -cp ../../../kiss.jar:../../kiss-pi.jar:/opt/pi4j/lib/'*' -d "target/classes" -s "src/main/java" $$(find "src/main/java" -regex '[^._].*\.java$$')

.PHONY: run
run:
	sudo java -cp target/classes:../../../kiss.jar:../../kiss-pi.jar:/opt/pi4j/lib/'*' kiss.API

.PHONY: test
test: 
	sudo java -cp target/classes:../../../kiss.jar:../../kiss-pi.jar:/opt/pi4j/lib/'*' kiss.API --norun

.PHONY: clean
clean:
	/bin/rm -rf target/*
	/bin/rm -rf $$(find . -name '*~' -o -name '._*' -o -name '#*')
