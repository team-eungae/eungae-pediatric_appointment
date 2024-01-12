function deleteAppointment(appointmentSeq) {
    console.log(appointmentSeq)
    $.ajax({
        url: '/api/my/appointments',
        type: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify({
            appointmentSeq : appointmentSeq
        }),
        success: (response) => {
            console.log(response)
            window.location.href = '/my/appointments';
        },
        error: function(error) {
            console.error('Error updating user:', error.message);
            alert('The provided ID does not exist.', error);
        }
    });

}
