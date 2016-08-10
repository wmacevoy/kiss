VER_MAJOR=0
VER_MINOR=2
VER_PATCH=0
VER=$(VER_MAJOR).$(VER_MINOR).$(VER_PATCH)

.PHONY: all
all : clean lib examples test

.PHONY: lib
lib :   # mvn compile
	if [ ! -d tmp ] ; then mkdir tmp; fi
	if [ ! -d target/classes ] ; then mkdir -p target/classes ; fi
	javac -Xlint:unchecked -cp target/classes -d target/classes -s src/main/java $$(find src/main/java -regex '[^._].*\.java$$')
	cd target/classes; jar cvfe ../../kiss-$(VER).jar kiss.util.Run $$(find . -name '*.class' -and -not -iname 'test*')
	cd target/classes; jar cfe ../../kiss-with-tests-$(VER).jar kiss.util.Run .
	cp kiss-$(VER).jar kiss.jar
	cp kiss-with-tests-$(VER).jar kiss-with-tests.jar
	openssl dgst -out kiss-$(VER).jar.sha256 -sha256 kiss-$(VER).jar
	openssl dgst -out kiss-with-tests-$(VER).jar.sha256 -sha256 kiss-with-tests-$(VER).jar
	cp kiss-$(VER).jar.sha256 kiss.jar.sha256
	cp kiss-with-tests-$(VER).jar.sha256 kiss-with-tests.jar.sha256

.PHONY: examples
examples:
	for dir in examples/*; do \
	  if [ -d "$$dir" ] ; then \
	    echo "$$dir..."; \
	    if [ ! -d "$$dir/target/classes" ] ; then \
	      mkdir -p "$$dir/target/classes" ; \
	    fi ; \
	    javac -cp "target/classes:$$dir/target/classes" \
	      -d "$$dir/target/classes" \
	      -s "$$dir/src/main/java" \
	      $$(find "$$dir/src/main/java" -regex '[^._].*\.java$$'); \
	  fi ; \
	done

.PHONY: clean
clean: # mvn clean
	/bin/rm -rf tmp/* target/* examples/*/target/* examples/*/tmp/*
	/bin/rm -rf $$(find . -name '*~' -o -name '._*' -o -name '#*')

deploy: all
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
