default:
	javac -sourcepath src -cp `find lib -name "*.jar" | tr "\\n" ":"`  -d bin `find src -name "*.java"`
	cd bin; jar cfve tp1.jar br.com.lealdn.grafo.Start `find .`
	@mv bin/tp1.jar .
	@echo "================================"
	@echo "Arquivo: tp1.jar criado"
	@echo "Ajuda        : java -jar tp1.jar"
	@echo "Para executar: java -jar tp1.jar <input> <k-comunidades> <algoritmo>"
clean:
	rm -Rf bin;
	mkdir bin;

