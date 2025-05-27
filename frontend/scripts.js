function setCookie(cname, cvalue) {
    document.cookie = cname + "=" + cvalue + ";path=/..";
}

function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function getAllFromList(type) {
    if (type == null) throw new Error("Tipo de elemento da lista indefinido!")
    let token = getCookie('token')
    let data2;
    return fetch('http://localhost:8080/' + type + '/all', {
        method: 'GET',
        headers: {
            'Content-Type':'application/json',
            'Authorization':token
        },
        }).then(response => response.json()).then(data => {
        console.log('POST Request Data:', data);
        return data;
    })
        .catch(error => {
            console.error('Error:', error);
        })
    }

function getTypenameById(type, id) {
    if (type == null || id == null) throw new Error("Tipo ou ID não identificado!")
    let token = getCookie('token')
    return fetch('http://localhost:8080/' + type + '/' + id, {
        method: 'GET',
        headers: {
            'Content-Type':'application/json',
            'Authorization':token
        },

    }).then(response => response.json()).then(data => {
        console.log('GET Request Data:', data);
        return data;
    })
        .catch(error => {
            console.error('Error:', error);
            return null;
        });
}

function postTypename(type,list) {
    if (type == null  || list == null) throw new Error("Tipo ou ID não identificado!")
    console.log(list)
    let token = getCookie('token')
    return fetch('http://localhost:8080/' + type, {
        method: 'POST',
        headers: {
            'Content-Type':'application/json',
            'Authorization':token,
        },
        'body':list

    }).then(response => response.json())
        .catch(error => {
            console.error('Error:', error);
            return null;
        });
}

function putTypenameById(type,id,list) {
    if (type == null || id == null || list == null) throw new Error("Tipo ou ID não identificado!")
    console.log(list)
    let token = getCookie('token')
    return fetch('http://localhost:8080/' + type + '/' + id, {
        method: 'PUT',
        headers: {
            'Content-Type':'application/json',
            'Authorization':token,
        },
        'body':list

    }).then(response => response.json())
        .catch(error => {
            console.error('Error:', error);
            return null;
        });
}

function deleteTypenameById(type,id) {
    if (type == null || id == null) throw new Error("Tipo ou ID não identificado!")
    let token = getCookie('token')
    return fetch('http://localhost:8080/' + type + '/' + id, {
        method: 'DELETE',
        headers: {
            'Content-Type':'application/json',
            'Authorization':token,
        },

    }).then(response => response.json())
        .catch(error => {
            console.error('Error:', error);
            return null;
        });
}

function getSelf() {
    let token = getCookie('token')
    return fetch('http://localhost:8080/users/self', {
        method: 'GET',
        headers: {
            'Content-Type':'application/json',
            'Authorization':token
        },

    }).then(response => response.json()).then(data => {
        console.log('GET Request Data:', data);
        return data;
    })
        .catch(error => {
            console.error('Error:', error);
            return null;
        });
}

function isNotNumberStr(str) {
    return isNaN(Number(str));
}

function makeList(listname,list) {
    const ul = document.getElementById(listname);
    for (let i = 0; i < list.length; i++) {
        const li = document.createElement('li')
        li.setAttribute('dbid',list[i].id)
        li.setAttribute('id','li'+list[i].name)
        li.innerText= list[i].name
        ul.appendChild(li)
    }
}

function createEditButton(where,type) {
    let editbutton = document.createElement('button')
    editbutton.appendChild(document.createTextNode("Editar"))
    editbutton.setAttribute('id','editbutton')
    editbutton.setAttribute('onclick','createEditField(this,\''+type+'\')')
    where.appendChild(editbutton)
    return editbutton
}
function createEditField(button, type) {
    const where = button.parent
    const searcheditem = document.getElementById('searcheditem')
    let list = JSON.parse(searcheditem.innerText) //gambiarra? não sei se funciona
    let editdiv = document.createElement('div')
    editdiv.setAttribute('id','editdiv')
    for (const listKey in list) {
        if(listKey !== 'id') addField(editdiv,listKey)
    }
    let submitbutton = document.createElement('button')
    submitbutton.setAttribute('onclick','submitEditTypename(this,\'' + type + '\')')
    submitbutton.innerText = 'Salvar Mudanças'
    editdiv.appendChild(submitbutton)
    where.append(editdiv)
}
function submitEditTypename(button,type) {
    const editdiv = button.parent
    let valuelist = readFields(editdiv)
    let id = document.getElementById('searcheditem').getAttribute('dbid')

    putTypenameById(type,id,valuelist)

}
function readFields(where) {
    let nodelist = where.childNodes
    let valuelist = {}
    nodelist.forEach((nodelistKey) => {
        if(nodelistKey.type === 'text') {
            let key = nodelistKey.id
            if(nodelistKey.value.length === 0) {
                valuelist[key] = null
            }
            else if(isNotNumberStr(nodelistKey.value)) {
                valuelist[key] = nodelistKey.value
            }
            else {
                valuelist[key] = Number(nodelistKey.value)
            }
        }
    })
    valuelist = JSON.stringify(valuelist)
    return valuelist
}
function createSearchedItem(where, id, data) {
    let searcheditem = document.createElement('pre')
    searcheditem.appendChild(document.createTextNode(JSON.stringify(data)))
    searcheditem.setAttribute('id','searcheditem')
    searcheditem.setAttribute('dbid',id)
    where.appendChild(searcheditem)
    return searcheditem
}

function addField(where,key) {
    let textfield = document.createElement('input')
    textfield.setAttribute('type','text')
    textfield.setAttribute('id',key)
    textfield.setAttribute('placeholder',key)
    where.appendChild(textfield)
}