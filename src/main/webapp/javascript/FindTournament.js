$.get('createTournament',{

  },function(data,status){

    data=JSON.parse(data)
    for(let index=0;index<data.length;index++)
        createCard(index,data[index].tournamentId,data[index].name,data[index].startDate,data[index].locationInput,data[index].rounds,data[index].duration)
    

  });

  function joinTournament(index,tournamentId){
    //var ID=document.cookie.split(" ")[0]
    var clickedTournamentID=tournamentId
    $.post('joinTournament', {
      //ID : ID,
      clickedTournamentID : clickedTournamentID
      }, function(data,status) {
          if(data=="already joined")
            alert("Already Joined");
          else if(data==='0')
            alert("Failed");
          else{
            alert("Success");
            window.location.replace("home.html")
          }
            

    });

  }
  




function createCard(index,tournamentId,nameP,dateP,locationP,roundsP,durationP){
    const div1=document.createElement("div")
    div1.style.width="75%"
    div1.style.float="left"

    const name=document.createElement("label")
    name.setAttribute("class","label")
    name.setAttribute("id","name"+(index+1))
    name.textContent="Torunament Name:"+nameP
    
    const date=document.createElement("label")
    date.setAttribute("class","label")
    date.setAttribute("id","date"+(index+1))
    date.textContent="Date:"+dateP
    
    const location=document.createElement("label")
    location.setAttribute("class","label")
    location.setAttribute("id","location"+(index+1))
    location.textContent="Location:"+locationP
    
    const rounds=document.createElement("label")
    rounds.setAttribute("class","label")
    rounds.setAttribute("id","rounds"+(index+1))
    rounds.textContent="No.of Rounds:"+roundsP
    
    const duration=document.createElement("label")
    duration.setAttribute("class","label")
    duration.setAttribute("id","duration"+(index+1))
    duration.textContent="Duration:"+durationP


    div1.appendChild(name)
    div1.appendChild(date)
    div1.appendChild(location)
    div1.appendChild(rounds)
    div1.appendChild(duration)

    const joinBtn=document.createElement("button")
    joinBtn.setAttribute("type","button")
    joinBtn.setAttribute("id","join"+(index+1))
    joinBtn.setAttribute("onclick","joinTournament("+(index+1)+","+tournamentId+")")
    joinBtn.textContent="JOIN"

    const div2=document.createElement("div")
    div2.style.width="25%"
    div2.style.float="right"

    
    div2.appendChild(joinBtn)

    const card=document.createElement("div")
    card.setAttribute("class","card")
    card.setAttribute("id","card"+(index+1))
    card.appendChild(div1)
    card.appendChild(div2)


    document.getElementById("allTournaments").appendChild(card)
    
    
}
