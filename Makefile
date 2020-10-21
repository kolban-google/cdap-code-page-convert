PLUGIN=code-page-convert
VERSION=1.0-SNAPSHOT

build:
	mvn package

install:
	cdap cli load artifact ./target/$(PLUGIN)-$(VERSION).jar config-file ./target/$(PLUGIN)-$(VERSION).json

run: build install

list:
	cdap cli list artifacts
