function openPage(pageName,state) {
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
        add(state);
        
     }
         
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

  function getPaired(idh){
    var old1=document.getElementById('matchesListNoBye'+String(idh))
    var old2=document.getElementById('matchesListBye'+String(idh))
    $.get('paired',{
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
              if(data[index].result!="BYE"){
                $("#matchesListNoBye"+String(idh)).append($.parseHTML(createNoByeCard2(data[index],index+1,idh)))
                //document.getElementById("matchesListNoBye").append($.parseHTML(createNoByeCard(data[index],index+1)))
                disableButtons(index+1,data[index].player1.playerID,data[index].player2.playerID,idh)
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
      <div style="width: 40%;" id="buttonBox${index}-${idh}" class="trieBtn">
          <label id="result${index}-${idh}" class="sublabel">${data.result}</label>
      </div>
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
    var win=localStorage.getItem("win")
    var loss=localStorage.getItem("loss")
    var bye=localStorage.getItem("bye")
    var draw=localStorage.getItem("draw")
    
    var point1=0;
    var point2=0;
    var result='';
    if(buttonIndex===1){
      point1+=parseInt(win, 10)
      point2+=parseInt(loss, 10)
      result='PLAYER_1_WON'
    }
    else if(buttonIndex===2){
      point1+=parseInt(draw, 10)
      point2+=parseInt(draw, 10)
      result='DRAW'
    }
    else{
      point1+=parseInt(loss, 10)
      point2+=parseInt(win, 10)
      result='PLAYER_2_WON'
    }
    
    $.post('updateScore',{
      case:"NOBYE",
      tournamentID:localStorage.getItem("tournamentID"),
      roundNumber:roundNumber,
      playerID1:player1ID,
      playerID2:player2ID,
      points1:point1,
      points2:point2,
      result:result
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
      points:point,
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
  //var load1='firsttime';
  //localStorage.setItem("noOfRounds",1)
  function add(load1){
      var totalRounds=parseInt(localStorage.getItem("totalRounds"), 10)      
      $.get('getRounds',{ 
        tournamentID:localStorage.getItem("tournamentID")   
      },function(data){
          console.log(data)
          var x=(parseInt(data, 10))+1; 
          if(load1=='firsttime'){
            for(let i=1;i<x;i++){
              const btn1=`<button id="Btn${i}" class="tablink1" onclick="disableMenuButtons(event,'${i}','old')">Round${i}</button>`
              $("#buttonMenu").append($.parseHTML(btn1))
              $("#divMenu").append($.parseHTML(createContent(i)))
            }
          }   

            if(load1!='firsttime'){
              if(x>totalRounds){
                alert("All rounds completed")
              }else{
                // var all=document.getElementsByClassName("tablink1")
                // var all2=document.getElementsByClassName("tablink1 active")
                // console.log(all.length+all2.length)
                // if(all.length+all2.length>1){
                //   $("#Btn"+all.length+all2.length-1).remove();

                  
                //   var temp=all.length+all2.length
                //   var old=document.getElementById("buttonMenu").childNodes[(temp-1).toString()];  
                //   console.log(old)
                //   const btn1=document.createElement("button")
                //   btn1.setAttribute("id","Btn"+(temp-1))
                //   btn1.setAttribute("class","tablink1")
                //   btn1.setAttribute("onclick","disableMenuButtons(event,'"+(temp-1)+"','old')")               
                //   btn1.textContent="Round"+(temp-1)
                //   document.getElementById("buttonMenu").replaceChild(btn1,old)
                //  }
                

                const btn2=`<button id="Btn${x}" class="tablink1" onclick="disableMenuButtons(event,'${x}','new')">Round${x}</button>`
                $("#buttonMenu").append($.parseHTML(btn2))
                $("#divMenu").append($.parseHTML(createContent(x)))
              }              
            }
          // }
          

          
      }); 

      // load1='';
      // console.log(load1)
      //console.log(localStorage.getItem("noOfRounds"))
      //var x=localStorage.getItem("noOfRounds")
      //console.log(x)
      //const btn=`<button class="tablink1" onclick="disableMenuButtons(event,'${x}')">Round${x}</button>`
      //const div=`<div id="${x}" class="tabcontent1" style="display:none;">Content${x}</div>`  
    
      //$("#buttonMenu").append($.parseHTML(btn))
      //$("#divMenu").append($.parseHTML(createContent(x)))
      //$("#divMenu").append($.parseHTML(div))    
      //setRounds(parseInt(x, 10)+1)
      //localStorage.setItem("noOfRounds",parseInt(x, 10)+1)
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


function setRounds(rounds){
  console.log("set")
  $.post('getRounds',{ 
    tournamentID:localStorage.getItem("tournamentID"),  
    roundsCompleted:rounds
  },function(data){
                    
  }); 
}
