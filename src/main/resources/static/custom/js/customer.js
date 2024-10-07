<!-- Custom scripts for all pages-->
window.onload = function () {
    btn_customer = document.getElementById('btn_customer');
    btn_customer.addEventListener('click', function () {
        customerMod();
    });

    let btn_cancel = document.querySelectorAll('.btn-cancel');
    btn_cancel.forEach(function (btn) {
        btn.addEventListener('click', function () {
            document.getElementById('f-add').reset();
        });
    });

    document.querySelector('.btnCard').addEventListener('click', function () {
        makeCard();
    });
}

function customerMod() {
    let form = document.getElementById('f-customer');
    let formData = new FormData(form);
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                alert("등록되었습니다.");
                location.href = xhr.responseText;
            } else if (xhr.status === 400) {
                alert(xhr.responseText);
            }
        }
    };
    let obj= {};
    for (let [ key, value ] of formData.entries()) {
        obj[key] = value;
    }
    let patList = [];
    let pat = {};
    let cards = document.querySelectorAll('.pat-info');
    console.log(cards);
    cards.forEach(card => {
        let ppat = card.querySelector('input[name="pat"]').value;
        let species = card.querySelector('input[name="species"]').value;
        let name = card.querySelector('input[name="name"]').value;
        let breed = card.querySelector('input[name="breed"]').value;
        let memo = card.querySelector('input[name="memo"]').value;
        let file = card.querySelector('input[name="file"]').value;

        pat['pat'] = ppat;
        pat['species'] = species;
        pat['name'] = name;
        pat['breed'] = breed;
        pat['memo'] = memo;
        pat['file'] = file;
        patList.push(pat);
        pat = {};
    });
    console.log(patList);
    let data = new FormData();
    data.append( 'vo', new Blob( [ JSON.stringify( obj ) ], { type: 'application/json' } ) );
    data.append( 'patList', new Blob( [ JSON.stringify( patList ) ], { type: 'application/json' } ) );
    xhr.open('POST', "/customer/mod/p", true);
    // xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(data);

}

function makeCard() {
    let form = document.getElementById('f-add');
    let formData = new FormData(form);
    let petObj = {};
    for (let [key, value] of formData.entries()) {
        petObj[key] = value;
    }
    let formFile = document.getElementById('formFile');
    let imgNum = 0;
    if (!!formFile.files[0]) {
        file = formFile.files[0];
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if ( xhr.readyState === XMLHttpRequest.DONE ) {
                if ( xhr.status === 200 ) {
                    console.log("이미지 저장완료 : " + xhr.responseText)
                    imgNum = parseInt(xhr.responseText);
                    make(petObj, imgNum);
                }
                else if( xhr.status === 400 ) {
                    alert( xhr.responseText );
                }
            }
        };

        xhr.open('POST', '/image/add', true);

        let Data = new FormData();
        Data.append( 'formFile', file );
        xhr.send( Data );
    }
    else {
        make(petObj, imgNum);
    }

    document.querySelector('.btn-cancel').click();
}

function make(petObj, imgNum) {
    let img = imgNum === 0 ?
        '<img src="..." className="img-fluid" alt="..." style="width: 150px; height: 120px; object-fit: cover; border-radius: 10px; margin: 5px 0 5px 4px">' :
        `<img src="/image/${imgNum}" className="img-fluid" alt="..." style="width: 150px; height: 120px; object-fit: cover; border-radius: 10px; margin: 5px 0 5px 4px">`;

    let genderIcon = petObj.gender === 'M' ?
        `<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#007bff" class="bi bi-gender-male me-2" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M9 1a4 4 0 1 0 0 8 4 4 0 0 0 0-8M6 4.5h3.293l-3.147 3.146a.5.5 0 1 0 .708.708L10 5.207V8.5a.5.5 0 0 0 1 0V4h-3.5a.5.5 0 0 0 0 1z"/>
        </svg>` :
        `<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="#FF69B4" class="bi bi-gender-female me-2" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M9.5 2a.5.5 0 0 1 0-1h5a.5.5 0 0 1 .5.5v5a.5.5 0 0 1-1 0V2.707L9.871 6.836a5 5 0 1 1-.707-.707L13.293 2zM6 6a4 4 0 1 0 0 8 4 4 0 0 0 0-8"/>
        </svg>`;

    // Creating a new card element with the captured data
    let newCard = document.createElement('div');
    newCard.className = 'card mb-3';
    newCard.style.maxWidth = '540px';
    newCard.innerHTML = `
        <div class="pat-info" hidden> 
            <input hidden name="pat" value="">
            <input hidden name="name" value="${petObj.name}">
            <input hidden name="species" value="${petObj.species}">
            <input hidden name="breed" value="${petObj.breed}">
            <input hidden name="gender" value="${petObj.gender}">
            <input hidden name="memo" value="${petObj.memo}">
            <input hidden name="file" value="${imgNum}">
        </div>
        <div class="card-header">
            <strong>${petObj.species}</strong>
        </div>
        <div class="row g-0">
            <div class="col-md-6 d-flex justify-content-center align-items-center">
                ${img}
            </div>
            <div class="col-md-6">
                <div class="card-body" style="padding-left: 0px">
                    <h5 class="card-title d-flex align-items-center">
                        ${genderIcon}
                        <span>${petObj.name}</span>
                    </h5>
                    <p class="card-text mb-1">종: ${petObj.breed}</p>
                    <p class="card-text mb-0"><small class="text-muted">메모: ${petObj.memo}</small></p>
                </div>
            </div>
        </div>
    `;

    // Adding the new card to the card container
    document.getElementById('cardContainer').appendChild(newCard);
}
function getImgNum(file) {
    console.log(file);
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if ( xhr.readyState === XMLHttpRequest.DONE ) {
            if ( xhr.status === 200 ) {
                console.log("이미지 저장완료 : " + xhr.responseText)
                return parseInt(xhr.responseText);
            }
            else if( xhr.status === 400 ) {
                alert( xhr.responseText );
            }
        }
    };

    xhr.open('POST', '/image/add', true);

    let formData = new FormData();
    formData.append( 'formFile', file );
    xhr.send( formData );
}


