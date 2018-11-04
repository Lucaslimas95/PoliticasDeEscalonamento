package politicas;

//Classe utilizada para instanciar o disco a partir dos dados fornecidos pelo arquivo de entrada,
//e contendo os metodos para acesso dos dados e contrucao do disco. 
public class Disco {
	private int setores;
	private int trilhas;
	private int partida;
	
	public int getSetores() {
		return setores;
	}
	public void setSetores(int setores) {
		this.setores = setores;
	}
	public int getTrilhas() {
		return trilhas;
	}
	public void setTrilhas(int trilhas) {
		this.trilhas = trilhas;
	}
	public int getPartida() {
		return partida;
	}
	public void setPartida(int partida) {
		this.partida = partida;
	}

	public Disco(int setores, int trilhas, int partida) {
		this.setores = setores;
		this.trilhas = trilhas;
		this.partida = partida;
	}
}
