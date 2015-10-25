JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) -d . $*.java

CLASSES = \
	*.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	$(RM) -rf assignment2

run:
	java POOCasino
