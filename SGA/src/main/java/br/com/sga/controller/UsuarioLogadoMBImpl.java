package br.com.sga.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import br.com.sga.monitoramento.DAO.UsuarioDAO;
import br.com.sga.monitoramento.model.Usuario;
import br.com.sga.services.SessionContext;

/**
 * Controla o LOGIN e LOGOUT do Usuário
 * */
@ManagedBean(name = "usuarioLogadoMB")
@SessionScoped
public class UsuarioLogadoMBImpl extends BasicMBImpl{
  
    private static final long serialVersionUID = 1L;
  
    private static Logger logger = Logger.getLogger(UsuarioLogadoMBImpl.class);
  
    @ManagedProperty(value = "#{userBO}")
    private UsuarioDAO userBO;
  
    private String email;
    private String login;
    private String senha;
  
    /**
     * Retorna usuario logado
     * */
    public Usuario getUser() {
       return (Usuario) SessionContext.getInstance().getUsuarioLogado();
    }
  
    public String doLogin() {
       try {
    	   userBO =  new UsuarioDAO();
           logger.info("Tentando logar com usuário " + login);
           Usuario user = userBO.retornaUsuario(login, senha);
  
           if (user == null) {
             addErrorMessage("Login ou Senha incorretos, tente novamente !");
             FacesContext.getCurrentInstance().validationFailed();
             return "";
           }
           
           if (user.getStatus() == 0) {
               addErrorMessage("Login bloqueado!");
               FacesContext.getCurrentInstance().validationFailed();
               return "";
             }
  
 
           logger.info("Login efetuado com sucesso");
           SessionContext.getInstance().setAttribute("usuarioLogado", user);
           return "/view/inicio.xhtml?faces-redirect=true";
       } catch (Exception e) {
           addErrorMessage(e.getMessage());
           FacesContext.getCurrentInstance().validationFailed();
           e.printStackTrace();
           return "";
       }
  
    }
  
    public String doLogout() {
       logger.info("Fazendo logout com usuário "
               + SessionContext.getInstance().getUsuarioLogado().getLogin());
       SessionContext.getInstance().encerrarSessao();
       addInfoMessage("Logout realizado com sucesso !");
       return "/view/login.xhtml?faces-redirect=true";
    }
  
    /*
    public void solicitarNovaSenha() {
       try {
           getUserBO().gerarNovaSenha(login, email);
           addInfoMessage("Nova Senha enviada para o email " + email);
       } catch (BOException e) {
           addErrorMessage(e.getMessage());
           FacesContext.getCurrentInstance().validationFailed();
       }
    }
  
  */
    public UsuarioDAO getUserBO() {
       return userBO;
    }
  
    public void setUserBO(UsuarioDAO userBO) {
       this.userBO = userBO;
    }
  
    public String getLogin() {
       return login;
    }
  
    public void setLogin(String login) {
       this.login = login;
    }
  
    public String getSenha() {
       return senha;
    }
  
    public void setSenha(String senha) {
       this.senha = senha;
    }
  
    public String getEmail() {
       return email;
    }
  
    public void setEmail(String email) {
       this.email = email;
    }
  
}