/**
 *
 */
$(document).ready(function ($){

    $('.eBtn').on("click", function(event) {

        event.preventDefault()

        let href = $(this).attr('href');

        jQuery.getJSON(href, function(data){
            $('.myForm #id').val(data.id);
            $('.myForm #username').val(data.userName);
            $('.myForm #email').val(data.email);
            $('.myForm #role').val(data.role);
        });

        $('#editModal').modal();
    });

    $('.dBtn').on("click", function(event) {

        event.preventDefault()

        let href = $(this).attr('href');

        jQuery.getJSON(href, function(data){
            $('.myDeleteForm #id').val(data.id);
            $('.myDeleteForm #username').val(data.userName);
            $('.myDeleteForm #email').val(data.email);
            $('.myDeleteForm #role').val(data.role);
        });

        $('#deleteModal').modal();
    });
});
