//var brandAndCatData;
//var orderItemData;

function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}
//function getReportUrl(){
//	var baseUrl = $("meta[name=baseUrl]").attr("content")
//	return baseUrl + "/api/reports/sales";
//}
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
        csva.download = "brand_report.csv"
}
function getBrandList(){
	var url = getBrandUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandList(data);
	   },
	   error: handleAjaxError
	});
}
function displayBrandList(data){
	var $tbody = $('#brand-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	toCsv();
}

//function displayReport(data){
//    console.log(data)
//	var $tbody = $('#report').find('tbody');
//	$tbody.empty();
//	for(var i in data){
//		var e = data[i];
//		var row = '<tr>'
//		+ '<td>'  + e.brand + '</td>'
//		+ '<td>'  + e.category + '</td>'
//		+ '<td>'  + e.quantity + '</td>'
//		+ '<td>'  + e.total + '</td>'
//		+ '</tr>';
//        $tbody.append(row);
//	}
//	toCsv(data);
//}
//function submits(event){
//    	var $form = $("#form");
//    	var json = toJson($form);
//    	var url = getReportUrl();
//    	$.ajax({
//    	   url: url,
//    	   type: 'POST',
//    	   data: json,
//    	   headers: {
//           	'Content-Type': 'application/json'
//           },
//    	   success: function(response) {
//    	   		displayReport(response);
//    	   },
//    	   error: handleAjaxError
//    	});
//
//    	return false;
//}

//function updateCats(event){
//    document.getElementById("category").innerHTML = "";
//    for(var i in brandAndCatData){
//        var e = brandAndCatData[i];
//            if(e.brand == event.value){
//                document.getElementById("category").innerHTML += '<option>' + e.category + '</option>';
//            }
//    }
//    document.getElementById("category").innerHTML += '<option value="">Null</option>';
//}
//function getBrandList(){
//	var url = getBrandUrl();
//	$.ajax({
//	   url: url,
//	   type: 'GET',
//	   success: function(data) {
//	   		dropdown(data);
//	   		brandAndCatData = data;
//	   },
//	   error: handleAjaxError
//	});
//
//}

//function dropdown(data){
////    	var $drpdwn = $('#menu-brand');
//    	document.getElementById("brand").innerHTML = "";
//    	var brands = new Set();
//    	for(var i in data){
//    		var e = data[i];
////            $drpdwn.append('<a class="dropdown-item">' + e.brand + '</a>');
//                if(!brands.has(e.brand)){
//                    document.getElementById("brand").innerHTML += '<option>' + e.brand + '</option>';
//                    brands.add(e.brand);
//                }
//        }
//        document.getElementById("brand").innerHTML += '<option value="">Null</option>';
//}


$(document).ready(getBrandList);

//INITIALIZATION CODE
//function init(){
//	$('#add-product').click(addProduct);
//	$('#update-product').click(updateProduct);
//	$('#refresh-data').click(getProductList);
//	$('#upload-data').click(displayUploadData);
//	$('#process-data').click(processData);
//	$('#download-errors').click(downloadErrors);
//    $('#productFile').on('change', updateFileName)
//}


//$(document).ready(getProductList);


//function getProductUrl(){
//	var baseUrl = $("meta[name=baseUrl]").attr("content")
//	return baseUrl + "/api/reports";
//}
////BUTTON ACTIONS
//function addProduct(event){
//	//Set the values to update
//	var $form = $("#product-form");
//	var json = toJson($form);
//	var url = getProductUrl();
//
//	$.ajax({
//	   url: url,
//	   type: 'POST',
//	   data: json,
//	   headers: {
//       	'Content-Type': 'application/json'
//       },
//	   success: function(response) {
//	   		getProductList();
//	   },
//	   error: handleAjaxError
//	});
//
//	return false;
//}
//
//function updateProduct(event){
//	$('#edit-product-modal').modal('toggle');
//	//Get the ID
//	var id = $("#product-edit-form input[name=id]").val();
//	var url = getProductUrl() + "/" + id;
//
//	//Set the values to update
//	var $form = $("#product-edit-form");
//	var json = toJson($form);
//
//	$.ajax({
//	   url: url,
//	   type: 'PUT',
//	   data: json,
//	   headers: {
//       	'Content-Type': 'application/json'
//       },
//	   success: function(response) {
//	   		getProductList();
//	   },
//	   error: handleAjaxError
//	});
//
//	return false;
//}
//
//
//function getProductList(){
//	var url = getProductUrl();
//	$.ajax({
//	   url: url,
//	   type: 'GET',
//	   success: function(data) {
//	   		displayProductList(data);
//	   },
//	   error: handleAjaxError
//	});
//}
//
//function deleteProduct(id){
//	var url = getProductUrl() + "/" + id;
//
//	$.ajax({
//	   url: url,
//	   type: 'DELETE',
//	   success: function(data) {
//	   		getProductList();
//	   },
//	   error: handleAjaxError
//	});
//}
//
//// FILE UPLOAD METHODS
//var fileData = [];
//var errorData = [];
//var processCount = 0;
//
//
//function processData(){
//	var file = $('#productFile')[0].files[0];
//	readFileData(file, readFileDataCallback);
//}
//
//function readFileDataCallback(results){
//	fileData = results.data;
//	uploadRows();
//}
//
//function uploadRows(){
//	//Update progress
//	updateUploadDialog();
//	//If everything processed then return
//	if(processCount==fileData.length){
//		return;
//	}
//
//	//Process next row
//	var row = fileData[processCount];
//	processCount++;
//
//	var json = JSON.stringify(row);
//	var url = getProductUrl();
//    console.log(json);
//	//Make ajax call
//	$.ajax({
//	   url: url,
//	   type: 'POST',
//	   data: json,
//	   headers: {
//       	'Content-Type': 'application/json'
//       },
//	   success: function(response) {
//	   		uploadRows();
//	   },
//	   error: function(response){
//	   		row.error=response.responseText
//	   		errorData.push(row);
//	   		uploadRows();
//	   }
//	});
//
//}
//
//function downloadErrors(){
//	writeFileData(errorData);
//}
//
////UI DISPLAY METHODS
//
//function displayProductList(data){
//	var $tbody = $('#product-table').find('tbody');
//	$tbody.empty();
//	for(var i in data){
//		var e = data[i];
//		console.log(e.brand);
//		console.log("e.brand");
//		var buttonHtml = '<button onclick="deleteProduct(' + e.id + ')">delete</button>'
//		buttonHtml += ' <button onclick="displayEditProduct(' + e.id + ')">edit</button>'
//		var row = '<tr>'
//		+ '<td>' + e.id + '</td>'
//		+ '<td>' + e.barcode + '</td>'
//		+ '<td>'  + e.brand + '</td>'
//		+ '<td>'  + e.category + '</td>'
//		+ '<td>'  + e.name + '</td>'
//		+ '<td>'  + e.mrp + '</td>'
//		+ '<td>' + buttonHtml + '</td>'
//		+ '</tr>';
//        $tbody.append(row);
//	}
//}
//
//function displayEditProduct(id){
//	var url = getProductUrl() + "/" + id;
//	$.ajax({
//	   url: url,
//	   type: 'GET',
//	   success: function(data) {
//	   		displayProduct(data);
//	   },
//	   error: handleAjaxError
//	});
//}
//
//function resetUploadDialog(){
//	//Reset file name
//	var $file = $('#productFile');
//	$file.val('');
//	$('#ProductFileName').html("Choose File");
//	//Reset various counts
//	processCount = 0;
//	fileData = [];
//	errorData = [];
//	//Update counts
//	updateUploadDialog();
//}
//
//function updateUploadDialog(){
//	$('#rowCount').html("" + fileData.length);
//	$('#processCount').html("" + processCount);
//	$('#errorCount').html("" + errorData.length);
//}
//
//function updateFileName(){
//	var $file = $('#productFile');
//	var fileName = $file.val();
//	$('#ProductFileName').html(fileName);
//}
//
//function displayUploadData(){
// 	resetUploadDialog();
//	$('#upload-product-modal').modal('toggle');
//}
//
//function displayProduct(data){
//	$("#product-edit-form input[name=barcode]").val(data.barcode);
//	$("#product-edit-form input[name=brand]").val(data.brand);
//	$("#product-edit-form input[name=category]").val(data.category);
//	$("#product-edit-form input[name=name]").val(data.name);
//	$("#product-edit-form input[name=mrp]").val(data.mrp);
//	$("#product-edit-form input[name=id]").val(data.id);
//	$('#edit-product-modal').modal('toggle');
//}




