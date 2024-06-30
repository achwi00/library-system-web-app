let subsite = null;

function general(){
    if(document.cookie)
    console.log("subsite: ", subsite);
    if(!subsite || subsite === "catalog") {
        subsite = "catalog"
        displayCatalog("catalog");
    }
    else {
        subsite = "readers";
        displayReaders();
    }
    document.getElementById("readers").addEventListener('click', displayReaders);
    document.getElementById("catalog").addEventListener('click', displayCatalog);
    document.getElementById("out").addEventListener('click', logout);
}

function add(){
    //added
    const container = document.getElementById("bookFormHolder");
    if(container != null) container.remove();

    const targetContainer = document.getElementById('items-container');
    const addNew = document.getElementById("add-new");
    addNew.removeEventListener('click', add);
    const upperNew = document.getElementById("new-upper");
    addNew.style.minHeight = "17vh";
    upperNew.style.height = "30%";
    if(subsite === "catalog") addBook();
    else addUser();
}

function displayReaders(){
    subsite = "readers";
    document.getElementById("add-new").addEventListener('click', add);
}
function displayCatalog(){
    subsite = "catalog";
    document.getElementById("add-new").addEventListener('click', add);
}
function logout(){}

function addBook(){
    const addNew = document.getElementById("add-new");
    const formsHolder = document.createElement('div');
    formsHolder.classList.add("addForms");
    formsHolder.id = "bookFormHolder";
    const addForms = document.createElement('form');
    const firstRow = document.createElement('div');
    const secondRow = document.createElement('div');
    firstRow.classList.add("row");
    secondRow.classList.add("row");
    addForms.id = "addBookForm";
    addForms.action = "/panel/add-book";
    addForms.method = "post";
    const author = document.createElement('input');
    author.type = "text";
    author.required = true;
    author.placeholder = "Enter author";
    author.classList.add("addInput");
    author.name = "author";
    const title = document.createElement('input');
    title.type = "text";
    title.required = true;
    title.placeholder = "Enter title";
    title.classList.add("addInput");
    title.name = "title";
    const publisher = document.createElement('input');
    publisher.type = "text";
    publisher.required = true;
    publisher.placeholder = "Enter publisher name";
    publisher.classList.add("addInput");
    publisher.name = "publisher";
    const button = document.createElement('input');
    button.type = "submit";
    button.value = "Add";
    button.classList.add("addInput")
    button.classList.add("btn");
    firstRow.appendChild(author);
    firstRow.appendChild(title);
    addForms.appendChild(firstRow);
    secondRow.appendChild(publisher);
    secondRow.appendChild(button);
    addForms.appendChild(secondRow);
    formsHolder.appendChild(addForms);
    addNew.appendChild(formsHolder);
    addForms.addEventListener('submit', function (e){
        e.preventDefault();
        const formData = new FormData(this);
        fetch('/panel/add-book', {
            method: 'POST',
            body: formData
        })
            .then(response => response.text())
            .then(data => {
                clearContainer(formsHolder)
                formsHolder.innerText = data;
            })
            .catch(error => console.error('Error:', error));
    });
}
function addUser(){
    const addNew = document.getElementById("add-new");
    const formsHolder = document.createElement('div');
    formsHolder.classList.add("addForms");
    formsHolder.id = "bookFormHolder";
    const addForms = document.createElement('form');
    const firstRow = document.createElement('div');
    const secondRow = document.createElement('div');
    firstRow.classList.add("row");
    secondRow.classList.add("row");
    addForms.id = "addUserForm";
    addForms.action = "/panel/add-user";
    addForms.method = "post";
    const nameOfUser = document.createElement('input');
    nameOfUser.type = "text";
    nameOfUser.required = true;
    nameOfUser.placeholder = "Enter name";
    nameOfUser.classList.add("addInput");
    nameOfUser.name = "name";
    const surname = document.createElement('input');
    surname.type = "text";
    surname.required = true;
    surname.placeholder = "Enter surname";
    surname.classList.add("addInput");
    surname.name = "title";
    const cardNum = document.createElement('input');
    cardNum.type = "text";
    cardNum.required = true;
    cardNum.placeholder = "Enter user's library card";
    cardNum.classList.add("addInput");
    cardNum.name = "publisher";
    const button = document.createElement('input');
    button.type = "submit";
    button.value = "Add";
    button.classList.add("addInput")
    button.classList.add("btn");
    firstRow.appendChild(nameOfUser);
    firstRow.appendChild(surname);
    addForms.appendChild(firstRow);
    secondRow.appendChild(cardNum);
    secondRow.appendChild(button);
    addForms.appendChild(secondRow);
    formsHolder.appendChild(addForms);
    addNew.appendChild(formsHolder);
    addForms.addEventListener('submit', function (e){
        e.preventDefault();
        const formData = new FormData(this);
        fetch('/panel/add-user', {
            method: 'POST',
            body: formData
        })
            .then(response => response.text())
            .then(data => {
                clearContainer(formsHolder)
                formsHolder.innerText = data;
            })
            .catch(error => console.error('Error:', error));
    });

}
function clearContainer(targetContainer)
{
    while(targetContainer.firstChild){
        targetContainer.firstChild.remove();
    }
}