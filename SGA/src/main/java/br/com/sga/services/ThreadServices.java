package br.com.sga.services;

public class ThreadServices {
	/*
	
	public List<ServerThread> getTenMoreThreads(Server server)
	{
		List<ServerThread> allThreads = getAllThreads(cli,server);
		List<ServerThread> tenThreads = new ArrayList<ServerThread>();
		Collections.sort(allThreads);
		
		for(int i=allThreads.size()-1;i>=allThreads.size()-10;i--){
			tenThreads.add(getThreadsInformations(cli, allThreads.get(i), server));
		}
		
		Collections.sort(tenThreads);
		disconnect(cli);
		return tenThreads;
	}
	
	
	private List<ServerThread> getAllThreads(CLI cli,Server server){
		
		String cmdAllThreads = "/host="+server.getNomeHost()+"/server="+server.getNomeServer()+"/core-service=platform-mbean/type=threading:read-resource(include-runtime=true)";
		String[] ids = parseResult(service.readResource(cli, cmdAllThreads));
		ServerThread thread;
		List<ServerThread> threads = new ArrayList<ServerThread>();
		
		for(int i=0;i<ids.length;i++){
			thread = new ServerThread();
			thread.setId(Long.parseLong(ids[i].trim().replace("L", "")));
			String cmdCpuTime =  "/host="+server.getNomeHost()+"/server="+server.getNomeServer()+"/core-service=platform-mbean/type=threading:get-thread-cpu-time(id="+thread.getId()+")";
			String res = service.readResource(cli, cmdCpuTime).replace("L", "");
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
	
	private ServerThread getThreadsInformations(ServerThread thread,Server server){
		String command = "/host="+server.getNomeHost()+"/server="+server.getNomeServer()+"/core-service=platform-mbean/type=threading:get-thread-info(id="+thread.getId()+")";
		String[] resultado = service.readResource(cli, command).split(",");
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
	*/
}
