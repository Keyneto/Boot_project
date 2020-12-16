/**
 * My first experience using JavaScript.
 * @Author Senya Sheykin
 */
$(document).ready(function ($) {

    fetch('/admin').then(result => {result.json().then(data => {JsonToTable(data);})});

    jQuery.getJSON('/current_user', function(data){

        getElById("currentEmail").innerHTML = data.email;
        getElById("currentRole").innerHTML = getRoleToString(data.role);
    });

    jQuery.getJSON('/current_user', function(info){

        if (info.role.indexOf('ROLE_USER') == 0 &&
            info.role.indexOf('ROLE_ADMIN') == -1) {
            document.getElementById('tabSidebar').innerHTML = "" +
                "<li class=\"nav-item\">\n" +
                "    <a class=\"nav-link active\" data-toggle=\"tab\" href=\"#user\">User</a>\n" +
                "</li>";

            $('#user').attr('class', 'tab-pane fade show active');
            $('#admin').attr('class', 'tab-pane fade');
        }
    });

    jQuery.getJSON('/current_user', function(data){

        getElById("currentUserId").innerHTML = data.id;
        getElById("currentUserUserName").innerHTML = data.userName;
        getElById("currentUserEmail").innerHTML = data.email;
        getElById("currentUserRole").innerHTML = getRoleToString(data.role);

    });

    jQuery('.currentUserPage #createNewUserButton').on('click', function (event) {

        event.preventDefault();

        let newUser = {
            userName: getElById("newUsername").value,
            email: getElById("newEmail").value,
            password: getElById("newPassword").value,
            role: getUsedSelectElement('newRole')
        };

        fetch('/admin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(newUser)
        }).then(result => result.json()).then(json => JsonToTable(json));

        $('#all_users').attr('class', 'tab-pane fade show active border');
        $('#tabAllUsers').attr('class', 'nav-link active');
        $('#tabNewUser').attr('class', 'nav-link');
        $('#create_new_user').attr('class', 'tab-pane fade border');
    })
});


function editModal(id) {

    let href = '/admin/' + id;

    fetch(href).then(
        result => {
            result.json().then(
                data => {
                    $('.myForm #editModalId').val(data.id);
                    $('.myForm #editModalUserName').val(data.userName);
                    $('.myForm #editModalEmail').val(data.email);
                    $('.myForm #editModalRole').val(data.role);
                }
            )
        }
    )

    jQuery('#editModal').modal();
}


function deleteModal(id) {

    let link = '/admin/' + id;

    fetch(link).then(
        result => {
            result.json().then(
                data => {
                    $('.myDeleteForm #deleteModalId').val(data.id);
                    $('.myDeleteForm #userName').val(data.userName);
                    $('.myDeleteForm #email').val(data.email);
                    $('.myDeleteForm #role').val(data.role);
                }
            )
        }
    )

    jQuery('#deleteModal').modal();
}


function editUser() {

    let userInfo = {
        id: getElById("editModalId").value,
        userName: getElById("editModalUserName").value,
        email: getElById("editModalEmail").value,
        password: getElById("editModalPassword").value,
        role: getUsedSelectElement('editModalRole')
    };

    fetch('/admin', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(userInfo)
    }).then(result => result.json()).then(json => JsonToTable(json));
};


function deleteUser() {

    let idUser = {
        id: getElById("deleteModalId").value,
    };

    fetch('/admin', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(idUser)
    }).then(result => result.json()).then(json => JsonToTable(json));
}


function JsonToTable(data) {

    let newCode = "";

    data.forEach((x) => {
        newCode += "<tr>"
            + "<td>" + x.id + "</td>"
            + "<td>" + x.userName + "</td>"
            + "<td>" + x.email + "</td>"
            + "<td>" + getRoleToString(x.role) + "</td>"
            + "<td><a onclick=\"editModal("+x.id+");\" " +
            "class=\"btn btn-info\">Edit</a></td>"
            + "<td><a onclick=\"deleteModal("+x.id+");\" " +
            "class=\"btn btn-danger\">Delete</a></td>"
            + "</tr>";
    })

    getElById("data").innerHTML = newCode;
}

function getRoleToString(roles) {
    return roles.join(' ').split('ROLE_').join('');
}

function getElById(id) {
    return document.getElementById(id);
}

function getUsedSelectElement(idElement) {

    let len= getElById(idElement).options.length;
    let UsedSelectElements = new Array;
    let i =0;

    for (let n = 0; n < len; n++) {
        if (getElById(idElement).options[n].selected == true) {
            UsedSelectElements[i] = getElById(idElement).options[n].value;
            i++;
        }
    }

    return UsedSelectElements;
}











