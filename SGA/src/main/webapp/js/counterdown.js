//Script para refresh na tela de monitoramento
var tempo = new Number();
tempo = 90; // Tempo em segundos
function startCountdown(){
	if((tempo - 1) >= 0){
		var min = parseInt(tempo/60);
		var seg = tempo%60;
		if(min < 10){
			min = "0"+min;
			min = min.substr(0, 2);}
		if(seg <=9){
			seg = "0"+seg;}
		horaImprimivel = min + ':' + seg;
		$("#countdown").html(horaImprimivel);
		setTimeout('startCountdown()',1000);
		tempo--;
	} else {
		window.location.reload();}
}
startCountdown();