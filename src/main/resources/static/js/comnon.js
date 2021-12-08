$(function () {
    const categoryHeaders = [
        {
            name: 'Наименование',
            field: 'category'
        },
        {
            name: 'Оценка',
            field: 'rating'
        }
    ];
    const productHeaders = [
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
    const orderHeaders = [
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

    let header = [];
    let content = [];

    let categoryForProduct = [];

    let statsForOrder = [];
    let productsForOrder = [];

    let state = 'category';
    let id = -1;
    let idForChanges = -1;

    $('#category').click(() => {
        state = 'category';
        init();
    })
    $('#product').click(() => {
        state = 'product';
        init();
    })
    $('#order').click(() => {
        state = 'order';
        init();
    })

    init()

    function init() {
        createTable();
        fillTable();
        createForm();
        $('.tab').click(()=>{tableFunction(event)});
    }

    $('#edit').hide();

    function createTable() {
        if ($('.tab'))
            $('.tab').remove();

        if (state == 'product'){
            header = productHeaders;
        }else if (state == 'category'){
            header = categoryHeaders;
        }else if (state == 'order'){
            header = orderHeaders;
        }
        $('<table class="tab" border="2"></table>').appendTo('.dynTa');
        $('<tr class="tabhead"></tr>').appendTo('.tab');
        header.forEach(head => {
            let th = $('<th></th>').appendTo('.tabhead');
            th.text(head.name);
        });
    }
    function createForm() {
        if ($('fieldset input').length)
            Array.from($('fieldset input')).forEach(e => e.remove())
        if ($('fieldset label').length)
            Array.from($('fieldset label')).forEach(e => e.remove())
        if ($('fieldset textarea').length)
            Array.from($('fieldset textarea')).forEach(e => e.remove())
        if ($('fieldset select').length)
            Array.from($('fieldset select')).forEach(e => e.remove())

        if (state == 'category'){
            $('<label for="field_1">Категория</label>').appendTo('fieldset');
            $('<input type="text" id="field_1" class="prod_in" name="category">').appendTo('fieldset');

            $('<label for="field_2">Рейтнг</label>').appendTo('fieldset');
            $('<input type="text" id="field_1" class="prod_in" name="rating">').appendTo('fieldset');
        }
        else if (state == 'product'){
            $.get('api/category/get', (data) => {
                categoryForProduct = data;
                $('<label for="field_1">Имя продукта</label>').appendTo('fieldset');
                $('<input type="text" id="field_1" name="name" class="prod_in">').appendTo('fieldset');

                $('<label for="field_2">Описание продукта</label>').appendTo('fieldset');
                $('<textarea id="field_2" name="description" cols="30" rows="10" class="prod_in"></textarea>').appendTo('fieldset');

                $('<label for="field_3">Цена продукта</label>').appendTo('fieldset');
                $('<input type="number" id="field_3" name="price" class="prod_in">').appendTo('fieldset');

                $('<select id="field_4" name="category" class="prod_in"></select>').appendTo('fieldset');

                categoryForProduct.forEach(e => {
                    $(`<option value=${e.id}>${e.category}</option>`).appendTo('#field_4')
                })
            })
        } else if (state == 'order'){
            $.get('api/product/get', (data) =>{
                productsForOrder = data;
                $.get('api/status/get', (data) =>{
                    statsForOrder = data;

                    $('<label for="field_1">Адрес</label>').appendTo('fieldset');
                    $('<input type="text" id="field_1" name="address" class="prod_in">').appendTo('fieldset');
                    $('<label for="field_2">Дата доставки</label>').appendTo('fieldset');
                    $('<input type="date" id="field_2" name="date" class="prod_in">').appendTo('fieldset');

                    $('<select id="field_3" name="product" class="prod_in"></select>').appendTo('fieldset');

                    productsForOrder.forEach(e => {
                        $(`<option value=${e.id}>${e.name}</option>`).appendTo('#field_3')
                    })

                    $('<select id="field_4" name="orderStatus" class="prod_in"></select>').appendTo('fieldset');

                    statsForOrder.forEach(e => {
                        $(`<option value=${e.id}>${e.status}</option>`).appendTo('#field_4')
                    })
                })
            })


        }
    }
    function fillTable() {
        $.get(`api/${state}/get`, (data) => {
            content = data;
            content.forEach((elem) => {
                let tr = $('<tr class="forAnyChange"></tr>').appendTo('.tab');
                tr.attr('id', `${elem.id}`);
                header.forEach(head => {
                    let td = $('<td></td>').appendTo(tr);
                    td.attr('name', `${head.field}`);
                    td.text(elem[head.field]);
                })
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
        let maxId = 0;
        content.forEach(elem => {
            if (elem.id > maxId)
                maxId = elem.id;
        })
        // if (state == 'product') {
        //     categoryForProduct.forEach(e => {
        //         if (e.id == temp.category) {
        //             temp.category = e;
        //         }
        //     })
        // }
        // if (state == 'order') {
        //     statsForOrder.forEach(e => {
        //         if (e.id == temp.orderStatus){
        //             temp.orderStatus = e;
        //         }
        //     })
        //     let x;
        //     productsForOrder.forEach(e => {
        //         if (e.id == temp.product){
        //             temp.product = e;
        //             $.get('api/category/get', (data) => {
        //                 categoryForProduct = data;
        //                 categoryForProduct.forEach(e => {
        //                     if (e.category == temp.product.category) {
        //                         x = e;
        //                     }
        //                 })
        //             })
        //         }
        //     })
        //     temp.product.category = x;
        // }
        let token = $("meta[name='_csrf']").attr("content");
        $.ajax({
            url: `api/${state}/post`,
            type: 'POST',
            dataType: 'json',
            cache: false,
            contentType: 'application/json',
            data: JSON.stringify(temp),
            headers: {"X-CSRF-TOKEN": token},
            success: () => {
                content.push(temp);
                init();
            }
        })
    });

    $('#delete').click(() => {
        if (id == null || id == undefined) return;
        let result = confirm('Удалить выбранную строку ?');
        if (!result) {
            $('.forColor').removeClass('forColor');
            return;
        }
        let token = $("meta[name='_csrf']").attr("content");
        $.ajax({
            url: `api/${state}/delete/${idForChanges}`,
            method: 'DELETE',
            dataType: 'json',
            headers: {"X-CSRF-TOKEN": token},
            success: () => {
                let formFields = $('form input');
                Array.from(formFields).forEach(e => {
                    e.value = '';
                })
                content.splice(id, 1);
                $('#edit').hide();
                $('#send').show();
                init();
                }
            })
    });

    $('#edit').click(() => {
        let temp = {};
        let formFields = $('.prod_in');
        Array.from(formFields).forEach(e => {
            temp[e.name] = e.value;
            e.value = '';
        })
        temp.id = id;
        // if (state == 'product') {
        //     categoryForProduct.forEach(e => {
        //         if (e.id == temp.category) {
        //             temp.category = e;
        //         }
        //     })
        // }
        //
        // if (state == 'order') {
        //     statsForOrder.forEach(e => {
        //         if (e.id == temp.orderStatus){
        //             temp.orderStatus = e;
        //         }
        //     })
        //     let x;
        //     productsForOrder.forEach(e => {
        //         if (e.id == temp.product){
        //             temp.product = e;
        //             $.get('api/category/get', (data) => {
        //                 categoryForProduct = data;
        //                 categoryForProduct.forEach(e => {
        //                     if (e.category == temp.product.category) {
        //                         x = e;
        //                     }
        //                 })
        //             })
        //         }
        //     })
        //     temp.product.category = x;
        // }
        let token = $("meta[name='_csrf']").attr("content");
        $.ajax({
            url: `api/${state}/update/${idForChanges}`,
            method: 'PUT',
            dataType: 'json',
            data: JSON.stringify(temp),
            cache: false,
            contentType: 'application/json',
            headers: {"X-CSRF-TOKEN": token},
            success: () => {
                $('#edit').hide();
                $('#send').show();
                content[id] = temp;
                init();
            }
        })
    })

    $('#filter').on('input', (event) => {
        let filter = event.target.value;
        let filteredField;
        if (state == 'category')
            filteredField = 'category'
        else if (state == 'product'){
            filteredField = 'name';
        }else if (state == 'order'){
            filteredField = 'address';
        }
        let filteredContent = content.filter(elem => {
            if (elem[`${filteredField}`].toUpperCase().includes(filter.toUpperCase()))
                return elem;
        })
        $('.tab').remove();
        createTable();
        filteredContent.forEach((elem) => {
            let tr = $('<tr class="forAnyChange"></tr>').appendTo('.tab');
            tr.attr('id', `${elem.id}`);
            header.forEach(head => {
                let td = $('<td></td>').appendTo(tr);
                td.attr('name', `${head.field}`);
                td.text(elem[head.field]);
            })
        })
        $('.tab').click(()=>{tableFunction(event)})
    })

    $('#asc').click(()=>{
        let filteredField;
        if (state == 'category')
            filteredField = 'category'
        else if (state == 'product'){
            filteredField = 'name';
        }else if (state == 'order'){
            filteredField = 'address';
        }
        console.log(filteredField)
        content.sort((a, b)=> {
            console.log(a[`${filteredField}`])
            if (a[filteredField].charAt(0) == b[filteredField].charAt(0)) return 0
            else if (a[filteredField].charAt(0) > b[filteredField].charAt(0)) return 1
            else return -1
        })
       init();
    })

    $('#desc').click(()=>{
        let filteredField;
        if (state == 'category')
            filteredField = 'category'
        else if (state == 'product'){
            filteredField = 'name';
        }else if (state == 'order'){
            filteredField = 'address';
        }
        content.sort((a, b)=> {
            if (a[filteredField].charAt(0) == b[filteredField].charAt(0)) return 0
            else if (a[filteredField].charAt(0) < b[filteredField].charAt(0)) return 1
            else return -1
        })
        init();
    })

    function tableFunction(event) {
        if (event.target.parentElement.className === 'forAnyChange') {
            let formFields = $('form input');
            $('.forColor').removeClass('forColor');
            id = content.findIndex(e => {
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