package br.com.sga.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.sga.monitoramento.model.Server;
import br.com.sga.monitoramento.model.AplicacaoThread;

public class ThreadServices {
	
	private ServicosCLI service = new ServicosCLI();
	
	public List<AplicacaoThread> getTenMoreThreads(Server server)
	{
		List<AplicacaoThread> allThreads = getAllThreads(server);
		List<AplicacaoThread> tenThreads = new ArrayList<AplicacaoThread>();
		Collections.sort(allThreads);
		
		for(int i=allThreads.size()-1;i>=allThreads.size()-10;i--){
			tenThreads.add(getThreadsInformations(allThreads.get(i), server));
		}
		
		Collections.sort(tenThreads);
		return tenThreads;
	}
	
	
	private List<AplicacaoThread> getAllThreads(Server server){
		
		String cmdAllThreads = "/host="+server.getHost()+"/server="+server.getNome()+"/core-service=platform-mbean/type=threading:read-resource(include-runtime=true)";
		String[] ids = parseResult(service.readResource(cmdAllThreads));
		AplicacaoThread thread;
		List<AplicacaoThread> threads = new ArrayList<AplicacaoThread>();
		
		for(int i=0;i<ids.length;i++){
			thread = new AplicacaoThread();
			thread.setId(Long.parseLong(ids[i].trim().replace("L", "")));
			String cmdCpuTime =  "/host="+server.getHost()+"/server="+server.getNome()+"/core-service=platform-mbean/type=threading:get-thread-cpu-time(id="+thread.getId()+")";
			String res = service.readResource(cmdCpuTime).replace("L", "");
			thread.setCpu_time(Long.parseLong(res));
			threads.add(thread);
		}	
		return threads;
	}
	
	private String[] parseResult(String resultado){
		String startString = "\\[";
		String endString = "]";
		Pattern p1 = Pattern.compile(startString);
		Matcher m1 = p1.matcher(resultado);
		Pattern p2 = Pattern.compile(endString);
		Matcher m2 = p2.matcher(resultado);
		m1.find();
		m2.find();
		int start = m1.end();
		int end = m2.start();
		String id = resultado.substring(start,end);
		return id.split(",");
	}
	
	private AplicacaoThread getThreadsInformations(AplicacaoThread thread,Server server){
		String command = "/host="+server.getHost()+"/server="+server.getNome()+"/core-service=platform-mbean/type=threading:get-thread-info(id="+thread.getId()+")";
		String[] resultado = service.readResource(command).split(",");
		String name = resultado[1].replace("\"","").replace("thread-name", "").replace("=>", "").trim();
		String state = resultado[2].replace("\"", "").replace("thread-state", "").replace("=>", "").trim();
		String blockTime = resultado[3].replace("\"", "").replace("blocked-time", "").replace("=>", "").replace("L", "").trim();
		String waitedTime = resultado[5].replace("\"", "").replace("waited-time", "").replace("=>", "").replace("L", "").trim();
		String lockName = resultado[9].replace("\"", "").replace("locked-name", "").replace("=>", "").trim();
		String stackTrace = resultado[12].replace("\"", "").replace("stack-trace", "").replace("=>", "").trim();
		
		thread.setName(name);
		thread.setState(state);
		thread.setBlocked_time(Long.parseLong(blockTime));
		thread.setWaited_time(Long.parseLong(waitedTime));
		thread.setLock_name(lockName);
		thread.setStackTrace(stackTrace);
		
		return thread;
	}
	
}
