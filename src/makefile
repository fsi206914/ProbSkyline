JARS = .:../lib/uncommons-maths-1.2.3.jar:../lib/slf4j-api-1.7.5.jar:../lib/slf4j-simple-1.7.5.jar:../lib/commons-io-2.4.jar:../lib/log4j-1.2.17.jar

JFLAGS = -d ../bin/ -cp $(JARS) 
JC = javac

.SUFFIXES : .java .class

%.class: %.java
	$(JC) $(JFLAGS) $< 

CLASSES = \
		org/liang/GenerateData/instanceGenerator.java\
		org/liang/GenerateData/SplitData.java\
		org/liang/IO/TextInstanceReader.java\
		org/liang/IO/TextInstanceWriter.java\
		org/liang/DataStructures/instance.java\
		org/liang/DataStructures/PartitionInfo.java\
		org/liang/ProbSkyQuery/PruneBase.java\
		org/liang/ProbSkyQuery/Prune1And2.java\
		org/liang/ProbSkyQuery/PruneMain.java\
		org/liang/KDTree/KDTree.java\
		org/liang/KDTree/KDTreeMain.java\
		org/liang/DataStructures/item.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm *~; rm -rf ../bin/*

