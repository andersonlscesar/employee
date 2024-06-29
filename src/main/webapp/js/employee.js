$(document).ready(function(){
	// Activate tooltip
	$('[data-toggle="tooltip"]').tooltip();
	
	// Select/Deselect checkboxes
	var checkbox = $('table tbody input[type="checkbox"]');
	$("#selectAll").click(function(){
		if(this.checked){
			checkbox.each(function(){
				this.checked = true;                        
			});
		} else{
			checkbox.each(function(){
				this.checked = false;                        
			});
		} 
	});
	checkbox.click(function(){
		if(!this.checked){
			$("#selectAll").prop("checked", false);
		}
	});
});


$("#deleteBtn").click
(


	function()
	{
		console.log("Click");
		var deletedEmployees = [];
		$("input:checkbox[class-='employeeCheckBox']:checked").each(function(){
			deletedEmployees.push($(this).val());
		});

		deletedEmployees = deletedEmployees.join(",");
		var employeeIds = deletedEmployees.toString();
		var response = "";
		$.ajax(
			{
				url: "delete",
				async: false,
				type: "POST",
				data: {"employeeId": employeeIds},
				success: function(data, textStatus, jqXHR)
				{
					if (data !== "") { response = data; }
					else { response = ""; }
					window.location.href = "/employee_war_exploded/"
				},
				error: function(jqXHR, textStatus, errorThrown)
				{
					console.log("Something went wrong ", errorThrown);
					response = "";
					alert("Exception, errorThrown" + errorThrown);
				}
			}
		)
	}

)