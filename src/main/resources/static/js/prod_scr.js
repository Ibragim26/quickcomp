$(function () {
    const headers = [
        {
            name: 'Название',
            field: 'name'
        },
        {
            name: 'Описание',
            field: 'description'
        },
        {
            name: 'Цена',
            field: 'price'
        },
        {
            name: 'Категория',
            field: 'category'
        }
    ];

    const caterories = [];
    $.get('api/category/get', (data) =>{
        data.forEach(e => caterories.push(e));
        createForm()
    })

    const contents = [];
    $.get('api/product/get', (data) =>{
        data.forEach(e => contents.push(e));
        fillTable(contents)
    })

    let id = -1;
    let idForChanges = -1;

    createTable()
    $('#edit').hide()

    createForm()

    function createForm() {
        if ($('form input').length != 0)
            Array.from($('input')).forEach(e => e.remove())
        if ($('label').length != 0)
            Array.from($('label')).forEach(e => e.remove())
        if ($('textarea').length != 0)
            Array.from($('textarea')).forEach(e => e.remove())
        if ($('select').length != 0)
            Array.from($('select')).forEach(e => e.remove())

        $('<label for="field_1">Имя продукта</label>').appendTo('fieldset');
        $('<input type="text" id="field_1" name="name" class="prod_in">').appendTo('fieldset');

        $('<label for="field_2">Описание продукта</label>').appendTo('fieldset');
        $('<textarea id="field_2"name="description" cols="30" rows="10" class="prod_in"></textarea>').appendTo('fieldset');

        $('<label for="field_3">Цена продукта</label>').appendTo('fieldset');
        $('<input type="number" id="field_3" name="price" class="prod_in">').appendTo('fieldset');

        $('<select id="field_4" name="category" class="prod_in"></select>').appendTo('fieldset');

        caterories.forEach(e => {
            $(`<option value=${e.id}>${e.category}</option>`).appendTo('#field_4')//('<option value="${e.id}">"${e.name}"</option>');
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
                if (head.field == "category"){
                    let td = $('<td></td>').appendTo(tr)
                    td.attr('name', `${head.field}`);
                    td.text(elem[head.field].category);
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

        let maxId = 1;
        contents.forEach(elem => {
            if (elem.id > maxId)
                maxId = elem.id;
        })
        temp.id = ++maxId;

        let cat = null;
        caterories.forEach(e => {
            if (e.id == temp.category){
                cat = e;
            }
        })
        temp.category = cat;

        $.ajax({
            url: 'api/product/post',
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
                $('.tab').click(()=>{tableFunction(event)})
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
            url: `/api/product/delete/${idForChanges}`,
            method: 'DELETE',
            dataType: 'json',
            success: () => {
                let formFields = $('.prod_in')
                Array.from(formFields).forEach(e => {
                    e.value = '';
                })
                contents.splice(id, 1);
                $('.tab').remove();
                createTable()
                fillTable(contents);
                $('.tab').click(()=>{tableFunction(event)})
                $('#edit').hide();
                $('#send').show();
            }
        })
    })

    $('#edit').click( () => {
        let formFields = $('.prod_in')
        let temp = {}
        Array.from(formFields).forEach(e => {
            temp[e.name] = e.value;
            e.value = ''
        })
        temp.id = idForChanges;

        let cat = null;
        caterories.forEach(e => {
            if (e.id == temp.category){
                cat = e;
            }
        })
        temp.category = cat;

        $.ajax({
            url: `/api/product/update/${idForChanges}`,
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
            }
        })

        $('#edit').hide();
        $('#send').show();
    })

    $('#filter').on('input', (event) => {
        let filter = event.target.value;
        let filteredContent = contents.filter(elem => {
            if (elem.name.toUpperCase().includes(filter.toUpperCase()))
                return elem;
        })
        $('.tab').remove();
        createTable();
        fillTable(filteredContent);
        $('.tab').click(()=>{tableFunction(event)})
    })

    $('#asc').click(()=>{
        contents.sort( (a, b)=> {
            if (a.name.charAt(0) === b.name.charAt(0)) return 0
            else if (a.name.charAt(0) > b.name.charAt(0)) return 1
            else return -1
        })
        $('.tab').remove();
        createTable();
        fillTable(contents);
        $('.tab').click(()=>{tableFunction(event)})
    })

    $('#desc').click(()=>{
        contents.sort( (a, b)=> {
            if (a.name.charAt(0) === b.name.charAt(0)) return 0
            else if (a.name.charAt(0) < b.name.charAt(0)) return 1
            else return -1
        })
        $('.tab').remove();
        createTable();
        fillTable(contents);
        $('.tab').click(()=>{tableFunction(event)})
    })

    function tableFunction(event) {
        if (event.target.parentElement.className === 'forAnyChange') {
            let formFields = $('.prod_in');
            $('.forColor').removeClass('forColor');
            id = contents.findIndex(e => {
                if (e.id == event.target.parentElement.id)
                    return e
            });
            event.target.parentElement.classList.add('forColor')

            idForChanges = event.target.parentElement.id

            Array.from(formFields).forEach(e => {
                e.value = $(`.forColor td[name=${e.name}]`).text()
            })
            $('#edit').show();
            $('#send').hide();
        }
    }
})