function openPage(pageName) {
    var i, tabcontent;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
      tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    document.getElementById(pageName).style.display = "block";

    if(pageName=='details')
        getDetails();
    else if(pageName=='players')
        getPlayers();
    else if(pageName=='matches')
        getPairing();
    
  }
  
  function getDetails(){
      $.get('SelectedTournament',{
        ID:localStorage.getItem("tournamentID")
      },function(data){
          data=JSON.parse(data)
          document.getElementById("name").innerHTML="Tournament Name:"+data[0].name
          document.getElementById("date").innerHTML="Date:"+data[0].dateRange
          document.getElementById("location").innerHTML="Location:"+data[0].locationInput
          document.getElementById("rounds").innerHTML="No.of Rounds:"+data[0].rounds
          document.getElementById("duration").innerHTML="Duration:"+data[0].duration
          document.getElementById("win").innerHTML="Win:"+data[0].win
          document.getElementById("loss").innerHTML="Loss:"+data[0].loss
          document.getElementById("bye").innerHTML="BYE:"+data[0].bye
          document.getElementById("draw").innerHTML="Draw:"+data[0].draw
      });
  }
  function getPlayers(){
    var old=document.getElementById('playersList')  
    $.get('PlayerDetails',{
        ID:localStorage.getItem("tournamentID")
      },function(data){
          data=JSON.parse(data)
          const new1=document.createElement('div')
          new1.setAttribute("id","playersList")
          $("#players").append(new1)
          document.getElementById("players").replaceChild(new1,old)
          for(let index=0;index<data.length;index++){
            $("#playersList").append($.parseHTML(createPlayersCard(data[index],index+1)))         
          } 

    });

  }
  function createPlayersCard(data,index){
    const template = `
    <div class="card" id="card${index}">   
      <label class="label" id="name${index}">${index}.Player Name:${data.name}</label>
      <label class="label" id="email${index}">Email:${data.email}</label>       
    </div>
        `;
     return template;
  }

  function getPairing(){
    var old1=document.getElementById('matchesListNoBye')
    var old2=document.getElementById('matchesListBye')
    $.get('pairing',{
        ID:localStorage.getItem("tournamentID"),
        round:1
      },function(data){
          data=JSON.parse(data)
          console.log(localStorage.getItem("tournamentID"))
          const new1=document.createElement('div')
          new1.setAttribute("id","matchesListNoBye")
          $("#matchList").append(new1)
          const new2=document.createElement('div')
          new2.setAttribute("id","matchesListBye")
          $("#matchList").append(new2)
          document.getElementById("matchList").replaceChild(new1,old1)
          document.getElementById("matchList").replaceChild(new2,old2)

          for(let index=0;index<data.length;index++){
              if(data[index].result=="NO_RESULT"){
                $("#matchesListNoBye").append($.parseHTML(createNoByeCard(data[index],index+1)))
                disableButtons(index+1)
              }                   
              else
                  $("#matchesListBye").append($.parseHTML(createByeCard(data[index],index+1))) 
          }
          
          
    });
  }

  function createNoByeCard(data,index){
    const template = `
      <label style="width: 10%;" id="number${index}" class="sublabel">${index}.</label>
      <label style="width: 25%;" id="firstplayer${index}" class="sublabel">${data.player1.name}</label>
      <div style="width: 40%;" id="buttonBox${index}" class="trieBtn">
          <button style="float: left;" id="firstBtn${index}" class="button">W:L</button>
          <button style="float: left;" id="middleBtn${index}" class="button">D:D</button>
          <button style="float: left;" id="lastBtn${index}" class="button">L:W</button>
      </div>
      <label style="width: 25%;" id="secondplayer${index}" class="sublabel">${data.player2.name}</label>     
        `;
     return template;
  }

  function createByeCard(data,index){
    const template = `
      <label style="width: 10%;" class="sublabel">${index}.</label>
      <label style="width: 25%;" class="sublabel">${data.player1.name}</label>
      <label style="width: 40%;" class="sublabel">-</label>
      <label style="width: 25%;" class="sublabel">BYE</label>    
        `;
     return template;
  }

  function disableButtons(index){
    console.log("buttonBox"+index)
    var header = document.getElementById("buttonBox"+index);
    var btns = header.getElementsByClassName("button");
    for (var i = 0; i < btns.length; i++) {
      btns[i].addEventListener("click", function() {
      var current = header.getElementsByClassName("active");
      if(current.length==0){
        this.className += " active";
      }
      else{
      current[0].className = current[0].className.replace(" active", "");
      this.className += " active";
      }
      });
    }
  }