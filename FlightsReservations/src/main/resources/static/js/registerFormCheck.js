function checkForm(form)
{
    if(form.password.value != form.confirmPassword.value) {
      alert("Error: Passwords don't match");
      form.password.focus();
      return false;
    }
    alert("Registration successful");
    return true;
}