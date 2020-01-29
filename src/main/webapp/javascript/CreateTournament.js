 var name="";
 var daterange="";
 var locationInput="";
function openPage(pageName) {
    var i, tabcontent;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
      tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    document.getElementById(pageName).style.display = "block";
  
  }
  
  document.getElementById("defaultOpen").click();
  
  $(function() {
    
    $('input[name="datefilter"]').daterangepicker({
        autoUpdateInput: false,
        locale: {
            cancelLabel: 'Clear'
        }
    });
    
    $('input[name="datefilter"]').on('apply.daterangepicker', function(ev, picker) {
        $(this).val(picker.startDate.format('MM/DD/YYYY') + ' - ' + picker.endDate.format('MM/DD/YYYY'));
    });
    
    $('input[name="datefilter"]').on('cancel.daterangepicker', function(ev, picker) {
        $(this).val('');
    });
  
  });

  $("#nextBtn").on("click",function(){
    name=$("#name").val();
    daterange=$("#daterange").val();
    locationInput=$("#location").val();


    document.getElementById("info").style.display = "none";
    document.getElementById("rules").style.display = "block";

    
  });

  $("#createBtn").on("click",function(){
    var rounds=$("#rounds").val();
    var duration=$("#duration").val();
    var Win=$("#Win").val();
    var Loss=$("#Loss").val();
    var BYE=$("#BYE").val();
    var Draw=$("#Draw").val();
    //var createdBy=document.cookie.split(" ")[0]

    $.post('createTournament',{
      name:name,
      daterange:daterange,
      locationInput:locationInput,
      rounds:rounds,
      duration:duration,
      Win:Win,
      Loss:Loss,
      BYE:BYE,
      Draw:Draw
      //createdBy:createdBy

    },function(data,status){
      
      if(data=="success"){
        alert("Tournament created");
        window.location.replace("home.html")
      }       
      else
        alert("Failed. Please try again.")

    });




  });

