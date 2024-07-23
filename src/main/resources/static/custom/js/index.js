<!-- Custom scripts for all pages-->
window.onload = function () {
    // 적립 modal x, cancel시 초기화 함수
    let btns_cancel = document.querySelectorAll('.btn-cancel1');
    btns_cancel.forEach(function (btn) {
        btn.addEventListener('click', function () {
            document.getElementById('f-add').reset();
            let customerUl = document.getElementById('u_customerList');
            customerUl.innerHTML = '';
            document.getElementById('modal-body1').hidden = false;
            document.getElementById('modal-body2').hidden = true;
        });
    });

    let btn = document.querySelector('.btnCard1');
    btn.addEventListener( 'click', function () {
        let form = document.getElementById('f-add');
        console.log(form.action);
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
    });

    let customer_btn = document.getElementById('btn_findCustomer');
    customer_btn.addEventListener( 'click', function () {
        let keyword =  document.getElementById('keyword').value;
        document.getElementById('keyword').value = '';
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
                let customerUl = document.getElementById('u_customerList');
                customerUl.innerHTML = '';

                if (data.length > 0) {
                    data.forEach(function(customer) {
                        let row = document.createElement('li');
                        row.className = 'list-group-item';
                        row.textContent = customer.name + '   ' + customer.tel;
                        row.addEventListener('click', function() {
                            document.getElementById('modal-body1').hidden = true;
                            document.getElementById('modal-body2').hidden = false;
                            document.getElementById('customer').value = customer.customer;
                            document.getElementById('name').value = customer.name;
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
    });



}

function updateBtnText(text) {
    let input = document.querySelector('.i_variable_p');
    if (text == '가격') {
        document.getElementById('dropdownButton').textContent = text;
        input.name = 'price';
    }
    else {
        document.getElementById('dropdownButton').textContent = text;
        input.name = 'point';
    }
}
