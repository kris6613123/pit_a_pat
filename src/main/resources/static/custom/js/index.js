<!-- Custom scripts for all pages-->
window.onload = function () {
    // 적립 modal x, cancel시 초기화 함수
    let btns_cancel1 = document.querySelectorAll('.btn-cancel1');
    btns_cancel1.forEach(function (btn) {
        btn.addEventListener('click', function () {
            document.getElementById('f-add').reset();
            let customerUl = document.getElementById('customerList1');
            customerUl.innerHTML = '';
            document.getElementById('modal-body1_1').hidden = false;
            document.getElementById('modal-body1_2').hidden = true;
        });
    });

    // 차감 modal x, cancel시 초기화 함수
    let btns_cancel2 = document.querySelectorAll('.btn-cancel2');
    btns_cancel2.forEach(function (btn) {
        btn.addEventListener('click', function () {
            document.getElementById('f-sub').reset();
            let customerUl = document.getElementById('customerList2');
            customerUl.innerHTML = '';
            document.getElementById('modal-body2_1').hidden = false;
            document.getElementById('modal-body2_2').hidden = true;
        });
    });

    // 예약 modal x, cancel시 초기화 함수
    let btns_cancel3 = document.querySelectorAll('.btn-cancel3');
    btns_cancel3.forEach(function (btn) {
        btn.addEventListener('click', function () {
            document.getElementById('f-reserve').reset();
            let customerUl = document.getElementById('customerList3');
            customerUl.innerHTML = '';
            document.getElementById('modal-body3_1').hidden = false;
            document.getElementById('modal-body3_2').hidden = true;
        });
    });
    document.getElementById('btn_findCustomer1').addEventListener( 'click', function () {
        findCustomerList('1');
    });
    document.getElementById('btn_findCustomer2').addEventListener( 'click', function () {
        findCustomerList('2');
    });
    document.getElementById('btn_findCustomer3').addEventListener( 'click', function () {
        findCustomerList('3');
    });

    document.querySelector('.btnCard1').addEventListener( 'click', function () {
        transactionMod('add');
    });
    document.querySelector('.btnCard2').addEventListener( 'click', function () {
        transactionMod('sub');
    });
}


function transactionMod(text) {
    let form = document.getElementById('f-' + text);
    let formData = new FormData(form);
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                alert(xhr.responseText);
                location.reload();
            } else if (xhr.status === 400) {
                alert(xhr.responseText);
            }
        }
    };
    let obj= {};
    for (let [ key, value ] of formData.entries()) {
        obj[key] = value;
    }
    xhr.open('POST', "/transaction/mod", true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(obj));
}

function findCustomerList(text) {
    let keyword =  document.getElementById('keyword' + text).value;
    document.getElementById('keyword' + text).value = '';
    let resultList = [];
    fetch('/customer/' + keyword + '/list', {
        method: 'POST'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            let customerUl = document.getElementById('customerList' + text);
            customerUl.innerHTML = '';

            if (data.length > 0) {
                data.forEach(function(customer) {
                    let row = document.createElement('li');
                    row.className = 'list-group-item';
                    row.innerHTML = '<span class="customer-name">' + customer.name + '</span>' + '   ( ' + telFormat(customer.tel) + ' )';
                    // row.textContent = customer.name + '   ( ' + telFormat(customer.tel) + ' )';
                    row.addEventListener('click', function() {
                        document.getElementById('modal-body' + text + '_1').hidden = true;
                        document.getElementById('modal-body' + text + '_2').hidden = false;
                        document.getElementById('customer' + text).value = customer.customer;
                        document.getElementById('name' + text).value = customer.name;
                    });
                    customerUl.appendChild(row);
                });
            } else {
                let row = document.createElement('li');
                row.className = 'list-group-item';
                row.textContent = '일치하는 결과가 없습니다.';
                customerUl.appendChild(row);
            }

        })
        .catch(error => {
            console.error('Fetch error:', error);
            alert('Failed to fetch data');
        });
}

function updateBtnText(text) {
    let input = document.querySelector('.i_variable_p');
    if (text == '가격') {
        document.getElementById('dropdownButton1').textContent = text;
        input.name = 'price';
    }
    else {
        document.getElementById('dropdownButton1').textContent = text;
        input.name = 'point';
    }
}

function telFormat(str) {
    str = str.replace(/[^0-9]/g, '');
    let tmp = '';
    if( str.length < 4){
        return str;
    }else if(str.length < 7){
        tmp += str.substr(0, 3);
        tmp += '-';
        tmp += str.substr(3);
        return tmp;
    }else if(str.length < 11){
        tmp += str.substr(0, 3);
        tmp += '-';
        tmp += str.substr(3, 3);
        tmp += '-';
        tmp += str.substr(6);
        return tmp;
    }else{
        tmp += str.substr(0, 3);
        tmp += '-';
        tmp += str.substr(3, 4);
        tmp += '-';
        tmp += str.substr(7);
        return tmp;
    }
}
