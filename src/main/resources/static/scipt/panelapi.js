let subsite = null;

function general(){
    if(!subsite || subsite === "catalog") displayCatalog("catalog");
    else displayReaders();
    document.getElementById("readers").addEventListener('click', displayReaders);
    document.getElementById("catalog").addEventListener('click', displayCatalog);
    document.getElementById("out").addEventListener('click', logout);
}

function add(){
    const targetContainer = document.getElementById('items-container');
    clearContainer(targetContainer);
    const addNew = document.getElementById("add-new");
    addNew.removeEventListener('click', add);
    const upperNew = document.getElementById("new-upper");
    addNew.style.minHeight = "17vh";
    upperNew.style.height = "30%";

    const formsHolder = document.createElement('div');
    formsHolder.classList.add("addForms");
    const addForms = document.createElement('form');
    const author = document.createElement('input');
    author.type = "text";
    author.required;
    author.placeholder = "Enter author";
    author.classList.add("addInput");
    const title = document.createElement('input');
    title.type = "text";
    title.required;
    title.placeholder = "Enter title";
    title.classList.add("addInput");
    const publisher = document.createElement('input');
    publisher.type = "text";
    publisher.required;
    publisher.placeholder = "Enter publisher name";
    publisher.classList.add("addInput");
    const button = document.createElement('input');
    button.type = "submit";
    button.value = "Add";
    button.classList.add("addInput")
    button.classList.add("btn");
    addForms.appendChild(author);
    addForms.appendChild(title);
    addForms.appendChild(publisher);
    addForms.appendChild(button);
    formsHolder.appendChild(addForms);
    addNew.appendChild(formsHolder);
    if(subsite === "readers") addBook();
    else addUser();

}
function displayReaders(){
    subsite = "readers";
    document.getElementById("add-new").addEventListener('click', add);
}
function displayCatalog(catalog){
    subsite = "catalog";
    document.getElementById("add-new").addEventListener('click', add);
}
function logout(){}

function addBook(){

}
function addUser(){

}
function clearContainer(targetContainer)
{
    while(targetContainer.firstChild){
        targetContainer.firstChild.remove();
    }
}