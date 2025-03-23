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

export const idReg            = /^[A-Za-z0-9]{4,20}$/
export const nameReg          = /^[가-힣a-zA-Z0-9. ()<>-]{2,20}$/
export const phoneReg         = /^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$/
export const emailReg         = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/
export const passwdReg        = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*()])[A-Za-z\d~!@#$%^&*()]{8,}$/
export const companyNumberReg = /^[0-9]{10}$/

export const userRoleList = [
    { key: 0, name: '최고관리자', value: 'MASTER' },
    { key: 1, name: '관리자', value: 'ADMIN' },
    { key: 2, name: '매니저', value: 'MANAGER' },
    { key: 3, name: '운영자', value: 'MEMBER' },
]

export const timeRange = [
    "00:00","00:30",
    "01:00","01:30",
    "02:00","02:30",
    "03:00","03:30",
    "04:00","04:30",
    "05:00","05:30",
    "06:00","06:30",
    "07:00","07:30",
    "08:00","08:30",
    "09:00","09:30",
    "10:00","10:30",
    "11:00","11:30",
    "12:00","12:30",
    "13:00","13:30",
    "14:00","14:30",
    "15:00","15:30",
    "16:00","16:30",
    "17:00","17:30",
    "18:00","18:30",
    "19:00","19:30",
    "20:00","20:30",
    "21:00","21:30",
    "22:00","22:30",
    "23:00","23:30",
    "24:00"
]