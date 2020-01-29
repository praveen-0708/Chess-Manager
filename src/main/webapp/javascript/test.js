function openPage(pageName) {
    var i, tabcontent;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
      tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    document.getElementById(pageName).style.display = "block";

    if(pageName=='details'){     
      getDetails();
    }
        
    else if(pageName=='players'){     
      getPlayers();
    }
       
    else if(pageName=='matches')
    {
        previousData();
    }
    else{
        getResult();
    }
         
  }

  
  
  function getDetails(){
      $.get('SelectedTournament',{
        // ID:localStorage.getItem("tournamentID")
      },function(data){
          data=JSON.parse(data)
          document.getElementById("name").innerHTML="Tournament Name:"+data[0].name
          document.getElementById("date").innerHTML="Date:"+data[0].startDate
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
        // ID:localStorage.getItem("tournamentID")
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
    
    $.get('pairing',{
        // ID:localStorage.getItem("tournamentID"),
        round:idh
      },function(data){
        data=JSON.parse(data)
        // console.log(localStorage.getItem("tournamentID"))
        const new1=document.createElement('div')
        new1.setAttribute("id","matchesListNoBye"+String(idh))
        $("#matchList"+String(idh)).append(new1)
        const new2=document.createElement('div')
        new2.setAttribute("id","matchesListBye"+String(idh))
        $("#matchList"+String(idh)).append(new2)
        
        


        for(let index=0;index<data.length;index++){
            if(data[index].result=="NO_RESULT"){
            $("#matchesListNoBye"+String(idh)).append($.parseHTML(createNoByeCard(data[index],index+1,idh)))
            disableButtons(index+1,data[index].player1.playerID,data[index].player2.playerID,idh)
            }                   
            else{
            $("#matchesListBye"+String(idh)).append($.parseHTML(createByeCard(data[index],index+1)))
            updateScoreWithBYEPostMethod(data[index].player1.playerID,idh)
            }       
        }         
    });
  }

  function getPaired(idh){
    var old1=document.getElementById('matchesListNoBye'+String(idh))
    var old2=document.getElementById('matchesListBye'+String(idh))
    $.get('paired',{
        // ID:localStorage.getItem("tournamentID"),
        round:idh
      },function(data){
          data=JSON.parse(data)
          // console.log(localStorage.getItem("tournamentID"))
          const new1=document.createElement('div')
          new1.setAttribute("id","matchesListNoBye"+String(idh))
          $("#matchList"+String(idh)).append(new1)
          const new2=document.createElement('div')
          new2.setAttribute("id","matchesListBye"+String(idh))
          $("#matchList"+String(idh)).append(new2)
          document.getElementById("matchList"+String(idh)).replaceChild(new1,old1)
          document.getElementById("matchList"+String(idh)).replaceChild(new2,old2)

          for(let index=0;index<data.length;index++){
              if(data[index].result!="BYE"){
                $("#matchesListNoBye"+String(idh)).append($.parseHTML(createNoByeCard2(data[index],index+1,idh)))
                }                   
              else{
                $("#matchesListBye"+String(idh)).append($.parseHTML(createByeCard2(data[index],index+1)))
                 
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

  function createNoByeCard2(data,index,idh){
    const template = `
      <label style="width: 10%;" id="number${index}-${idh}" class="sublabel">${index}.</label>
      <label style="width: 25%;" id="firstplayer${index}-${idh}" class="sublabel">${data.player1.name}</label>
      
      <label style="width: 40%;" id="result${index}-${idh}" class="sublabel">${data.result}</label>
      
      <label style="width: 25%;" id="secondplayer${index}-${idh}" class="sublabel">${data.player2.name}</label>     
        `;
     return template;
  }

  function createByeCard2(data,index){
    const template = `
      <label style="width: 10%;" class="sublabel">${index}.</label>
      <label style="width: 25%;" class="sublabel">${data.player1.name}</label>
      <label style="width: 40%;" class="sublabel">BYE</label>
      <label style="width: 25%;" class="sublabel">-</label>    
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
    // var win=localStorage.getItem("win")
    // var loss=localStorage.getItem("loss")
    // var bye=localStorage.getItem("bye")
    // var draw=localStorage.getItem("draw")
    
    // var point1=0;
    // var point2=0;
    var result='';
    if(buttonIndex===1){
      // point1+=parseInt(win, 10)
      // point2+=parseInt(loss, 10)
      result='PLAYER_1_WON'
    }
    else if(buttonIndex===2){
      // point1+=parseInt(draw, 10)
      // point2+=parseInt(draw, 10)
      result='DRAW'
    }
    else{
      // point1+=parseInt(loss, 10)
      // point2+=parseInt(win, 10)
      result='PLAYER_2_WON'
    }
    
    $.post('updateScore',{
      case:"NOBYE",
      // tournamentID:localStorage.getItem("tournamentID"),
      roundNumber:roundNumber,
      playerID1:player1ID,
      playerID2:player2ID,
      // points1:point1,
      // points2:point2,
      result:result
    },function(data){
          alert(data)
    });

  }

  function updateScoreWithBYEPostMethod(playerID,roundNumber){
    
    // var bye=localStorage.getItem("bye")
    // var point=0;
    // point+=parseInt(bye, 10)
    $.post('updateScore',{
      case:"BYE",
      // tournamentID:localStorage.getItem("tournamentID"),
      roundNumber:roundNumber,
      playerID:playerID,
      // points:point,
      result:"BYE"
    },function(data){
          console.log(data)
    });
    
  }

  function disableMenuButtons(evt, IDNAME,state) {
    
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
    
    if(state=='old')
        getPaired(IDNAME);
    else
        getPairing(IDNAME); 
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
  function createContent2(x){
    const template=`
    <div id="${x}" class="tabcontent1" style="display: block;">
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


function setRounds(rounds){
  // console.log("set")
  $.post('getRounds',{ 
    // tournamentID:localStorage.getItem("tournamentID"),  
    roundsCompleted:rounds
  },function(data){
                    
  }); 
}

function previousData(){
    var old=document.getElementById("buttonMenu");
    
      $.get('getRounds',{ 
        // tournamentID:localStorage.getItem("tournamentID")   
        },function(data){
            data=JSON.parse(data)
            var x=(parseInt(data.roundsCompleted, 10));

            const oldDiv=document.getElementById("divMenu");
            oldDiv.innerHTML='';

            const div=document.createElement('div')
            div.setAttribute("id","buttonMenu")
            document.getElementById("matches").appendChild(div)
            for(let i=1;i<=x;i++){
                const btn1=document.createElement("button")
                btn1.setAttribute("id","Btn"+i)
                btn1.setAttribute("class","tablink1")
                btn1.setAttribute("onclick","disableMenuButtons(event,'"+i+"','old')")
                btn1.textContent="Round"+i
                div.append(btn1)
                $("#divMenu").append($.parseHTML(createContent(i))) 
            } 
            document.getElementById("matches").appendChild(div)
            document.getElementById("matches").replaceChild(div,old)
        });
}

function addNewRound(){
    //var totalRounds=parseInt(localStorage.getItem("totalRounds"), 10);
    $.get('getRounds',{ 
        // tournamentID:localStorage.getItem("tournamentID")   
        },function(data){
            data=JSON.parse(data)
            
            var totalRounds=(parseInt(data.totalRounds, 10));
            var x=(parseInt(data.roundsCompleted, 10));
            if(x>=totalRounds){
                alert("All rounds completed");
                document.getElementById("next").style.visibility="hidden";
                addScoreOfFinalRounds(x);
                previousData();

            }else{  
                var btns = document.getElementsByClassName("tablink1 active");
                if(btns.length>0){
                    for (let i = 0; i < btns.length; i++){
                        btns[i].className = btns[i].className.replace(" active", "");
                    }
                }
                //console.log("round:"+x)
                if(x>0){
                    console.log("round:"+x)
                    addScoreOfARound(x)                   
                }
                    
                const btn1=document.createElement("button");
                btn1.setAttribute("id","Btn"+(x+1));
                btn1.setAttribute("class","tablink1 active");
                btn1.setAttribute("onclick","disableMenuButtons(event,'"+(x+1)+"','old')");
                btn1.textContent="Round"+(x+1);
                document.getElementById("buttonMenu").appendChild(btn1);

                var mainDiv = document.getElementsByClassName("tabcontent1");
                if(mainDiv.length>0){
                    for (let i = 0; i < mainDiv.length; i++){
                        mainDiv[i].style.display="none";
                    }
                }

                $("#divMenu").append($.parseHTML(createContent2(x+1)));
                getPairing((x+1).toString());
            }
    });
}

function addScoreOfARound(roundNumber){
    $.post('calculateTotalPoints',{ 
        // tournamentID:localStorage.getItem("tournamentID"),
        roundNumber:roundNumber
        },function(data){

    });

}
function addScoreOfFinalRounds(roundNumber){
    $.post('calculateTotalPoints',{ 
        // tournamentID:localStorage.getItem("tournamentID"),
        roundNumber:roundNumber
        },function(data){

    });
    addScoreOfFinalRounds=noop;

}
function noop(){};

function getResult(){
    var oldresult=document.getElementById("mainresult");
    const newresult=document.createElement('div');
    newresult.setAttribute("id","mainresult");
    document.getElementById("rank").replaceChild(newresult,oldresult);
    $("#mainresult").append($.parseHTML(createResultCardMenu()));
    $.get('result',{ 
        // tournamentID:localStorage.getItem("tournamentID")        
        },function(data){
            data=JSON.parse(data);
            for(let index=0;index<data.length;index++){
                $("#result").append($.parseHTML(createResultCard(data[index],index+1))) 
            } 

    });
}

function createResultCardMenu(){
    const template=`
    <table id="result">
        <tr>
            <th>Name</th>
            <th>Points</th>
            <th>Rank</th>
        </tr>
    </table>
    `;
    return template;
}
function createResultCard(data,index){
    const template=`
    <tr>
        <td>${data.name}</td>
        <td>${data.points}</td>
        <td>${index}</td>
    </tr>

    `;
    return template;
}