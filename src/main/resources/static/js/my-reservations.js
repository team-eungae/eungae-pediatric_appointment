function deleteAppointment(appointmentSeq) {
    console.log(appointmentSeq)
    $.ajax({
        url: '/api/my/appointments',
        type: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify({
            appointmentSeq : appointmentSeq
        }),
        success: () => {
            window.location.href = '/my/appointments';
        },
        error: function(error) {
            alert('선택한 예약이 존재하지 않습니다.');
        }
    });

}
