//$("#defaultOpen").click()
function clickNavBar(evt, NavOption) {
  if(NavOption=="joined"){
    var old=document.getElementById('joinedList')  
    $.get(NavOption, {
      ID : document.cookie.split(" ")[0]   
      }, function(data) {
          
          data=JSON.parse(data)
          const new1=document.createElement('div')
          new1.setAttribute("id","joinedList")
          $("#joined").append(new1)
          document.getElementById("joined").replaceChild(new1,old)
          
          for(let index=0;index<data.length;index++){
            $("#joinedList").append($.parseHTML(createCard1(data[index],index+1)))         
          }          
    });
  }
  else{
    var old=document.getElementById('createdList')  
    $.get(NavOption, {
      ID : document.cookie.split(" ")[0]   
      }, function(data) {
          
          data=JSON.parse(data)
          const new1=document.createElement('div')
          new1.setAttribute("id","createdList")
          $("#created").append(new1)
          document.getElementById("created").replaceChild(new1,old)
          
          for(let index=0;index<data.length;index++){
            $("#createdList").append($.parseHTML(createCard2(data[index],index+1)))         
          }          
    });
  }
  

  var i, tabcontent, tablinks;

  // Get all elements with class="tabcontent" and hide them
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  // Get all elements with class="tablinks" and remove the class "active"
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }

  // Show the current tab, and add an "active" class to the link that opened the tab
  document.getElementById(NavOption).style.display = "block";
  evt.currentTarget.className += " active";

}

function createCard1(data,index){
  const template = `
  <div class="card" id="card${index}">
    
    <label class="label" id="name${index}">Tournament Name:${data.name}</label>
    <label class="label" id="date${index}">Date:${data.dateRange}</label>       
    <label class="label" id="location${index}">Location:${data.locationInput}</label>       
    <label class="label" id="rounds${index}">No.of Rounds:${data.rounds}</label>
    <label class="label" id="duration${index}">Duration:${data.duration}</label>

  </div>
      `;
   return template;
}

function createCard2(data,index){
  const template = `
  <div class="card" id="card${index}" onclick="arrowBtn(${data.tournamentId},${data.rounds},${data.win},${data.loss},${data.bye},${data.draw})")>
    <div style="float:left; width:75%;">
        <label class="label" id="name${index}">Tournament Name:${data.name}</label>
        <label class="label" id="date${index}">Date:${data.dateRange}</label>       
        <label class="label" id="location${index}">Location:${data.locationInput}</label>       
        <label class="label" id="rounds${index}">No.of Rounds:${data.rounds}</label>
        <label class="label" id="duration${index}">Duration:${data.duration}</label>
    </div>
    <div style="float:right; width:25%;">
        <label class="arrow">&#8250;</label>
    </div>
</div>
      `;
   return template;
}

function arrowBtn(tournamentID,totalRounds,win,loss,bye,draw){
  localStorage.setItem("tournamentID",tournamentID)
  localStorage.setItem("win",win)
  localStorage.setItem("loss",loss)
  localStorage.setItem("bye",bye)
  localStorage.setItem("draw",draw)
  window.location.replace("TournamentDetails.html")
}