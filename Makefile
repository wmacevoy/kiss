.PHONY: all
all : lib examples test

SRC:=$(shell find src/main/java -regex '[^._].*\.java$$')

kiss.jar kiss-with-tests.jar : $(SRC)
	/bin/rm -rf target/classes/*
	if [ ! -d target/classes ] ; then mkdir -p target/classes ; fi
	javac -target 1.7 -source 1.7 -Xlint:unchecked -cp target/classes -d target/classes -s src/main/java $$(find src/main/java -regex '[^._].*\.java$$')
	cd target/classes; jar cvfe ../../kiss.jar kiss.API $$(find . -name '*.class' -and -not -iname 'test*')
	cd target/classes; jar cfe ../../kiss-with-tests.jar kiss.API .

%.sha256 : %
	openssl dgst -out $@ -sha256 $<

.PHONY: lib
lib :   kiss.jar kiss.jar.sha256 kiss-with-tests.jar kiss-with-tests.jar.sha256

.PHONY: doc
doc :
	mvn javadoc:javadoc

.PHONY: verify
verify:
	for file in kiss.jar kiss-with-tests.jar ; do \
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
	/bin/rm -rf kiss.jar kiss.jar.sha256 kiss-with-tests.jar kiss-with-tests.jar.sha256
	/bin/rm -rf $$(find . -name '*~' -o -name '._*' -o -name '#*')

tt:
	 echo $$GPG_TTY
deploy: clean all
	git add -f kiss.jar kiss.jar.sha256 \
	           kiss-with-tests.jar kiss-with-tests.jar.sha256
	git commit --allow-empty -m "deploy `date -u`"
	git push
	mvn clean deploy

.PHONY: docker-deploy
docker-deploy: docker-test
	@test -n "$$GPG_PASSPHRASE" || (echo "Set GPG_PASSPHRASE env var first" && exit 1)
	docker run --rm \
	  -v "$$HOME/.m2:/root/.m2" \
	  -v "$$HOME/.gnupg:/root/.gnupg" \
	  -v "$$(pwd):/kiss" \
	  -e GPG_PASSPHRASE \
	  -w /kiss \
	  eclipse-temurin:8-jdk \
	  bash -c '\
	    apt-get update -qq && apt-get install -y -qq maven gnupg > /dev/null && \
	    mkdir -p ~/.gnupg && chmod 700 ~/.gnupg && \
	    echo "no-autostart" > ~/.gnupg/gpg.conf && \
	    echo "allow-loopback-pinentry" > ~/.gnupg/gpg-agent.conf && \
	    gpg-agent --daemon --allow-loopback-pinentry 2>/dev/null; \
	    mvn clean deploy -Dgpg.passphrase="$$GPG_PASSPHRASE" \
	  '

.PHONY: site
site: lib
	cp kiss.jar docs/

.PHONY: serve
serve: site
	python3 docs/serve.py 8080

.PHONY: e2e
e2e: site
	docker compose up --build --abort-on-container-exit --exit-code-from e2e
	docker compose down

.PHONY: docker-test
docker-test:
	docker build -t kiss-test .
	docker run --rm kiss-test

.PHONY: test
test: self-test example-tests

.PHONY: self-test
self-test:
	java -cp kiss-with-tests.jar kiss.API --app kiss.util.Test

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

