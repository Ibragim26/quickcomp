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

    $.get('http://localhost:8080/api/order/get', (data) =>{
        data.forEach(e => contents.push(e));
        fillTable(contents)
    })

    $.get('http://localhost:8080/api/product/get', (data) =>{
        data.forEach(e => products.push(e));
        $.get('http://localhost:8080/api/status/get', (data) =>{
            data.forEach(e => stats.push(e));
            createForm()
        })
    })
    let id = -1;
    let idForChanges = -1;

    createTable()
    $('#edit').hide()


    function createForm() {
        if ($('input').length != 0)
            Array.from($('input')).forEach(e => e.remove())
        if ($('label').length != 0)
            Array.from($('label')).forEach(e => e.remove())
        if ($('textarea').length != 0)
            Array.from($('textarea')).forEach(e => e.remove())
        if ($('select').length != 0)
            Array.from($('select')).forEach(e => e.remove())

        $('<label for="field_1">Введите ваш адрес</label>').appendTo('fieldset');
        $('<input type="text" id="field_1" name="address" class="prod_in">').appendTo('fieldset');
        $('<label for="field_2">Введите предполагаемую дату доставки</label>').appendTo('fieldset');
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

    $('#send').click(function(){
        let temp = new Object();
        let formFields = $('.prod_in');
        Array.from(formFields).forEach(ins =>{
            let param = `${ins.name}`
            temp[param] = ins.value;
            ins.value = '';
        })

        let maxId = 0;
        contents.forEach(elem => {
            if (elem.id > maxId)
                maxId = elem.id;
        })
        temp.id = ++maxId;

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

        $.ajax({
            url: 'http://localhost:8080/api/order/post',
            type: 'POST',
            dataType: 'json',
            cache: false,
            contentType: 'application/json',
            data: JSON.stringify(temp),
            success: () => {
                contents.push(temp);
                $('.tab').remove();
                createTable()
                fillTable()
            }
        })
    })

    $('.tab').click(()=>{tableFunction(event)})

    $('#delete').click(function () {
        if (id === null || id === undefined) return;
        let result = confirm('Удалить выбранную строку ?');
        if (!result) {
            $('.forColor').removeClass('forColor');
            return;
        }

        $.ajax({
            url: `http://localhost:8080/api/order/delete/${idForChanges}`,
            method: 'DELETE',
            dataType: 'json',
            success: () => {
                let formFields = $('form input')
                Array.from(formFields).forEach(e => {
                    e.value = '';
                })
                contents.splice(id, 1);
                $('#edit').hide();
                $('#send').show();
                $('.tab').remove();
                createTable()
                fillTable(contents);
                $('.tab').click(()=>{tableFunction(event)})
                id = null;
            }
        })
        $('label[for="field_1"]').text('Введите адрес');
        $('label[for="field_2"]').text('Введите предполагаемую дату доставки');
    })

    $('#edit').click( () => {
        let formFields = $('form input')
        let temp = {}
        Array.from(formFields).forEach(e => {
            temp[e.name] = e.value;
            e.value = ''
        })
        temp.id = id;

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

        $.ajax({
            url: `http://localhost:8080/api/order/update/${idForChanges}`,
            method: 'PUT',
            dataType: 'json',
            data: JSON.stringify(temp),
            cache: false,
            contentType: 'application/json',
            success: () => {
                contents[id] = temp;
                $('.tab').remove();
                createTable()
                fillTable(contents);
                $('.tab').click(()=>{tableFunction(event)})
                id = null;
            }
        })

        $('label[for="field_1"]').text('Введите адрес');
        $('label[for="field_2"]').text('Введите предполагаемую дату доставки');
            $('#edit').hide();
            $('#send').show();
        })

        $('#filter').on('input', (event) => {
            let filter = event.target.value;
            let filteredContent = contents.filter(elem => {
                if (elem.category.toUpperCase().includes(filter.toUpperCase()))
                    return elem;
            })
            $('.tab').remove();
            createTable();
            fillTable(filteredContent);
            $('.tab').click(()=>{tableFunction(event)})
        })

        $('#asc').click(()=>{
            contents.sort( (a, b)=> {
                if (a.address.charAt(0) === b.address.charAt(0)) return 0
                else if (a.address.charAt(0) > b.address.charAt(0)) return 1
                else return -1
            })
            $('.tab').remove();
            createTable();
            fillTable(contents);
            $('.tab').click(()=>{tableFunction(event)})
        })

        $('#desc').click(()=>{
            contents.sort( (a, b)=> {
                if (a.address.charAt(0) === b.address.charAt(0)) return 0
                else if (a.address.charAt(0) < b.address.charAt(0)) return 1
                else return -1
            })
            $('.tab').remove();
            createTable();
            fillTable(contents);
            $('.tab').click(()=>{tableFunction(event)})
        })

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

                $('label[for="field_1"]').text('Поменяйте адрес');
                $('label[for="field_2"]').text('Поменяйте предполагаемую дату доставки');

                Array.from(formFields).forEach(e => {
                    e.value = $(`.forColor td[name=${e.name}]`).text()
                })
                $('#edit').show();
                $('#send').hide();

            }
    }
})