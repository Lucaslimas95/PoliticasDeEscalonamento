package politicas;

//Classe que contem os metodos de acesso e construcao para os processos usados durante a execucao do programa.
public class Processo {
	private int chegada;
	private int latency;
	private int trilha;
	private int transfer;
	private int acesso;
	private int espera;
	
	public int getChegada() {
		return chegada;
	}
	public void setChegada(int chegada) {
		this.chegada = chegada;
	}
	public int getLatency() {
		return latency;
	}
	public void setLatency(int latency) {
		this.latency = latency;
	}
	public int getTrilha() {
		return trilha;
	}
	public void setSeek(int trilha) {
		this.trilha = trilha;
	}
	public int getTransfer() {
		return transfer;
	}
	public void setTransfer(int transfer) {
		this.transfer = transfer;
	}
	public int getAcesso() {
		return acesso;
	}
	public void setAcesso(int acesso) {
		this.acesso = acesso;
	}
	public int getEspera() {
		return espera;
	}
	public void setEspera(int espera) {
		this.espera = espera;
	}
	public Processo(String chegada, String latency, String trilha, String transfer) {
		this.chegada = Integer.parseInt(chegada);
		this.latency = Integer.parseInt(latency);
		this.trilha = Integer.parseInt(trilha);
		this.transfer = Integer.parseInt(transfer);
	}

}
