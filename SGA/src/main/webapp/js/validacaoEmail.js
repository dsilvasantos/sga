//alert('qualquer coisa');
function validaEmail(email) {
    var filter = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;

    if (!filter.test(document.querySelector("#email").value)) {
        document.querySelector("#email").innerHTML="<font color='red'>Email inválido </font>";
        alert('Por favor, digite o email corretamente');
        return false;
    }else {
        console.log("email valido");
    }
}
