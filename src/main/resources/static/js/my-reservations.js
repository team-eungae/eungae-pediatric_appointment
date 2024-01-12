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
            alert('선택한 예약이 존재하지 않습니다.');
        }
    });

}
