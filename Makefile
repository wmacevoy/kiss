VER_MAJOR=0
VER_MINOR=2
VER_PATCH=0
VER=$(VER_MAJOR).$(VER_MINOR).$(VER_PATCH)

.PHONY: all
all : clean lib examples test

.PHONY: lib
lib :   # mvn compile
	if [ ! -d tmp ] ; then mkdir tmp; fi
	if [ ! -d target/classes ] ; then mkdir target/classes ; fi
	javac -Xlint:unchecked -cp target/classes -d target/classes -s src/main/java $$(find src/main/java -regex '[^._].*\.java$$')
	jar cfe kiss-$(VER).jar kiss.util.Run -C target/classes .
	cp kiss-$(VER).jar kiss.jar
	openssl dgst -out kiss-$(VER).jar.sha256 -sha256 kiss-$(VER).jar
	cp kiss-$(VER).jar.sha256 kiss.jar.sha256

.PHONY: examples
examples:
	for dir in examples/*; do if [ -d "$$dir" ] ; then echo "$$dir..."; javac -cp "target/classes:$$dir/target/classes" -d "$$dir/target/classes" -s "$$dir/src/main/java" $$(find "$$dir/src/main/java" -regex '[^._].*\.java$$'); fi ; done

.PHONY: clean
clean: # mvn clean
	/bin/rm -rf tmp/* target/kiss-$(VER).jar* target/kiss.jar* target/classes/* examples/target/classes/*
	/bin/rm -rf $$(find . -name '*~' -o -name '._*')

deploy:
	/bin/rm -rf tmp/* target/kiss-$(VER).jar* target/kiss.jar* target/classes/* examples/target/classes/*
	/bin/rm -rf $$(find . -name '*~' -o -name '._*')
	mvn clean
	mvn compile
	jar cfe kiss-$(VER).jar kiss.util.Run -C target/classes .
	openssl dgst -out kiss-$(VER).jar.sha256 -sha256 kiss-$(VER).jar
	git add -f kiss-$(VER).jar kiss-$(VER).jar.sha256 kiss.jar kiss.jar.sha256
	git commit -m "deploy `date`"
	git push
	mvn deploy

.PHONY: test
test : # mvn exec:exec
	java -cp target/classes kiss.util.Run --app kiss.util.Test
	if [ ! -d tmp ] ; then mkdir tmp ; fi
	cd tmp; for dir in ../examples/*; do if [ -d "$$dir" ] ; then echo "$$dir..."; java -cp "../target/classes:$$dir/target/classes" kiss.util.Run --norun; fi ; done

.PHONY: run
run : # mvn exec:exec
	if [ ! -d tmp ] ; then mkdir tmp ; fi
	cd tmp; for dir in ../examples/*; do if [ -d "$$dir" ] ; then echo "$$dir..."; java -cp "../target/classes:$$dir/target/classes" kiss.util.Run --notest; fi ; done
