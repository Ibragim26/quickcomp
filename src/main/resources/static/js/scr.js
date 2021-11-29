$(function () {
    const headers = [
        {
            name: 'Наименование',
            field: 'category'
        },
        {
            name: 'Оценка',
            field: 'rating'
        }
    ];
    const contents = [];

    $.get('api/category/get', (data) =>{
        data.forEach(e => contents.push(e));
        fillTable(contents);
    })

    createForm();

    function createForm() {
        refreshForm();
        $('<label for="field_1">Категория</label>').appendTo('fieldset');
        $('<input type="text" id="field_1" class="prod_in" name="category">').appendTo('fieldset');

        $('<label for="field_2">Рейтнг</label>').appendTo('fieldset');
        $('<input type="text" id="field_1" class="prod_in" name="rating">').appendTo('fieldset');
    }

    let id = -1;
    let idForChanges = -1;

    createTable(headers);
    $('.tab').click(()=>{tableFunction(event)});
    $('#edit').hide();

    function fillTable(content = contents, header = headers) {
        content.forEach((elem) => {
            let tr = $('<tr class="forAnyChange"></tr>').appendTo('.tab');
            tr.attr('id', `${elem.id}`);
            header.forEach(head => {
                let td = $('<td></td>').appendTo(tr);
                td.attr('name', `${head.field}`);
                td.text(elem[head.field]);
            })
        })
    }

    $('#send').click(()=>{
        let temp = new Object();
        let formFields = $('.prod_in');
        Array.from(formFields).forEach(ins =>{
            let param = `${ins.name}`;
            temp[param] = ins.value;
            ins.value = '';
        })
        sending('category', temp, headers, contents);
        fillTable();
        createForm();
        $('.tab').click(()=>{tableFunction(event)});
    });

    $('#delete').click(() => {
        deleting('category', id, idForChanges, headers, contents);
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
        editing('category', temp, id, idForChanges, headers, contents);
        fillTable(contents);
        $('.tab').click(()=>{tableFunction(event)});
        createForm();
    });

    $('#filter').on('input', (event) => {
        let filter = event.target.value;
        let filteredContent = contents.filter(elem => {
            if (elem.category.toUpperCase().includes(filter.toUpperCase()))
                return elem;
        })
        $('.tab').remove();
        createTable(headers);
        fillTable(filteredContent);
        $('.tab').click(()=>{tableFunction(event)})
    })

    $('#asc').click(()=>{
        ascing(headers, contents, 'category')
        fillTable(contents);
        $('.tab').click(()=>{tableFunction(event)})
    })

    $('#desc').click(()=>{
        descing(headers, contents, 'category')
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
            event.target.parentElement.classList.add('forColor');
            idForChanges =  event.target.parentElement.id;
            Array.from(formFields).forEach(e => {
                e.value = $(`.forColor td[name=${e.name}]`).text()
            })
            $('#edit').show();
            $('#send').hide();
        }
    }
})