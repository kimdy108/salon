import axios from 'axios'
import { useUserStore } from '@/stores/userStore'
import { baseUrl } from '@/references/config'
import { encryptStringSalt, decryptStringSalt } from '@/utils/common'

////////////////////////// REQUEST //////////////////////////
axios.interceptors.request.use(async function (config) {
  const userStore = useUserStore()

  if(userStore.getCurrentUser != null && userStore.getCurrentUser.at != null) {
      config.headers['Authorization'] = `Bearer ${userStore.getCurrentUser.at}`
  }

  return config;
},
function (error) {
  return Promise.reject(error);
});

////////////////////////// RESPONSE //////////////////////////
axios.interceptors.response.use(function (response) {
  return response;
},
async function (error) {
  if(typeof error !== 'undefined' && error.response.data.retCode == 9997) {
    console.log("인증재처리")
    const userStore = useUserStore()
    
    const refreshResult = await authRefreshRequest();
    if(refreshResult.retStatus) {
      const item = userStore.getCurrentUser
      item.at = refreshResult.retData
      userStore.setCurrentUser(item)

      return axios(error.config);
    }
    else {
      userStore.setUserLogout()
      if(confirm('로그인정보가 만료되었습니다.\n다시 로그인이 필요 합니다.')) {
        window.location.reload()
      }
    }
  }
  
  return Promise.reject(error);
});


////////////////////////// API //////////////////////////

const authRefreshRequest = async () => {
  const userStore = useUserStore();

  const reqObj = {} 
  reqObj.baseURL = baseUrl
  reqObj.url = '/auth/refresh'
  reqObj.method = 'POST'

  reqObj.data = {
    userAccount : encryptStringSalt(decryptStringSalt(userStore.getCurrentUser.uac) + '-' + decryptStringSalt(userStore.getCurrentUser.sgd)),
    refreshToken: encryptStringSalt(userStore.getCurrentUser.rt)
  }
  reqObj.headers = { accept: 'application/json' }
  try {
    const result = await axios(reqObj)
    return {
      retCode: 1000,
      retData: result.headers['accesstoken'],
      retHttpCode: 202,
      retHttpStatus: "ACCEPTED",
      retStatus: true
    }
  }
  catch (error) {
    return {
      retCode: error.response.data.retCode,
      retData: error.response.data.retData,
      retHttpCode: error.response.data.retHttpCode,
      retHttpStatus: error.response.data.retHttpStatus,
      retStatus: error.response.data.retStatus
    }
  }
}

class ApiService {
  async requestAPI(req) {
    const reqObj = {}
    reqObj.baseURL = baseUrl
    reqObj.url = req.url
    reqObj.method = req.method

    if (req.params) reqObj.params = req.params
    if (req.data) reqObj.data = req.data
    if (req.headers) reqObj.headers = req.headers
    if (req.responseType) reqObj.responseType = req.responseType
    try {
      const result = await axios(reqObj)
      return result.data
    }
    catch (error) {
      if(!error.response) return error
      else return error?.response?.data
    }
  }
}

export default new ApiService()
