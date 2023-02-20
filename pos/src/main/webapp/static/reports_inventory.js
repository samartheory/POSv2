
function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/invent";
}

function toCsv(){
      var csv_data=[];
      csv_data.push("ID,");
        // Get each row data
        var rows = document.getElementsByTagName('tr');
        for (var i = 0; i < rows.length; i++) {

            // Get each column data

            var cols = rows[i].querySelectorAll('td,th');

            // Stores each csv row data
            var csvrow = [];
            for (var j = 0; j < cols.length; j++) {

                // Get the text data of each cell of
                // a row and push it to csvrow
                csvrow.push(cols[j].innerHTML);
            }

            // Combine each column value with comma
            csv_data.push(csvrow.join(","));
        }
        // combine each row data with new line character
        csv_data = csv_data.join('\n');
        var csva = document.getElementById("csv");
        csva.href = "data:text/csv;" + csv_data
        csva.download = "inventory_report.csv"
}
function getInventoryList(){
	var url = getInventoryUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventoryList(data);
	   },
	   error: handleAjaxError
	});
}


function displayInventoryList(data){
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	toCsv();
}
function navbarSelect(){
    var role = $("meta[name=role]").attr("content");
    if(role == "admin"){
//        document.getElementById("navselect").innerHTML = '<nav th:replace="snippets.html :: name_app_navbar"></nav>'
//    $('#navselect').html('<nav th:replace="snippets.html :: name_app_navbar"></nav>');
        document.getElementById("foradmin").style.display = "block";
    }
    else{
        document.getElementById("forstandard").style.display = "block";
    }
}


$(document).ready(getInventoryList);

$(document).ready(navbarSelect);