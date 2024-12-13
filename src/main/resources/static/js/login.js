

  document.getElementById("show-login").addEventListener("click",function(){
    var x = document.getElementById("password-login");
    if (x.type === "password") {
      x.type = "text";
    } else {
      x.type = "password";
    }

  })

  
  document.getElementById("show-register").addEventListener("click",function(){
    var x = document.getElementById("password-register");
    if (x.type === "password") {
      x.type = "text";
    } else {
      x.type = "password";
    }

  })