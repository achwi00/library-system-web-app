let subsite = null;
const urlParams1 = new URLSearchParams(window.location.search);
const sessionKey = urlParams1.get('sessionKey');

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

    const addNew = document.getElementById("add-new");
    addNew.removeEventListener('click', add);
    const upperNew = document.getElementById("new-upper");
    addNew.style.minHeight = "17vh";
    upperNew.style.height = "30%";
    if(subsite === "catalog") addBook();
    else addUser();
}

function displayReaders() {
    toDefaultView();
    subsite = "readers";
    document.getElementById("add-new").addEventListener('click', add);
    const itemsContainer = document.getElementById("items-container");
    clearContainer(itemsContainer);

    fetch(`/panel/all-readers`)
        .then(response => response.json())
        .then(data => {
            data.forEach(reader =>{
                const item = document.createElement('div');
                item.classList.add("item");
                item.classList.add("element");
                const upper = document.createElement('div');
                upper.classList.add("upper-item");
                const itemLeft = document.createElement('div');
                const itemRight = document.createElement('div');
                itemLeft.classList.add("item-left");
                itemRight.classList.add("item-left");
                const pName = document.createElement('p');
                const pSurname = document.createElement('p');
                const pCard = document.createElement('p');
                const bookBtn = document.createElement('button');
                bookBtn.classList.add("operation-btn");
                bookBtn.classList.add("grow");
                bookBtn.textContent = "Delete";

                bookBtn.addEventListener('click', function (e){
                    e.preventDefault();
                    const formData = new FormData();
                    formData.append('cardNum',`${reader.cardNumber}`);
                    fetch('/panel/all-readers/delete', {
                        method: 'POST',
                        body: formData
                    })
                        .then(response => response.text())
                        .then(data => {
                            bookBtn.disabled = true;
                            bookBtn.style.backgroundColor = "var(--peach)";
                            bookBtn.style.color = "var(--washedblack)";
                            bookBtn.innerText = data;
                        })
                        .catch(error => console.error('Error:', error));
                });

                const arrowHolder = document.createElement('button');
                arrowHolder.classList.add("more");
                arrowHolder.classList.add("grow")
                const arrow = document.createElement('i');
                arrow.classList.add("bi");
                arrow.classList.add("bi-plus");

                arrow.addEventListener('click', userDetails);

                function userDetails(){
                   clearContainer(itemsContainer);
                   const bookFormHolder = document.getElementById("bookFormHolder");
                   //append the bookFormHolder
                   const addNew = document.getElementById("add-new");
                   addNew.removeEventListener('click', add);
                   const upperNew = document.getElementById("new-upper");
                   clearContainer(upperNew);
                   addNew.style.minHeight = "19vh";
                   upperNew.style.height = "30%";
                   clearContainer(upperNew);
                   const pName = document.createElement('h2');
                   pName.textContent = `${reader.name} ${reader.surname}, Library card: ${reader.cardNumber}`;
                   upperNew.appendChild(pName);
                   addNew.appendChild(upperNew);
                   const info = document.createElement('p');
                   info.innerText = "Current borrowings: ";
                   addNew.appendChild(info);

                    const formData = new FormData();
                    formData.append('cardNumber', `${reader.cardNumber}`);
                    fetch('/panel/all-readers/current-borrow', {
                        method: 'POST',
                        body: formData
                    })
                        .then(response => response.json())
                        .then(data => {
                            data.forEach(b => {
                            const booking = document.createElement('p');
                            booking.style.margin = "0";
                            console.log(`${b.startTime}`);
                            booking.innerText = `${b.borrowing.startTime} ${b.book.author} ${b.book.bookCopyId}`;

                            addNew.appendChild(booking);
                        })
                        })
                        .catch(error => console.error('Error:', error));


                    //fetch the user history, create item for each
                    const cardForm = new FormData();
                    cardForm.append('cardNumber', `${reader.cardNumber}`);
                    fetch('/panel/all-readers/history', {
                        method: 'POST',
                        body: cardForm
                    })
                        .then(response => response.json())
                        .then(data => {
                            data.forEach(entry => {
                                const item = document.createElement('div');
                                item.classList.add("item");
                                item.classList.add("element");
                                const upper = document.createElement('div');
                                upper.classList.add("upper-item");
                                const itemLeft = document.createElement('div');
                                const itemRight = document.createElement('div');
                                itemLeft.classList.add("item-left");
                                itemRight.classList.add("item-left");
                                const time = document.createElement('p');
                                const author = document.createElement('p');
                                const bookCopy = document.createElement('p');
                                time.innerText = `${entry.borrowing.startTime} - ${entry.borrowing.endTime}`;
                                author.innerText = `${entry.book.author}`;
                                bookCopy.innerText = `${entry.book.bookCopyId}`;
                                itemLeft.appendChild(time);
                                itemLeft.appendChild(author);
                                itemRight.appendChild(bookCopy);

                                upper.appendChild(itemLeft);
                                upper.appendChild(itemRight);
                                item.appendChild(upper);
                                itemsContainer.appendChild(item);
                            })
                        })

                    //end
                }

                arrowHolder.appendChild(arrow);
                itemRight.appendChild(bookBtn);
                itemRight.appendChild(arrowHolder);
                pName.textContent = `${reader.name}`;
                pSurname.textContent = `${reader.surname}`;
                pCard.textContent = `${reader.cardNumber}`
                itemLeft.appendChild(pName);
                itemLeft.appendChild(pSurname);
                itemLeft.appendChild(pCard);

                upper.appendChild(itemLeft);
                upper.appendChild(itemRight);
                item.appendChild(upper);
                itemsContainer.appendChild(item);

            })
        })

}
 function displayCatalog(){
    toDefaultView();
    subsite = "catalog";
    document.getElementById("add-new").addEventListener('click', add);
    const itemsContainer = document.getElementById("items-container");

    fetch(`/panel/all-books`)
        .then(response => response.json())
        .then(data => {
            data.forEach(book => {
                const item = document.createElement('div');
                item.classList.add("item");
                item.classList.add("element");
                const upper = document.createElement('div');
                upper.classList.add("upper-item");
                const itemLeft = document.createElement('div');
                const itemRight = document.createElement('div');
                itemLeft.classList.add("item-left");
                itemRight.classList.add("item-left");
                const pAuthor = document.createElement('p');
                const pTitle = document.createElement('p');
                const bookBtn = document.createElement('button');
                bookBtn.classList.add("operation-btn");
                bookBtn.classList.add("grow");


                if(`${book.status}`=== 'free'){
                    bookBtn.textContent = "Book";
                    bookBtn.addEventListener('click', function (){
                        bookBtn.style.visibility = "hidden";
                        item.style.height = "20vh";
                        upper.style.height = "30%";
                        const bookForm = document.createElement('div');
                        bookForm.classList.add("reservationHolder");
                        const info = document.createElement('p');
                        info.textContent = "Provide reader's library card number.";
                        const bookCopy = document.createElement('p');
                        bookCopy.textContent = `Book's id: ${book.bookCopyId}`;
                        bookCopy.classList.add("bookCopyP");
                        bookCopy.style.color = "var(--washedblack)";
                        const reservationForm = document.createElement('form');
                        reservationForm.classList.add("reservationForm");
                        const cardNum = document.createElement('input');
                        cardNum.classList.add("addInput");
                        cardNum.placeholder = "Library card number";
                        cardNum.type = "text";
                        cardNum.required = true;
                        cardNum.style.margin = "0";
                        cardNum.name = "cardNum";
                        const reservationBtn = document.createElement('input');
                        reservationBtn.classList.add("operation-btn");
                        reservationBtn.classList.add("grow");
                        reservationBtn.type = "submit";
                        reservationBtn.value = "Book";
                        reservationBtn.style.height = "3.5vh";
                        reservationBtn.style.width = "30%";
                        reservationForm.appendChild(cardNum);
                        reservationForm.appendChild(reservationBtn);
                        bookForm.appendChild(bookCopy);
                        bookForm.appendChild(info);
                        bookForm.appendChild(reservationForm);
                        item.appendChild(bookForm);

                        bookForm.addEventListener('submit', function (e){
                            e.preventDefault();
                            const formData = new FormData();
                            formData.append('cardNum',cardNum.value);
                            //formData.append('bookId', String(`${book.id}`));
                            formData.append('bookId', `${book.bookCopyId}`);
                            fetch('/panel/all-books/borrowing', {
                                method: 'POST',
                                body: formData
                            })
                                .then(response => response.text())
                                .then(data => {
                                    clearContainer(bookForm)
                                    bookForm.innerText = data;
                                })
                                .catch(error => console.error('Error:', error));
                        });


                    })
                }
                else {
                    bookBtn.textContent = "Return";

                    bookBtn.addEventListener('click', function (e){
                        e.preventDefault();
                        const formData = new FormData();
                        formData.append('bookCopyId', `${book.bookCopyId}`);
                        fetch('/panel/all-books/return-book', {
                            method: 'POST',
                            body: formData
                        })
                            .then(response => response.text())
                            .then(data => {
                                bookBtn.disabled = true;
                                bookBtn.style.backgroundColor = "var(--peach)";
                                bookBtn.style.color = "var(--washedblack)";
                                bookBtn.innerText = data;
                            })
                            .catch(error => console.error('Error:', error));
                    });

                }
                const arrowHolder = document.createElement('button');
                arrowHolder.classList.add("more");
                arrowHolder.classList.add("grow")
                const arrow = document.createElement('i');
                arrow.classList.add("bi");
                arrow.classList.add("bi-chevron-down");

                arrow.addEventListener('click', bookDetails);

                function bookDetails(){
                    //need correction
                    // while(!item.firstChild.classList.contains("upper-item")){
                    //     item.firstChild.remove();
                    // }
                    arrow.removeEventListener('click', bookDetails);
                    bookBtn.style.visibility = "hidden";
                    item.style.height = "20vh";
                    upper.style.height = "30%";
                    const bookForm = document.createElement('div');
                    bookForm.classList.add("reservationHolder");
                    const bookCopy = document.createElement('p');
                    bookCopy.textContent = `Book's id: ${book.bookCopyId}`;
                    const bookDeleteBtn = document.createElement('button');
                    bookDeleteBtn.classList.add("operation-btn");
                    bookDeleteBtn.classList.add("grow");
                    bookDeleteBtn.innerText = "Delete";

                    bookDeleteBtn.addEventListener('click', function (){

                        const removeBookForm = new FormData();
                        removeBookForm.append('bookCopyId', `${book.bookCopyId}`);

                        fetch('/panel/all-books/delete-book', {
                            method: 'POST',
                            body: removeBookForm
                        })
                            .then(response => response.text())
                            .then(data => {
                                bookDeleteBtn.disabled = true;
                                bookBtn.style.backgroundColor = "var(--peach)";
                                bookBtn.style.color = "var(--washedblack)";
                                bookDeleteBtn.innerText = data;
                            })
                            .catch(error => console.error('Error:', error));

                    })
                    bookForm.appendChild(bookCopy);
                    bookForm.appendChild(bookDeleteBtn);
                    item.appendChild(bookForm);
                }

                arrowHolder.appendChild(arrow);
                itemRight.appendChild(bookBtn);
                itemRight.appendChild(arrowHolder);
                pAuthor.textContent = `${book.author}`;
                pTitle.textContent = `${book.title}`;
                itemLeft.appendChild(pAuthor);
                itemLeft.appendChild(pTitle);

                upper.appendChild(itemLeft);
                upper.appendChild(itemRight);
                item.appendChild(upper);
                itemsContainer.appendChild(item);
            })
        })
}
function logout(){
    fetch(`/panel/logout?sessionKey=${sessionKey}`,{
        method:'POST'
    }).then(response => {
        if (response.ok) {
            window.location.href = '/';
        } else {
            alert('Błąd wylogowania.');
        }
    }).catch(error => {
        console.error('Błąd wylogowania:', error);
    });
}

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
    surname.name = "surname";
    const cardNum = document.createElement('input');
    cardNum.type = "text";
    cardNum.required = true;
    cardNum.placeholder = "Enter user's library card";
    cardNum.classList.add("addInput");
    cardNum.name = "cardNum";
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

function toDefaultView(){
    const itemsContainer = document.getElementById("items-container");
    clearContainer(itemsContainer);
    const addNew = document.getElementById("add-new");
    addNew.style.minHeight = "6vh";
    const upper = document.getElementById("new-upper");
    upper.height = "100%";
    clearContainer(upper);
    const bookFormHolder = document.getElementById("bookFormHolder");
    if(bookFormHolder != null) bookFormHolder.remove();

    const pAddNew = document.createElement('h2');
    const more = document.createElement('h1');
    pAddNew.textContent = "Add new";
    more.textContent = "+";
    upper.appendChild(pAddNew);
    upper.appendChild(more);

    const readerName = document.querySelector("#add-new > p");
    if(readerName){
        readerName.remove();
    }
}