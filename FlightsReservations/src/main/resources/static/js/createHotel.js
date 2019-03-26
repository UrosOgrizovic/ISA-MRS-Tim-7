
function create_hotel(myForm)
{
    console.log(myForm);

    if(myForm.name.value=="")
    {
        alert("You must enter a Hotel name!");
        return false;
    }
    else//in case of wrong type (add later)
    {

    }

    if(myForm.address.value=="")
    {
        alert("You must enter a Hotel address!");
        return false;
    }

    if(myForm.promoDescription.value=="")
    {
        alert("You must enter a Hotel descritpion!");
        return false;
    }

    /*if(form.logotPath.value=="") //fix later
    {
       
        return false;
    }*/

    return true;
}