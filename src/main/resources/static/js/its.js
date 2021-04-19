$(document).ready(function () {
    $('.edit-button-tmp').on('click', function (event) {
        event.preventDefault();
        /*        $.get($(this).attr('href'), function (user, status) {
                    $('#userprofile-form #id').val(user.id);
                    $('#userprofile-form #firstName').val(user.firstName);
                    $('#userprofile-form #lastName').val(user.lastName);
                    $('#userprofile-form #age').val(user.age);
                    $('#userprofile-form #email').val(user.email);
                    $('#userprofile-form #password').val(user.password);
                });*/
        // $('#inputId').prop('readonly', true); когда делитим

        $.get({
            url: $(this).attr('href'),
            dataType: "html"
        }).done(function (html) {
            $('#userprofile-modal').replaceWith(html);
            showUserProfile();
        }).fail(function(jqXHR, textStatus, errorThrown) {
            console.log('textStatus: ' + textStatus );
            console.log('errorThrown: ' + errorThrown );
            console.log('jqXHR: ' + jqXHR);
        });
    });

    $('.edit-button').on('click', function (event) {
        event.preventDefault();
        $('#user-profile').modal("show").find('.modal-dialog').load($(this).attr('href'), function(response, status, xhr) {
            if (xhr.status === 404) {
                $(location).attr('href', '/admin');
            }
            $('#user-profile .modal-header h3').text('Edit User');
            let submit = $('#user-profile .modal-footer .submit');
            submit.text('Save');
            submit.addClass('btn-primary');
            $('#user-profile #method').val("patch");
        });
    });

    $('.delete-button').on('click', function (event) {
        event.preventDefault();
        $('#user-profile').modal("show").find('.modal-dialog').load($(this).attr('href'), function(response, status, xhr) {
            if (xhr.status === 404) {
                $(location).attr('href', '/admin');
            }
            $('#user-profile .modal-header h3').text('Delete User');
            $('#user-profile #password-div').remove();
            $("#user-profile #firstName").prop("readonly", true);
            $("#user-profile #lastName").prop("readonly", true);
            $("#user-profile #age").prop("readonly", true);
            $("#user-profile #email").prop("readonly", true);
            $("#user-profile #roles").prop("disabled", true);
            let submit = $('#user-profile .modal-footer .submit');
            submit.text('Delete');
            submit.addClass('btn-danger');
            $('#user-profile #method').val("delete");
        });
    });
});

function showUserProfile() {
    $('#userprofile-modal').modal();
}