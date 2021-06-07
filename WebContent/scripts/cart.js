/**
 * 
 */
/* $('input:text').keypress(function(e){
    var code = e.keyCode || e.which;
	var btn = document.getElementById("eqBtn");

    if( code === 13 ) {
        e.preventDefault();
        btn.click();
    };
}); */
document.getElementById("inp")
    .addEventListener("keyup", function(event) {
    event.preventDefault();
    if (event.keyCode === 13) {
        document.getElementById("eqBtn").click();
    }
});