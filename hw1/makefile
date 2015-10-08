JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java

CLASSES = \
	PlayGame.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
