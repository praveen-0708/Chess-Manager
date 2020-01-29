function loadLoginPage(){
    const div1=document.createElement('div')
    div1.setAttribute('id','col')

    const div2=document.createElement('div')
    div2.setAttribute('class','login-form')

    const h1 = document.createElement('h1')
    h1.textContent = "Login";


    const p = document.createElement('p')
    p.textContent = "Please login";

    const hr=document.createElement('hr')

    const label1=document.createElement('label')
    label1.textContent="Email"

    

    const email=document.createElement("input")
    email.setAttribute("type","text")
    email.setAttribute("placeholder","Email")
    email.setAttribute("name","email")
    email.setAttribute("id","emailId")
    email.required=true

    const label2=document.createElement('label')
    label2.textContent="Password"

    const password=document.createElement("input")
    password.setAttribute("type","password")
    password.setAttribute("placeholder","Password")
    password.setAttribute("name","password")
    password.setAttribute("id","passwordId")
    password.required=true

    const login=document.createElement("button")
    login.setAttribute("type","button")
    login.setAttribute("class","loginid")
    login.setAttribute("id","loginBtn")
    login.textContent="Login"

    const p2 = document.createElement('p')
    p2.setAttribute("class","message")
    p2.textContent = "Not registered?";


    const create=document.createElement("button")
    create.setAttribute("type","button")
    create.setAttribute("id","createAccount")
    create.textContent="Create an account"
    create.setAttribute("onclick","loginToSignup()")

    p2.appendChild(create)

    div2.appendChild(h1)
    div2.appendChild(p)
    div2.appendChild(hr)
    
    div2.appendChild(label1)
    div2.appendChild(email)
    div2.appendChild(label2)
    div2.appendChild(password)
    div2.appendChild(login)
    div2.appendChild(p2)

    div1.appendChild(div2)

    document.getElementById("dynamicLoader").appendChild(div1)
}

function loginToSignup(){
    document.getElementById("dynamicLoader").removeChild(document.getElementById("col"))
    loadSignUpPage()
}

function signUpToLogin(){
    document.getElementById("dynamicLoader").removeChild(document.getElementById("parent"))
    loadLoginPage()
}

function loadSignUpPage(){

    const div1=document.createElement('div')
    div1.setAttribute('id','parent')

    const div2=document.createElement('div')
    div2.setAttribute('class','container')

    const h1 = document.createElement('h1')
    h1.textContent = "Sign Up";

    const p = document.createElement('p')
    p.textContent = "Please fill in this form to create an account.";

    const hr=document.createElement('hr')

    const label1=document.createElement('label')

    label1.textContent="Name"

    const name=document.createElement("input")
    name.setAttribute("type","text")
    name.setAttribute("placeholder","Enter Name")
    name.setAttribute("name","name")
    name.setAttribute("id","name")
    name.required=true

    const label2=document.createElement('label')
    label2.textContent="Email"

    const email=document.createElement("input")
    email.setAttribute("type","text")
    email.setAttribute("placeholder","Enter Email")
    email.setAttribute("name","email")
    email.setAttribute("id","email")
    email.required=true

    const label3=document.createElement('label')
    label3.textContent="Password"

    const password=document.createElement("input")
    password.setAttribute("type","password")
    password.setAttribute("placeholder","Enter Password")
    password.setAttribute("name","psw")
    password.setAttribute("id","password")
    password.required=true

    const div3=document.createElement('div')
    div3.setAttribute("class","clearfix")

    const cancel=document.createElement("button")
    cancel.setAttribute("type","button")
    cancel.setAttribute("class","loginbtn")
    cancel.setAttribute("id","cancelBtn")
    cancel.textContent="LOGIN"
    cancel.setAttribute("onclick","signUpToLogin()")

    const signUpBtn=document.createElement("button")
    signUpBtn.setAttribute("type","button")
    signUpBtn.setAttribute("class","signupbtn")
    signUpBtn.setAttribute("id","signUpBtn")
    signUpBtn.textContent="SIGN UP"

    div3.appendChild(cancel)
    div3.appendChild(signUpBtn)

    div2.appendChild(h1)
    div2.appendChild(p)
    div2.appendChild(hr)
    div2.appendChild(label1)
    div2.appendChild(name)
    div2.appendChild(label2)
    div2.appendChild(email)
    div2.appendChild(label3)
    div2.appendChild(password)
    div2.appendChild(div3)

    div1.appendChild(div2)

    document.getElementById("dynamicLoader").appendChild(div1)



}