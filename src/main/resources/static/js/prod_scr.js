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
        createForm();
    })

    const contents = [];
    $.get('api/product/get', (data) =>{
        data.forEach(e => contents.push(e));
        fillTable(contents);
        $('.tab').click(()=>{tableFunction(event)});
    })

    let id = -1;
    let idForChanges = -1;

    createTable();
    $('#edit').hide();

    function createForm() {
        refreshForm();

        $('<label for="field_1">Имя продукта</label>').appendTo('fieldset');
        $('<input type="text" id="field_1" name="name" class="prod_in">').appendTo('fieldset');

        $('<label for="field_2">Описание продукта</label>').appendTo('fieldset');
        $('<textarea id="field_2" name="description" cols="30" rows="10" class="prod_in"></textarea>').appendTo('fieldset');

        $('<label for="field_3">Цена продукта</label>').appendTo('fieldset');
        $('<input type="number" id="field_3" name="price" class="prod_in">').appendTo('fieldset');

        $('<select id="field_4" name="category" class="prod_in"></select>').appendTo('fieldset');

        caterories.forEach(e => {
            $(`<option value=${e.id}>${e.category}</option>`).appendTo('#field_4')
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
            tr.attr('id', `${elem.id}`);
            header.forEach(head => {
                if (head.field == "category"){
                    let td = $('<td></td>').appendTo(tr)
                    td.attr('name', `${head.field}`);
                    let a = null;
                    for (let key in elem.category){
                        if (key == 'category')
                            a = elem.category[key];
                    }
                    td.text(a);
                }else {
                    let td = $('<td></td>').appendTo(tr);
                    td.attr('name', `${head.field}`);
                    td.text(elem[head.field]);
                }
            })
        })
    }

    $('#send').click(()=>{
        let formFields = $('.prod_in');
        Array.from(formFields).forEach(ins =>{
            let param = `${ins.name}`;
            temp[param] = ins.value;
            ins.value = '';
        })
        let temp = new Object();
        let cat = null;
        caterories.forEach(e => {
            if (e.id == temp.category){
                cat = e;
            }
        })
        sending('product', temp, headers, contents);
        fillTable();
        createForm();
        $('.tab').click(()=>{tableFunction(event)});
    });

    $('#delete').click(() => {
        deleting('product', id, idForChanges, headers, contents);
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
        let cat = null;
        caterories.forEach(e => {
            if (e.id == temp.category){
                cat = e;
            }
        })
        temp.category = cat;
        editing('product', temp, id, idForChanges, headers, contents);
        fillTable(contents);
        $('.tab').click(()=>{tableFunction(event)});
        createForm();
    });

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
        ascing(headers, contents, 'name')
        fillTable(contents);
        $('.tab').click(()=>{tableFunction(event)})
    })

    $('#desc').click(()=>{
        descing(headers, contents, 'name')
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