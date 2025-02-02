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
