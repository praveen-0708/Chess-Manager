$(document).ready(function(){
  $(document).on("click","#loginBtn", function(){

   var email = $('#emailId').val();
   var password = $('#passwordId').val();
        $.get('login', {
                email : email,
                password : password
        }, function(responseText) {
                alert(responseText);
        });
   });


   $(document).on("click","#signUpBtn", function(){
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
                        alert("Successfully Registered.")
                   }else{
                    alert("Successfully Registered.")
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