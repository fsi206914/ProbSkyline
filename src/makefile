JARS = .:../lib/uncommons-maths-1.2.3.jar:../lib/commons-io-2.4.jar:../lib/log4j-1.2.17.jar

JFLAGS = -d ../bin/ -cp $(JARS) 
JC = javac

.SUFFIXES : .java .class

%.class: %.java
	$(JC) $(JFLAGS) $< 

CLASSES = \
		org/liang/WrapRect/WRTreeInfo.java\
		org/liang/ProbSkyQuery/PruneNaiveMain.java\
		org/liang/ProbSkyQuery/WRTreeHandler.java\
		org/liang/ProbSkyQuery/PruneMain.java\
		org/liang/ProbSkyQuery/Prune3.java\
		org/liang/WrapRect/WRTree.java\
		org/liang/GenerateData/SplitData.java\
		org/liang/GenerateData/instanceGenerator.java\
		org/liang/Visual/InstVisualization.java\
		org/liang/Visual/MinMaxVisual.java\
		org/liang/KDTree/KDTreeMain.java\
		org/liang/IO/TextInstanceReader.java\
		org/liang/IO/TextInstanceWriter.java\
		org/liang/DataStructures/instance.java\
		org/liang/DataStructures/PartitionInfo.java\
		org/liang/ProbSkyQuery/PruneBase.java\
		org/liang/ProbSkyQuery/Prune1And2.java\
		org/liang/KDTree/KDTree.java\
		org/liang/KDTree/KDPoint.java\
		org/liang/ProbSkyQuery/KDTreeHandler.java\
		urg/liang/ProbSkyQuery/Prune3.java\
		org/liang/ProbSkyQuery/util.java\
		org/liang/DataStructures/KDTreeInfo.java\
		org/liang/DataStructures/item.java

KDCLASSES= \
		org/liang/KDTree/KDTree.java\

default: classes

kdjar: files

classes: $(CLASSES:.java=.class)

files: $(KDCLASSES:.java=.class)

clean:
	rm *~; rm -rf ../bin/*

