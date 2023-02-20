function getBrandUrl() {
  var baseUrl = $("meta[name=baseUrl]").attr("content");
  return baseUrl + "/api/daySalesReport";
}

var report;

//get the list of all brands
function getBrandList() {
  var url = getBrandUrl();
  $.ajax({
    url: url,
    type: "GET",
    success: function (data) {
      report = data;
      displayBrandList(data);
      handleSuccess("Successfully updated!")
    },
    error: handleAjaxError,
  });
}

//UI DISPLAY METHODS -- //+ '<td>' + buttonHtml + '</td>'

function displayBrandList(data) {
  var $tbody = $("#brand-table").find("tbody");
  $tbody.empty();
  for (var i in data) {
    var e = data[i];
    var buttonHtml =
      '<button onclick="deleteBrand(' + e.id + ')">delete</button>';
    //buttonHtml += ' <button onclick="displayEditBrand(' + e.id + ')">edit</button>'
    var row =
      "<tr>" +
      "<td>" +
      e.date +
      "</td>" +
      "<td>" +
      e.orderCount +
      "</td>" +
      "<td>" +
       e.orderItemCount +
       "</td>" +
      "<td>" +
      e.totalRevenue +
      "</td>" +
      "</tr>";
    $tbody.append(row);
  }
}

function downloadReport() {
    writeFileData(report)
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

//run code when DOM is ready
$(document).ready(getBrandList);
$(document).ready(navbarSelect);