all: src/PlayGame.class
src/PlayGame.class:src/PlayGame.java
	javac -d ./src src/*.java

clean:
	rm src/*.class
	rm -rf src/assignment3

run:
	java -cp \src PlayGame
