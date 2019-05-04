package br.com.sga.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import br.com.sga.monitoramento.DAO.DepartamentoDAO;
import br.com.sga.monitoramento.model.Departamento;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@ManagedBean(name = "relatorio")
@ViewScoped
public class RelatorioController {

	private Date dataInicial;

	private Date dataFinal;

	private String msg;

	private String departamento;

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public void gerarRecursosPorAplicacao() {
		if (dataFinal == null || dataFinal == null) {
			msg = "Valores informados inválidos";
			return;
		}
		if (dataInicial.after(dataFinal)) {
			msg = "Data inicial superior a data final";
			return;
		}
		
		Map parametros = new HashMap<>();
		parametros.put("dataInicial", dataInicial);
		parametros.put("dataFinal", dataFinal);
		
		gerarRelatorio("RecursosPorAplicacao.jrxml",parametros,"RecursosPorAplicacao");
	}

	public void gerarRecursosPorDepartamento() throws JRException, IOException, SQLException, NamingException {
		if (departamento == null) {
			msg = "Valores informados inválidos";
			return;
		}
		if (dataFinal == null || dataFinal == null) {
			msg = "Valores informados inválidos";
			return;
		}
		if (dataInicial.after(dataFinal)) {
			msg = "Data inicial superior a data final";
			return;
		}
		DepartamentoDAO departamentoDAO = new DepartamentoDAO();
		Departamento departamentoRec = departamentoDAO.retornaDepartamento(departamento);

		Map parametros = new HashMap<>();
		parametros.put("dataInicial", dataInicial);
		parametros.put("dataFinal", dataFinal);
		parametros.put("departamento", departamentoRec.getId());
		
		gerarRelatorio("RecursosPorDepartamento.jrxml",parametros,"RecursosPorDepartamento");
	}
	
	public void gerarErrosPorDepartamento() throws JRException, IOException, SQLException, NamingException {
		if (departamento == null) {
			msg = "Valores informados inválidos";
			return;
		}
		if (dataFinal == null || dataFinal == null) {
			msg = "Valores informados inválidos";
			return;
		}
		if (dataInicial.after(dataFinal)) {
			msg = "Data inicial superior a data final";
			return;
		}
		DepartamentoDAO departamentoDAO = new DepartamentoDAO();
		Departamento departamentoRec = departamentoDAO.retornaDepartamento(departamento);

		Map parametros = new HashMap<>();
		parametros.put("dataInicial", dataInicial);
		parametros.put("dataFinal", dataFinal);
		parametros.put("departamento", departamentoRec.getId());
		
		gerarRelatorio("ErroPorDepartamento.jrxml",parametros,"ErroPorDepartamento");
	}

	public void gerarRelatorio(String jasperRelatorio, Map parametros,String nomeRelatorio) {

		try {

			String path = "/WEB-INF/reports/" + jasperRelatorio;
			InputStream jasperTemplate = FacesContext.getCurrentInstance().getExternalContext()
					.getResourceAsStream(path);

			JasperReport jasper = JasperCompileManager.compileReport(jasperTemplate);

			InitialContext initialContext = new InitialContext();
			DataSource dataSource = (DataSource) initialContext.lookup("java:jboss/datasources/sga");
			Connection conexao = dataSource.getConnection();

			JasperPrint jasperprint = JasperFillManager.fillReport(jasper, parametros, conexao);

			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
					.getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + nomeRelatorio + ".pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperprint, servletOutputStream);
			servletOutputStream.flush();
			servletOutputStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			System.out.println("Erro ao emitir relatorio:" + nomeRelatorio);
			System.out.println(e.getMessage());
		}
	}
}
