.PHONY: lib
lib:
	$(MAKE) -C ../.. lib

.PHONY: this
this:
	javac -cp "../../target/classes:target/classes" -d "target/classes" -s "src/main/java" $$(find "src/main/java" -regex '[^._].*\.java$$')

.PHONY: run
run:
	if [ ! -d tmp ] ; then mkdir tmp ; fi
	cd tmp; java -cp "../../../target/classes:../target/classes" kiss.util.Run

.PHONY: test
test: 
	if [ ! -d tmp ] ; then mkdir tmp ; fi
	cd tmp; java -cp "../../../target/classes:../target/classes" kiss.util.Run --norun

.PHONY: clean
clean:
	/bin/rm -rf tmp/* target/*
	/bin/rm -rf $$(find . -name '*~' -o -name '._*' -o -name '#*')
