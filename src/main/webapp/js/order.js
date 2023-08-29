'use strict';

(function ($) {

})(jQuery);

function open_delivery(roadName, addrDetail, zipCode, deliveryStatus) {
    Swal.fire({
        title: '배송지 정보',
        html: `<b>도로명 주소</b> : ${roadName} <br/><br/> <b>상세주소</b> : ${addrDetail} <br/><br/> <b>우편번호</b> : ${zipCode} <br/><br/> <b>배송상태</b> : ${deliveryStatus}`,
        icon: 'info',
        confirmButtonText: '확인'
    })

}