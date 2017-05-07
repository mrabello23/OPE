package br.edu.impacta.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

@Named
@SessionScoped
public class UtilityTela implements Serializable{

	private static final long serialVersionUID = 1L;

	public static void executarJavascript(String comando) {
        RequestContext.getCurrentInstance().execute(comando);
    }
	
	public static void atualizarComponente(String id) {
        RequestContext.getCurrentInstance().update(id);
    }

    public static void criarMensagemErro(String titulo, String texto) {
    	Messages.create(titulo).error().detail(texto).add();
    }
    
    public static void criarMensagemErroSemDetail(String texto) {
    	Messages.create(texto).error().add();
    }

    public static void criarMensagemAviso(String titulo, String texto) {
    	Messages.create(titulo).warn().detail(texto).add();
    }
    
    public static void criarMensagemFatalErro(String titulo, String texto) {
    	Messages.create(titulo).fatal().detail(texto).add();
    }

    public static void criarMensagem(String titulo, String texto) {
    	Messages.create(titulo).detail(texto).add();
    }
    
    public static String getBooleanImg(Boolean boo) {
        if (boo == null) {
            return null;
        }
        if (boo) {
            return "/resources/images/bus_green.png";
        } else {
            return "/resources/images/bus_red.png";
        }
    }
    
    public static String getDescBoolean(Boolean boo) {
        if ( boo == null) {
            return "N�o";
        }
        if (boo) {
            return "Sim";
        } else {
            return "N�o";
        }
    }

}
