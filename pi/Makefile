.PHONY: all
all : clean lib examples test

SRC:=$(shell find src/main/java -regex '[^._].*\.java$$')

kiss-pi.jar: $(SRC)
	$(MAKE) -C .. lib
	/bin/rm -rf target/classes
	mkdir target/classes
	javac -Xlint:unchecked -cp ../kiss.jar:target/classes:/opt/pi4j/lib/'*' -d target/classes -s src/main/java $$(find src/main/java -regex '[^._].*\.java$$')
	cd target/classes; jar cf ../../kiss-pi.jar .

%.sha256 : %
	openssl dgst -out $@ -sha256 $<

.PHONY: lib
lib :   kiss-pi.jar kiss-pi.jar.sha256

.PHONY: doc
doc :
	mvn javadoc:javadoc

.PHONY: verify
verify:
	for file in kiss-pi.jar ; do \
	  if openssl dgst -sha256 "$$file" | diff -q - "$$file.sha256" ; \
	    then \
	      echo "$$file checksum matches $$file.sha256" ; \
	  fi; \
	done

.PHONY: examples
examples:
	for dir in examples/*; do \
	  if [ -f "$$dir/Makefile" ] ; then \
	    echo "$$dir: make this"; \
	    $(MAKE) -C "$$dir" this ; \
	  fi ; \
	done

.PHONY: clean
clean: # mvn clean
	/bin/rm -rf tmp/* target/* examples/*/target/* examples/*/tmp/*
	/bin/rm -rf $$(find . -name '*~' -o -name '._*' -o -name '#*')

deploy: clean all
	git add -f kiss-pi.jar kiss-pi.jar.sha256
	git commit -m "deploy `date -u`"
	git push
	mvn clean
	mvn compile
	mvn deploy

.PHONY: test
test: self-test example-tests

.PHONY: self-test
self-test:
	java -cp target/classes kiss.util.Run --app kiss.util.Test

.PHONY: example-tests
example-tests:
	for dir in examples/*; do \
	  if [ -f "$$dir/Makefile" ] ; then \
	    echo "$$dir: make test"; \
	    $(MAKE) -C "$$dir" test ; \
	  fi ; \
	done

.PHONY: example-runs
example-runs:
	for dir in examples/*; do \
	  if [ -f "$$dir/Makefile" ] ; then \
	    echo "$$dir: make run"; \
	    $(MAKE) -C "$$dir" run ; \
	  fi ; \
	done

