
package br.com.sga.controller;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
  
public class BasicMBImpl implements BasicMB, Serializable {
  
       public void addErrorMessage(String componentId, 
         String errorMessage) {
             addMessage(componentId, errorMessage, 
              FacesMessage.SEVERITY_ERROR);
       }
  
       public void addErrorMessage(String errorMessage) {
             addErrorMessage(null, errorMessage);
       }
  
       public void addInfoMessage(String componentId, 
        String infoMessage) {
             addMessage(componentId, infoMessage, 
             FacesMessage.SEVERITY_INFO);
       }
  
       public void addInfoMessage(String infoMessage) {
             addInfoMessage(null, infoMessage);
       }
  
       private void addMessage(String componentId, 
        String errorMessage,
                    FacesMessage.Severity severity) {
             FacesMessage message = new FacesMessage
             (errorMessage);
             message.setSeverity(severity);
             FacesContext.getCurrentInstance().addMessage
             (componentId, message);
       }
}