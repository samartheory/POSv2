
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orders";
}
function onlyBaseUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl;
}
//function orderIdTitle(){
//    var orderId = $("meta[name=orderId]").attr("orderId")
//    console.log(orderId);
//    var myElement = document.getElementById("order-title");
//        myElement.innerHTML = "Order #" + orderId;
//}
function orderIdTitle(){
    // Get the query string from the URL
    var queryString = window.location.href;
    console.log(queryString);
    // Get the value of a specific parameter
    var idfromurl =  queryString.split("order-item/")[1];
    console.log(idfromurl);
    var myElement = document.getElementById("order-title");
    myElement.innerHTML = "Order #" + idfromurl;
}
//BUTTON ACTIONS
function addOrder(event){
	//Set the values to update
	var $form = $("#order-form");
	var json = toJson($form);
	var url = getOrderUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getOrderList();
	   },
	   error: handleAjaxError
	});

	return false;
}
function place(id){
	//Set the values to update
	var url = getOrderUrl() + '/'+id;

	$.ajax({
	   url: url,
	   type: 'POST',
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getOrderList();
	   },
	   error: handleAjaxError
	});

	return false;
}
function updateOrder(event){
	$('#edit-order-modal').modal('toggle');
	//Get the ID
	var id = $("#order-edit-form input[name=id]").val();
	var url = getOrderUrl() + "/" + id;

	//Set the values to update
	var $form = $("#order-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getOrderList();
	   },
	   error: handleAjaxError
	});

	return false;
}


function getOrderList(){
	var url = getOrderUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderList(data);
	   },
	   error: handleAjaxError
	});
}

function deleteOrder(id){
	var url = getOrderUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getOrderList();
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#orderFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}
	
	//Process next row
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	var url = getOrderUrl();
    console.log(json);
	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		uploadRows();  
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}
function redirect(id){
    window.location.href = onlyBaseUrl() + "/ui/orderitem/" + id;
}
function redirectPlaced(id){
    window.location.href = onlyBaseUrl() + "/ui/orderitemplaced/" + id;
}
//UI DISPLAY METHODS

function displayOrderList(data){
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		if(e.status == false){
            var buttonHtml = '<button type="button" class="btn btn-outline-danger btn-sm" onclick="redirect(' + e.id + ')">Edit</button>  '
                buttonHtml += '<button type="button" class="btn btn-success btn-sm" onclick="place(' + e.id + ')">Place</button>  '
            var row = '<tr>'
                    + '<td>' + e.id + '</td>'
                    + '<td>' + e.time + '</td>'
                    + '<td>Incomplete</td>'
                    + '<td>' + buttonHtml + '</td>'
                    + '</tr>';
            $tbody.append(row);
        }
        else{
            var buttonHtml = '<button type="button" class="btn btn-primary btn-sm" onclick="redirectPlaced(' + e.id + ')">View</button>  '
            var row = '<tr>'
                    + '<td>' + e.id + '</td>'
                    + '<td>' + e.time + '</td>'
                    + '<td>Complete</td>'
                    + '<td>' + buttonHtml + '</td>'
                    + '</tr>';
            $tbody.append(row);
        }
	}
}

function displayEditOrder(id){
	var url = getOrderUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrder(data);
	   },
	   error: handleAjaxError
	});	
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#orderFile');
	$file.val('');
	$('#OrderFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts	
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#orderFile');
	var fileName = $file.val();
	$('#OrderFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog(); 	
	$('#upload-order-modal').modal('toggle');
}

function displayOrder(data){
	$("#order-edit-form input[name=time]").val(data.time);
	$("#order-edit-form input[name=id]").val(data.id);
	$("#order-edit-form input[name=status]").val(data.status);
	$('#edit-order-modal').modal('toggle');
}

//INITIALIZATION CODE
function init(){
console.log("inside init");
	$('#add-order').click(addOrder);
	$('#update-order').click(updateOrder);
	$('#refresh-data').click(getOrderList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#orderFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getOrderList);
