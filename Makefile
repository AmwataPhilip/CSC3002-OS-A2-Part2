JAVAC = javac
.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
DOCDIR=doc

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES = dishwashS/CleaningDishes.class dishwashS/Dryer.class dishwashS/Washer.class dishwashS/WetDishRack.class

CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm -rf $(BINDIR)/*

cleandocs:
	rm -rf $(DOCDIR)
	mkdir $(DOCDIR)

docs:
	make cleandocs
	javadoc -d $(DOCDIR) $(SRCDIR)/*.java

run: $(CLASS_FILES)
	java -cp bin dishwashS.CleaningDishes 6 3 100 100
