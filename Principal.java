package politicas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Principal {
//Classe principal que realiza a leitura do arquivo de entrada e executa os metodos necessarios para 
//calcular os tempos para cada politica.
	public static void main(String[] args) throws Exception {
		File file = new File("src/Entrada.txt");//Inicializa o arquivo de entrada, no programa.
		
		BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(file)));//abre o arquivo de entrada.

		String linha = null;

		//Realiza a leitura dos dados necessarios para a contrucao do disco.
		linha = entrada.readLine();	
		String dados[] = linha.split("=");
		int setores = Integer.parseInt(dados[1]);
		linha = entrada.readLine();	
		String dados1[] = linha.split("=");
		int trilhas = Integer.parseInt(dados1[1]);
		linha = entrada.readLine();	
		String dados2[] = linha.split("=");
		int posInicial = Integer.parseInt(dados2[1]);

		Disco disco = new Disco(setores, trilhas, posInicial);

		List<Processo> processosInicial = new ArrayList<Processo>();

		linha = entrada.readLine();
		
		//realizada a leitura das linhas do arquivo adicionando os processos a uma lista que sera usada para os calculos,
		//ate que encontra a condicão de parada
		while(!(linha.equals(";"))) {
			String dadosP[] = linha.split("=");
			String entradas[] = dadosP[1].split(",");
			int validar = Integer.parseInt(entradas[2]);
			if(validar < trilhas)//Verifica se o processo possui uma quantidade de trilhas compativel com o disco, caso contrario nao adiciona o processo.
				processosInicial.add(new Processo(entradas[0], entradas[1], entradas[2], entradas[3]));
			linha = entrada.readLine();
		}

		entrada.close();
		
		//Realiza a chamada do metodo factory que ira executar a construção das politicas desejadas.
		FactoryPolitica factory = new FactoryPolitica(); 

		factory.getPolitica(disco, processosInicial);
	}
}
