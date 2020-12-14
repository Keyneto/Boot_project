/**
 * My first experience using JavaScript.
 * @Author Senya Sheykin
 */
$(document).ready(function ($) {

    fetch('/admin').then(result => {result.json().then(data => {JsonToTable(data);})});

    jQuery.getJSON('/current_user', function(data){

        document.getElementById("currentEmail").innerHTML = data.email;
        document.getElementById("currentRole").innerHTML = data.role;
    });

    jQuery.getJSON('/current_user', function(info){

        if (info.role == 'USER') {
            document.getElementById('tabSidebar').innerHTML = "" +
                "<li class=\"nav-item\">\n" +
                "    <a class=\"nav-link active\" data-toggle=\"tab\" href=\"#user\">User</a>\n" +
                "</li>";

            $('#user').attr('class', 'tab-pane fade show active');
            $('#admin').attr('class', 'tab-pane fade');
        }
    });

    jQuery.getJSON('/current_user', function(data){

        document.getElementById("currentUserId").innerHTML = data.id;
        document.getElementById("currentUserUserName").innerHTML = data.userName;
        document.getElementById("currentUserEmail").innerHTML = data.email;
        document.getElementById("currentUserRole").innerHTML = data.role;
    });

    jQuery('.currentUserPage #createNewUserButton').on('click', function (event) {

        event.preventDefault();

        let newUser = {
            userName: document.getElementById("newUsername").value,
            email: document.getElementById("newEmail").value,
            password: document.getElementById("newPassword").value,
            role: document.getElementById("newRole").value,
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
        id: document.getElementById("editModalId").value,
        userName: document.getElementById("editModalUserName").value,
        email: document.getElementById("editModalEmail").value,
        password: document.getElementById("editModalPassword").value,
        role: document.getElementById("editModalRole").value
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
        id: document.getElementById("deleteModalId").value,
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
            + "<td>" + x.role + "</td>"
            + "<td><a onclick=\"editModal("+x.id+");\" " +
            "class=\"btn btn-info\">Edit</a></td>"
            + "<td><a onclick=\"deleteModal("+x.id+");\" " +
            "class=\"btn btn-danger\">Delete</a></td>"
            + "</tr>";
    })

    document.getElementById("data").innerHTML = newCode;
}











