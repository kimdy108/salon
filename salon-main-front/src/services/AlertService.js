import Swal from 'sweetalert2'

class AlertService {
  normalAlertAction(html, title, btnStr, type) {
    const alertConfig = {
      title,
      html,
      confirmButtonText: btnStr,
      icon: type
    }
    
    Swal.fire(alertConfig)
  }

  confirmAlertAction(html, title, confirmStr, cancelStr, type, confirmKey, callback) {
    this.confirmAlert.fire({
      title,
      html,
      icon: type,
      confirmButtonText: confirmStr,
      cancelButtonText: cancelStr
    })
    .then((result) => {
      if (result.isConfirmed) {
        if(confirmKey!=null) callback(confirmKey)
        else callback()
      }
    })
  }
}
export default new AlertService()