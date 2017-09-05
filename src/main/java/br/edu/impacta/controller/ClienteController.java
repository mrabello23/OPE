package br.edu.impacta.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.edu.impacta.dao.ClienteDAO;
import br.edu.impacta.entity.Cliente;
import br.edu.impacta.entity.TelCliente;

/**
 * @author Stefany Souza
 */

@Named(value = "clienteControl")
@ViewScoped
public class ClienteController extends BasicControlCad<Cliente> implements Serializable {

	private static final long serialVersionUID = 1L;

	private static ClienteDAO clienteDAO = new ClienteDAO();
	private TelCliente telCliente = new TelCliente();

	private boolean disableButton = true;

	private String maskCpf;
	private String valueNome;
	private String valueCpf;
	private String pessoa;

	private String maskTelefone;


	// *******************************************
	// * Alterar somente neste construtor
	// *******************************************
	public ClienteController() throws Exception {
		super(Cliente.class,  clienteDAO);
	}

	//Verifica se telefone j� existe e altera, sen�o adiciona na lista
	public void addTelefone(){
		if(verificaTelefone()){
			List<TelCliente> telefones = ((Cliente)this.getSelected()).getTelefones();
			for (TelCliente telefone : telefones) {
				if(Objects.equals(telCliente, telefone)){
					telefone.setNumero(telCliente.getNumero());
					telefone.setComplemento(telCliente.getComplemento());
					telefone.setTipo(telCliente.getTipo());

					this.telCliente = new TelCliente();
					return;
				}
			}
			((Cliente)this.getSelected()).addTelefone(telCliente);
			telCliente = new TelCliente();
		}
	}
	
	public void openDialog(){
		setaPessoa();
		UtilityTela.executarJavascript("PF('dlgCadastro').show();");
	}

	//Mostra o telefone do cliente para poder alterar
	public void upTelefone(TelCliente telefone){
		telCliente = telefone;
	}

	//Remove telefone da lista
	public void delTelefone(TelCliente telefone){
		((Cliente)this.getSelected()).delTelefone(telefone);

	}

	//Verifica se todos os campos do telefone est�o preenchidos
	public boolean verificaTelefone(){
		if(telCliente.getNumero().equals("")){
			return false;
		}

		if(telCliente.getTipo().equals("")){
			return false;
		}
		return true;
	}

	//Quando seleciona a linha habilita o bot�o cancelar e visualizar
	public void onRowSelect(){
		disableButton = false;
	}

	//Quando seleciona a linha habilita o bot�o cancelar e visualizar
	public void unRowSelect(){
		disableButton = true;
	}

	//Altera mascara e nome do campo dependendo do tipo de pessoa
	public void updateForm(){
		if(this.getPessoa().equals("1") || this.getPessoa() == null){
			this.maskCpf = "999.999.999-99";
			this.valueNome = "Nome";
			this.valueCpf = "CPF";
		}else{
			this.maskCpf = "99.999.999/9999-99";
			this.valueNome = "Raz�o Social";
			this.valueCpf = "CNPJ";
		}
	}

	//Verifica qual tipo de pessoa quando for alterar
	public void setaPessoa(){
		if(getSelected() != null){
			if(((Cliente)this.getSelected()).getTipo() == 1){
				this.setPessoa("1");
			}else{
				this.setPessoa("2");
			}
			this.updateForm();
		}
	}

	//altera a mascara do telefone de acordo com o tipo
	public void updateMaskTelefone() {
		if(telCliente != null) {
			switch (telCliente.getTipo()) {
			case "Residencial":
				maskTelefone = "(99)9999-9999";
				break;
			case "Comercial":
				maskTelefone = "(99)9999-9999";
				break;
			case "Celular":
				maskTelefone = "(99)99999-9999";
				break;
			default:
				break;
			}
		}
	}

	public void limpaFormulario(){
		this.maskCpf = null;
		this.pessoa = null;
		this.valueCpf = null;
		this.valueNome = null;
	}

	@Override
	public void doStartAddRecord() throws Exception {
		this.updateForm();
		super.doStartAddRecord();
	}


	@Override
	public void treatRecord() {
		if(this.getPessoa() == "1" || this.getPessoa().equals("1")){
			((Cliente)this.getSelected()).setTipo(1);
		}else{
			((Cliente)this.getSelected()).setTipo(2);
		}
		super.treatRecord();
		this.limpaFormulario();
		UtilityTela.executarJavascript("PF('dlgCadastro').hide()");
	}

	//****************************************************
	//**************** AUTOCOMPLETE **********************
	//****************************************************
	public List<Cliente> completeClientes(String query){

		List<Cliente> allClientes = clienteDAO.findAtivos();
		List<Cliente> filteredClientes = new ArrayList<>();

		if(allClientes != null) {
			for(Cliente cliente : allClientes) {
				if(cliente.getNome().toLowerCase().contains(query)) {
					filteredClientes.add(cliente);
				}
			}
		}

		return filteredClientes;
	}

	public boolean isDisableButton() {
		return disableButton;
	}

	public void setDisableButton(boolean disableButton) {
		this.disableButton = disableButton;
	}

	public TelCliente getTelCliente() {
		return telCliente;
	}

	public void setTelCliente(TelCliente telCliente) {
		this.telCliente = telCliente;
	}

	public String getMaskCpf() {
		if(maskCpf == null){
			return "999.999.999-99";
		}

		return maskCpf;
	}

	public void setMaskCpf(String maskCpf) {
		this.maskCpf = maskCpf;
	}

	public String getValueNome() {
		if(valueNome == null){
			return "Nome";
		}
		return valueNome;
	}

	public void setValueNome(String valueNome) {
		this.valueNome = valueNome;
	}

	public String getPessoa() {
		if(pessoa == null){
			return "1";
		}
		return pessoa;
	}

	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}

	public String getValueCpf() {
		if(valueCpf == null){
			return "CPF";
		}
		return valueCpf;
	}

	public void setValueCpf(String valueCpf) {
		this.valueCpf = valueCpf;
	}

	public String getMaskTelefone() {
		if(maskTelefone == null) {
			return "(99)9999-9999";
		}
		return maskTelefone;
	}

	public void setMaskTelefone(String maskTelefone) {
		this.maskTelefone = maskTelefone;
	}

}
