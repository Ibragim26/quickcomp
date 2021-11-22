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

    const contents = [];
    $.get('http://localhost:8080/api/product/get', (data) =>{
        data.forEach(e => contents.push(e));
        fillTable(contents)
    })

    let id = -1;
    let idForChanges = -1;

    createTable()
    $('#edit').hide()

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
                let td = $('<td></td>').appendTo(tr)
                td.attr('name', `${head.field}`);
                td.text(elem[head.field]);
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

        $.ajax({
            url: 'http://localhost:8080/api/product/post',
            type: 'POST',
            dataType: 'json',
            cache: false,
            contentType: 'application/json',
            data: JSON.stringify(temp),
            success: (e) => {
                // console.log(e.category);
                // let cat;
                // $.get('http://localhost:8080/api/category/get', (data) =>{
                //     data.forEach(e => {
                //         console.log(e)
                //         if (e.category === temp.category) {
                //             cat = e.category;
                //             temp.category = e.id;
                //         }
                //     });
                // })

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

        $('label[for="field_1"]').text('Введите наименование');
        $('label[for="field_2"]').text('Введите описание');
        $('label[for="field_3"]').text('Введите цену');

        $.ajax({
            url: `http://localhost:8080/api/product/delete/${idForChanges}`,
            method: 'DELETE',
            dataType: 'json',
            success: () => {
                let formFields = $('.prod_in')

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
    })

    $('#edit').click( () => {
        let formFields = $('.prod_in')
        let temp = {}
        Array.from(formFields).forEach(e => {
            temp[e.name] = e.value;
            e.value = ''
        })
        temp.id = idForChanges;
        $.ajax({
            url: `http://localhost:8080/api/product/update/${idForChanges}`,
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
                $('.tab').click(()=>{tableFunction(event)})
            }
        })

        $('label[for="field_1"]').text('Введите наименование');
        $('label[for="field_2"]').text('Введите описание');
        $('label[for="field_3"]').text('Введите цену');

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
            if (a.category.charAt(0) === b.category.charAt(0)) return 0
            else if (a.category.charAt(0) > b.category.charAt(0)) return 1
            else return -1
        })
        $('.tab').remove();
        createTable();
        fillTable(contents);
        $('.tab').click(()=>{tableFunction(event)})
    })

    $('#desc').click(()=>{
        contents.sort( (a, b)=> {
            if (a.category.charAt(0) === b.category.charAt(0)) return 0
            else if (a.category.charAt(0) < b.category.charAt(0)) return 1
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

            $('label[for="field_1"]').text('Поменяйте наименование');
            $('label[for="field_2"]').text('Поменяйте описание');
            $('label[for="field_3"]').text('Поменяйте цену');

            Array.from(formFields).forEach(e => {
                e.value = $(`.forColor td[name=${e.name}]`).text()
            })
            $('#edit').show();
            $('#send').hide();
        }
    }
})