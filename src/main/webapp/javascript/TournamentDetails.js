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
    // else if(pageName=='matches')
    //     getPairing();
    
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

  function getPairing(idh){
    
    console.log(idh)
    
    var old1=document.getElementById('matchesListNoBye'+String(idh))
    var old2=document.getElementById('matchesListBye'+String(idh))
    $.get('pairing',{
        ID:localStorage.getItem("tournamentID"),
        round:idh
      },function(data){
          data=JSON.parse(data)
          console.log(localStorage.getItem("tournamentID"))
          const new1=document.createElement('div')
          new1.setAttribute("id","matchesListNoBye"+String(idh))
          $("#matchList"+String(idh)).append(new1)
          const new2=document.createElement('div')
          new2.setAttribute("id","matchesListBye"+String(idh))
          $("#matchList"+String(idh)).append(new2)
          document.getElementById("matchList"+String(idh)).replaceChild(new1,old1)
          document.getElementById("matchList"+String(idh)).replaceChild(new2,old2)

          for(let index=0;index<data.length;index++){
              if(data[index].result=="NO_RESULT"){
                $("#matchesListNoBye"+String(idh)).append($.parseHTML(createNoByeCard(data[index],index+1,idh)))
                //document.getElementById("matchesListNoBye").append($.parseHTML(createNoByeCard(data[index],index+1)))
                disableButtons(index+1,data[index].player1.playerID,data[index].player2.playerID,idh)
              }                   
              else{
                $("#matchesListBye"+String(idh)).append($.parseHTML(createByeCard(data[index],index+1)))
                updateScoreWithBYEPostMethod(data[index].player1.playerID,idh)
              }
                  
          }
          
          
    });
  }

  function createNoByeCard(data,index,idh){
    const template = `
      <label style="width: 10%;" id="number${index}-${idh}" class="sublabel">${index}.</label>
      <label style="width: 25%;" id="firstplayer${index}-${idh}" class="sublabel">${data.player1.name}</label>
      <div style="width: 40%;" id="buttonBox${index}-${idh}" class="trieBtn">
          <button style="float: left;" id="firstBtn${index}-${idh}" class="button">W:L</button>
          <button style="float: left;" id="middleBtn${index}-${idh}" class="button">D:D</button>
          <button style="float: left;" id="lastBtn${index}-${idh}" class="button">L:W</button>
      </div>
      <label style="width: 25%;" id="secondplayer${index}-${idh}" class="sublabel">${data.player2.name}</label>     
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

  function disableButtons(index,player1ID,player2ID,roundNumber){
    var header = document.getElementById("buttonBox"+index+"-"+roundNumber);
    var btns = header.getElementsByClassName("button");
    for (let i = 0; i < btns.length; i++) {
      btns[i].addEventListener("click", function() {
        
        updateScore(i+1,player1ID,player2ID,roundNumber);
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

  function updateScore(buttonIndex,player1ID,player2ID,roundNumber){
    var win=localStorage.getItem("win")
    var loss=localStorage.getItem("loss")
    var bye=localStorage.getItem("bye")
    var draw=localStorage.getItem("draw")
    
    var point1=0;
    var point2=0;
    if(buttonIndex===1){
      point1+=parseInt(win, 10)
      point2+=parseInt(loss, 10)
    }
    else if(buttonIndex===2){
      point1+=parseInt(bye, 10)
      point2+=parseInt(bye, 10)
    }
    else{
      point1+=parseInt(loss, 10)
      point2+=parseInt(win, 10)
    }
    
    $.post('updateScore',{
      case:"NOBYE",
      tournamentID:localStorage.getItem("tournamentID"),
      roundNumber:roundNumber,
      playerID1:player1ID,
      playerID2:player2ID,
      points1:point1,
      points2:point2
    },function(data){
          alert(data)
    });

  }

  function updateScoreWithBYEPostMethod(playerID,roundNumber){
    
    var bye=localStorage.getItem("bye")
    var point=0;
    point+=parseInt(bye, 10)
    $.post('updateScore',{
      case:"BYE",
      tournamentID:localStorage.getItem("tournamentID"),
      roundNumber:roundNumber,
      playerID:playerID,
      points:point
    },function(data){
          console.log(data)
    });
    
  }

  function disableMenuButtons(evt, IDNAME) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent1");
    for (i = 0; i < tabcontent.length; i++) {
      tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink1");
    for (i = 0; i < tablinks.length; i++) {
      tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(IDNAME).style.display = "block";
    evt.currentTarget.className += " active";
    var header = document.getElementById("buttonMenu");
    var current = header.getElementsByClassName("active");
    if(current.length==1){
      
      this.className += " active";
    }
    else{
    current[0].className = current[0].className.replace(" active", "");
    this.className += " active";
    }
    getPairing(IDNAME);
  }

  localStorage.setItem("noOfRounds",1)
  function add(){
      //getRounds()
      console.log(localStorage.getItem("noOfRounds"))
      var x=localStorage.getItem("noOfRounds")
      //console.log(x)
      const btn=`<button class="tablink1" onclick="disableMenuButtons(event,'${x}')">Round${x}</button>`
      //const div=`<div id="${x}" class="tabcontent1" style="display:none;">Content${x}</div>`  
    
      $("#buttonMenu").append($.parseHTML(btn))
      $("#divMenu").append($.parseHTML(createContent(x)))
      //$("#divMenu").append($.parseHTML(div))    
      //setRounds(parseInt(x, 10)+1)
      localStorage.setItem("noOfRounds",parseInt(x, 10)+1)
  }

  function createContent(x){
    const template=`
    <div id="${x}" class="tabcontent1" style="display:none;">
        <div>
            <label style="width: 10%;" class="mainlabel">Match</label>
            <label style="width: 25%;" class="mainlabel">Player-1</label>
            <label style="width: 40%;" class="mainlabel">Result</label>
            <label style="width: 25%;" class="mainlabel">Player-2</label>
        </div>
        <div id="matchList${x}">
            <div id="matchesListNoBye${x}"></div>
            <div id="matchesListBye${x}"></div>
        </div>
    </div>
    `
    return template;
  }

function getRounds(){
  console.log("get")
  $.get('getRounds',{ 
    tournamentID:localStorage.getItem("tournamentID")   
  },function(data){
        localStorage.setItem("noOfRounds",parseInt(data, 10));              
  }); 
}

function setRounds(rounds){
  console.log("set")
  $.post('getRounds',{ 
    tournamentID:localStorage.getItem("tournamentID"),  
    roundsCompleted:rounds
  },function(data){
                    
  }); 
}
