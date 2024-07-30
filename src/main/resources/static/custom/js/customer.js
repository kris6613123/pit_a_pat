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

    document.querySelector('.btnCard').addEventListener( 'click', function () {
        makeCard();
    });
}

function customerMod() {
    let form = document.getElementById('f-customer');
    let formData = new FormData(form);
    console.log(formData);
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                alert("등록되었습ㄴ다.");
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
    xhr.open('POST', "/customer/mod/p", true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(obj));
}

function makeCard() {
    let form = document.getElementById('f-add');
    let formData = new FormData(form);
    form.reset();
    let obj= {};
    for (let [ key, value ] of formData.entries()) {
        obj[key] = value;
    }
    console.log(obj);


}


