$(function () {
    const headers = [
        {
            name: 'Продукт',
            field: 'product'
        },
        {
            name: 'Адрес',
            field: 'address'
        },
        {
            name: 'Дата',
            field: 'date'
        },
        {
            name: 'Статус',
            field: 'orderStatus'
        }
    ];

    const stats = [];
    const products = [];
    const contents = [];

    $.get('api/order/get', (data) =>{
        data.forEach(e => contents.push(e));
        fillTable(contents);
        $('.tab').click(()=>{tableFunction(event)})
    })

    $.get('api/product/get', (data) =>{
        data.forEach(e => products.push(e));
        $.get('api/status/get', (data) =>{
            data.forEach(e => stats.push(e));
            createForm();
        })
    })
    let id = -1;
    let idForChanges = -1;

    createTable();
    $('#edit').hide();


    function createForm() {
        refreshForm();

        $('<label for="field_1">Адрес</label>').appendTo('fieldset');
        $('<input type="text" id="field_1" name="address" class="prod_in">').appendTo('fieldset');
        $('<label for="field_2">Дата доставки</label>').appendTo('fieldset');
        $('<input type="date" id="field_2" name="date" class="prod_in">').appendTo('fieldset');

        $('<select id="field_3" name="product" class="prod_in"></select>').appendTo('fieldset');

        products.forEach(e => {
            $(`<option value=${e.id}>${e.name}</option>`).appendTo('#field_3')
        })

        $('<select id="field_4" name="orderStatus" class="prod_in"></select>').appendTo('fieldset');

        stats.forEach(e => {
            $(`<option value=${e.id}>${e.status}</option>`).appendTo('#field_4')
        })
    }

    function createTable(header = headers) {
        $('<table class="tab" border="2"></table>').appendTo('.dynTa');
        $('<tr class="tabhead"></tr>').appendTo('.tab');
        header.forEach(head => {
            let th = $('<th></th>').appendTo('.tabhead');
            th.text(head.name);
        });
    }

    function fillTable(content = contents, header = headers) {
        content.forEach((elem) => {
            let tr = $('<tr class="forAnyChange"></tr>').appendTo('.tab');
            tr.attr('id', `${elem.id}`)
            header.forEach(head => {
                if (head.field == 'orderStatus'){
                    let td = $('<td></td>').appendTo(tr)
                    td.attr('name', `${head.field}`);
                    let a = null;
                    for (let key in elem.orderStatus){
                        if (key == 'status')
                            a = elem.orderStatus[key]
                    }
                    td.text(a);
                }else if (head.field == 'product'){
                    let td = $('<td></td>').appendTo(tr)
                    td.attr('name', `${head.field}`);
                    let a = null;
                    for (let key in elem.product){
                        if (key == 'name')
                            a = elem.product[key]
                    }
                    td.text(a);
                }else {
                    let td = $('<td></td>').appendTo(tr)
                    td.attr('name', `${head.field}`);
                    td.text(elem[head.field]);
                }
            })
        })
    }

    $('#send').click(()=>{
        let temp = {};
        let formFields = $('.prod_in');
        Array.from(formFields).forEach(ins =>{
            let param = `${ins.name}`;
            temp[param] = ins.value;
            ins.value = '';
        })
        let stat = null;
        stats.forEach(e => {
            console.log(e.id);
            console.log(temp.orderStatus);
            if (e.id == temp.orderStatus){
                stat = e;
            }

        })
        temp.orderStatus = stat;
        let product = null;
        products.forEach(e => {
            console.log(e.id);
            console.log(temp.product);
            if (e.id == temp.product){
                product = e;
            }
        })
        temp.product = product;

        sending('order', temp, headers, contents);
        fillTable();
        createForm();
        $('.tab').click(()=>{tableFunction(event)});
    });

    $('#delete').click(() => {
        deleting('order', id, idForChanges, headers, contents);
        fillTable();
        $('.tab').click(()=>{tableFunction(event)})
        createForm();
    });

    $('#edit').click(() => {
        let temp = {};
        let formFields = $('.prod_in');
        Array.from(formFields).forEach(e => {
            temp[e.name] = e.value;
            e.value = '';
        })
        let stat = null;
        stats.forEach(e => {
            if (e.id == temp.orderStatus){
                stat = e;
            }
        })
        temp.orderStatus = stat;
        let product = null;
        products.forEach(e => {
            if (e.id == temp.product){
                product = e;
            }
        })
        temp.product = product;
        editing('order', temp, id, idForChanges, headers, contents);
        fillTable(contents);
        $('.tab').click(()=>{tableFunction(event)});
        createForm();
    });


    $('#filter').on('input', (event) => {
        let filter = event.target.value;
        let filteredContent = contents.filter(elem => {
            if (elem.address.toUpperCase().includes(filter.toUpperCase()))
                return elem;
        })
        $('.tab').remove();
        createTable();
        fillTable(filteredContent);
        $('.tab').click(()=>{tableFunction(event)})
    })

    $('#asc').click(()=>{
        ascing(headers, contents, 'address')
        fillTable(contents);
        $('.tab').click(()=>{tableFunction(event)})
    });
    $('#desc').click(()=>{
        descing(headers, contents, 'address')
        fillTable(contents);
        $('.tab').click(()=>{tableFunction(event)})
    });

    function tableFunction(event) {
        if (event.target.parentElement.className === 'forAnyChange') {
            let formFields = $('form input');
            $('.forColor').removeClass('forColor');
            id = contents.findIndex(e => {
                if (e.id == event.target.parentElement.id)
                    return e
            });
            event.target.parentElement.classList.add('forColor')
            idForChanges =  event.target.parentElement.id;

            Array.from(formFields).forEach(e => {
                e.value = $(`.forColor td[name=${e.name}]`).text()
            })
            $('#edit').show();
            $('#send').hide();
        }
    }
})