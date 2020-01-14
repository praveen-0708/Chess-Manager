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
   console.log(email);
   console.log(password);
        if (password.length < 8) {
              $('#password').after('<span class="error">Password must be at least 8 characters long</span>');
              return false;
        }
        var regEx = /^[A-Z0-9][A-Z0-9._%+-]{0,63}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/;
          var validEmail = regEx.test(email);
          if (!validEmail) {
                $('#email').after('<span class="error">Enter a valid email</span>');
                return false;
          }
        return true;
   }




});