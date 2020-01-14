$(document).ready(function(){
  $("#loginBtn").on("click", function(){

   var email = $('#emailId').val();
   var password = $('#passwordId').val();
        $.get('login', {
                email : email,
                password : password
        }, function(responseText) {
                alert(responseText);
        });
   });

   $('#createAccount').on("click",function(){
        $(".col").load("signup.html");
   });

  $('#cancelBtn').on("click",function(){
       $(".parent1").load("index.html");
  });


   $("#signUpBtn").on("click", function(){
      var name = $('#name').val();
      var email = $('#email').val();
      var password = $('#password').val();
      if(validate(email,password)){
          $.post('register', {
                   name : name,
                   email : email,
                   password : password
           }, function(data,status) {
                   if(status=="success"){
                   console.log(status);
                        $(".parent1").load("index.html");
                   }
           });
      }

   });

   function validate(email,password){

       var regEx = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
         var validEmail = regEx.test(email);
         if (!validEmail) {
               alert("Enter a valid email");
               return false;
         }

        if (password.length < 8) {
              alert("Password must be at least 8 characters");
              return false;
        }

        return true;
   }




});