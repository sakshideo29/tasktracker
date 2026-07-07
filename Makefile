# Spring Boot TaskTracker Makefile

APP_NAME=tasktrackerapp

.PHONY: run build test clean package coverage help

run:
	mvn spring-boot:run

build:
	mvn clean compile

test:
	mvn test

package:
	mvn clean package

clean:
	mvn clean

coverage:
	mvn test jacoco:report

install:
	mvn clean install

help:
	@echo "Available commands:"
	@echo "  make run       - Run Spring Boot app"
	@echo "  make build     - Compile project"
	@echo "  make test      - Run tests"
	@echo "  make package   - Build jar file"
	@echo "  make clean     - Clean build files"
	@echo "  make coverage  - Generate test coverage report"
	@echo "  make install   - Install dependencies"