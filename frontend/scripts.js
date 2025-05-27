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

function isNotNumberStr(str) {
    return isNaN(Number(str));
}