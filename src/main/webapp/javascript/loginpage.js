$(document).ready(function(){
  $(document).on("click","#loginBtn", function(){

   var email = $('#emailId').val();
   var password = $('#passwordId').val();
    if(validateLogin(email,password)){
            $.post('login', {
                    email : email,
                    password : password
            }, function(ID) {

                    if(ID!="failure"){
                        // document.cookie=ID+" "+email+" "+password
                        window.location.replace("home.html");
                    }
                    else
                        alert(ID);
            });
        }
   });


   $(document).on("click","#signUpBtn", function(){
      var name = $('#name').val();
      var email = $('#email').val();
      var password = $('#password').val();
      var emp=$('#empId').val();
      
      if(validate(name,email,password)){
          $.post('register', {
                   eid : emp, 
                   name : name,
                   email : email,
                   password : password
           }, function(data,status) {
                
                   if(data=="registered"){
                        alert("Successfully Registered.")
                   }else if(data=="exists"){
                    alert("Email already taken");
                   }
                   else{
                       alert("Failed");
                   }

           });
       }

   });

   function validate(name,email,password){

    if(name=="" || email=="" || password==""){
        alert("Please fill all the required fields.");
        return false;
    }

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

   function validateLogin(email,password){

    // if(email=="" || password==""){
    //     alert("Please fill all the required fields.");
    //     return false;
    // }

    // var regEx = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    //   var validEmail = regEx.test(email);
    //   if (!validEmail) {
    //         alert("Enter a valid email");
    //         return false;
    //   }

    //  if (password.length < 8) {
    //        alert("Password must be at least 8 characters");
    //        return false;
    //  }

     return true;
}

});