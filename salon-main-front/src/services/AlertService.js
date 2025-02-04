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
}
export default new AlertService()