JAR=kiss-1.0-SNAPSHOT.jar
.PHONY: lib
lib :   # mvn compile
	if [ ! -d target/classes ] ; then mkdir target/classes ; fi
	javac -Xlint:unchecked -cp target/classes -d target/classes -s src/main/java $$(find src/main/java -regex '[^._].*\.java$$')
	jar cfe target/$(JAR) kiss.util.Run -C target/classes .

.PHONY: examples
examples :
	for dir in examples/*; do if [ -d "$$dir" ] ; then echo "$$dir..."; javac -cp "target/$(JAR):$$dir/target/classes" -d "$$dir/target/classes" -s "$$dir/src/main/java" $$(find "$$dir/src/main/java" -regex '[^._].*\.java$$'); fi ; done

.PHONY: clean
clean : # mvn clean
	/bin/rm -rf tmp/* target/$(JAR) target/classes/* examples/target/classes/*
	/bin/rm -rf $$(find . -name '*~' -o -name '._*')

deploy :
	mvn deploy

.PHONY: test
test : # mvn exec:exec
	java -jar  "target/$(JAR)" --app kiss.util.Test
	if [ ! -d tmp ] ; then mkdir tmp ; fi
	cd tmp; for dir in ../examples/*; do if [ -d "$$dir" ] ; then echo "$$dir..."; java -cp "../target/$(JAR):$$dir/target/classes" kiss.util.Run < /dev/null; fi ; done

