const apiUrl = window.location.hostname
const apiMethod = window.location.protocol

const apiPort = process.env.VUE_APP_API_PORT

export const baseMethod = apiMethod
export const baseAddr = apiUrl
export const basePort = apiPort

export const baseUrl = `${apiMethod}//${apiUrl}:${apiPort}/api`

export const emptyUUID = '00000000-0000-0000-0000-000000000000'
export const divisionChar = '🅂🄰🄻🄾🄽'
export const divisionCharSub = '🄷🄰🄸🅁' // 🄰🄱🄲🄳🄴🄵🄶🄷🄸🄹🄺🄻🄼🄽🄾🄿🅀🅁🅂🅃🅄🅅🅆🅇🅈🅉
export const defineAuthKey = 'BKroUrRPzJokrDeFfkkBQnVEeQshnQLz'
export const masterAuthKey = '0xD3D596Ed60Ba5B424eF0D655cD1e237eB0bcd2cD'

export const idReg            = /^[A-Za-z0-9]{5,20}$/
export const nameReg          = /^[가-힣a-zA-Z0-9. ()<>-]{2,20}$/
export const phoneReg         = /^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$/
export const emailReg         = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/
export const passwdReg        = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*()])[A-Za-z\d~!@#$%^&*()]{8,}$/
export const companyNumberReg = /^[0-9]{10}$/