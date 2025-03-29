import { divisionChar, defineAuthKey } from '@/references/config'

import CryptoJS from 'crypto-js'

export const encryptString = (str) => {
  const key = btoa(defineAuthKey)
  const iv = CryptoJS.enc.Hex.parse(hexEncode('KIMDY@SALONHAIRS'))

  const encrypt = CryptoJS.AES.encrypt(str, CryptoJS.enc.Base64.parse(key), {
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7,
    format: CryptoJS.format.OpenSSL,
    iv: iv
  }).toString()

  return encrypt
}

export const decryptString = (str) => {
  const key = btoa(defineAuthKey)
  const iv = CryptoJS.enc.Hex.parse(hexEncode('KIMDY@SALONHAIRS'))

  const decrypt = CryptoJS.AES.decrypt(str, CryptoJS.enc.Base64.parse(key), {
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7,
    format: CryptoJS.format.OpenSSL,
    iv: iv
  })

  return decrypt.toString(CryptoJS.enc.Utf8)
}

export const hexEncode = (str) => {
  let hex, i;
  let result = "";
  for (i=0; i<str.length; i++) {
    hex = str.charCodeAt(i).toString(16);
    result += (""+hex).slice(-4);
  }
  return result
}

export const rndStr = (len) => {
  let text = ""
  const chars = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLNMOPQRSTUVWXYZ"
  for( let i=0; i < len; i++ ) {
    text += chars.charAt(Math.floor(Math.random() * chars.length))
  }
  return text;
}

export const encryptStringSalt = (str) => {
  const encFinalStr = rndStr(8) + divisionChar + str + divisionChar + rndStr(8)
  return encryptString(encFinalStr)
}

export const decryptStringSalt = (str) => {
  const decFinalStr = decryptString(str)
  return decFinalStr.split(divisionChar)[1]
}

export const makeCurrentByMonth = (value) => {
  const returnArr = []
  const months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
  if (!value) return Array(12).fill(0)
  for(const i in months) {
    const include = value.find((item) => item.month === months[i])
    returnArr.push(include ? include.count : 0)
  }
  return returnArr
}