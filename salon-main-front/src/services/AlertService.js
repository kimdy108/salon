import Swal from 'sweetalert2'

class AlertService {
  constructor() {
    this.confirmAlert = this._getConfirmAlert()
    this.normalAlert = this._getNormalAlert()
  }

  normalAlertAction(html, title, btnStr, type) {
    const alertConfig = {
      title,
      html,
      confirmButtonText: btnStr,
      icon: type
    }
    
    this.normalAlert.fire(alertConfig)
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
        if(confirmKey != null) callback(confirmKey)
        else callback()
      }
    })
  }

  _getNormalAlert() {
    return Swal.mixin({
      customClass: {
        confirmButton: 'w-full py-4 text-md bg-emerald-200 text-emerald-600 hover:bg-emerald-300 !rounded-lg'
      },
      buttonsStyling: false
    })
  }

  _getConfirmAlert() {
    return Swal.mixin({
      showConfirmButton: true,
      showCancelButton: true,
      customClass: {
        confirmButton: 'w-[45%] mx-2 py-4 text-md bg-emerald-200 text-emerald-600 hover:bg-emerald-300 !rounded-lg',
        cancelButton: 'w-[45%] mx-2 py-4 text-md text-gray-700 bg-white hover:bg-gray-100 !border border-gray-200 !rounded-lg'
      },
      buttonsStyling: false
    })
  }
}

export default new AlertService()