JARS = .:./lib/protobuf-java-2.5.0.jar#:../lib/commons-logging-1.1.1-javadoc.jar:../lib/commons-logging-1.1.1-sources.jar:../lib/commons-logging-adapters-1.1.1.jar:../lib/commons-logging-api-1.1.1.jar:../lib/commons-logging-tests.jar


all:
	mkdir bin; cd src; make

Server1:
	java -cp ./bin:$(JARS) org/liang/SocketMonitor/ServerManager localhost 7890 1

Server2:
	java -cp ./bin:$(JARS) org/liang/SocketMonitor/ServerManager localhost 7891 2

Server3:
	java -cp ./bin:$(JARS) org/liang/SocketMonitor/ServerManager localhost 7892 3

protoc:
	protoc -I=. --java_out=./src ./command.proto

clean:
	cd src; make clean

writeProtoc:
	java -cp bin/:lib/protobuf-java-2.5.0.jar  org/liang/AddMachine Mabook

readProtoc:
	java -cp bin/:lib/protobuf-java-2.5.0.jar  org/liang/ReadMachine Mabook
