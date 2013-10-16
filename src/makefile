JARS = .:../lib/protobuf-java-2.5.0.jar#:../lib/commons-logging-1.1.1-javadoc.jar:../lib/commons-logging-1.1.1-sources.jar:../lib/commons-logging-adapters-1.1.1.jar:../lib/commons-logging-api-1.1.1.jar:../lib/commons-logging-tests.jar

JFLAGS = -d ../bin/ -cp $(JARS) 
JC = javac

.SUFFIXES : .java .class

%.class: %.java
	$(JC) $(JFLAGS) $< 

CLASSES = \
		org/liang/SocketMonitor/ServerManager.java\
		org/liang/SocketMonitor/ServerSocketHandler.java\
		org/liang/SocketMonitor/ServerSocketListener.java\
		org/liang/SocketMonitor/Command.java\
		org/liang/SocketMonitor/CommandSender.java\
		org/liang/SocketMonitor/GrepCommand.java\
		org/liang/SocketMonitor/MachineCommand.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm *~; rm -rf ../bin/*

writeProtoc:
	javac -cp .:../lib/protobuf-java-2.5.0.jar -d ../bin/ org/liang/AddMachine.java

readProtoc:
	javac -cp .:../lib/protobuf-java-2.5.0.jar -d ../bin/ org/liang/ReadMachine.java
