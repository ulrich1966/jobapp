/**
 * 
 */
function moin() {
	alert('Moin!');
}

function switchPass() {
	$(".eye-cmd").click(function() {
		
		var type = $(".imp-pass").attr("type");		
	
		if(type == "password"){
			$(".imp-pass").attr("type", "text")
			$(".eye-cmd").attr("class", "eye-cmd fa fa-eye-slash");
		} 

		if(type == "text"){
			$(".imp-pass").attr("type", "password")		
			$(".eye-cmd").attr("class", "eye-cmd fa fa-eye");
		} 
				
	});
}