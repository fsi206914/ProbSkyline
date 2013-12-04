
JARS = .:./lib/uncommons-maths-1.2.3.jar:./lib/commons-lang3-3.1.jar:./lib/slf4j-api-1.7.5.jar:./lib/slf4j-simple-1.7.5.jar:./lib/commons-io-2.4.jar:./lib/log4j-1.2.17.jar

all:
	mkdir bin; cd src; make

GenerateInstance:
	java -cp ./bin:$(JARS) org/liang/GenerateData/instanceGenerator
	
SplitData:
	java -ea -cp ./bin:$(JARS) org/liang/GenerateData/SplitData

PruneMain:
	java -ea -cp ./bin:$(JARS) org/liang/ProbSkyQuery/PruneMain

KDTreeMain:
	java -ea -Xms2048m -cp ./bin:$(JARS) org/liang/KDTree/KDTreeMain

PruneNaiveMain:
	java -ea -Xms2048m -cp ./bin:$(JARS) org/liang/ProbSkyQuery/PruneNaiveMain

Merge:
	java -ea -Xms2048m -cp ./bin:$(JARS) org/liang/ProbSkyQuery/Merge

KDJAR:
	jar -cvf ./KDTree.jar ./bin/org/liang/KDTree/*.class

clean:
	cd src; make clean
