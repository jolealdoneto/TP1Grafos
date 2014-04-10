default:
	javac -sourcepath src -cp `find lib -name "*.jar" | tr "\\n" ":"`  -d bin `find src -name "*.java"`
	cd bin; jar cfve tp1.jar br.com.lealdn.grafo.Start `find .`
	@mv bin/tp1.jar .
	@echo "Arquivo: tp1.jar criado"
clean:
	rm -Rf bin;
	mkdir bin;

