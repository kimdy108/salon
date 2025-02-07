import axios from 'axios'
import { baseUrl } from '@/references/config'

////////////////////////// REQUEST //////////////////////////
axios.interceptors.request.use(async function (config) {
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
  return Promise.reject(error);
});


////////////////////////// API //////////////////////////
class AuthService {
  async requestLogin (req) {
    const reqObj = {}
    reqObj.baseURL = baseUrl
    reqObj.url = '/auth/login'
    reqObj.method = 'POST'
    reqObj.headers = { accept: 'application/json' }

    if (req.data) reqObj.data = req.data
    try {
      const result = await axios(reqObj)
      result.data.retData = {user: result.data.retData, at: result.headers['accesstoken'], rt:result.headers['refreshtoken']}
      return result.data
    }
    catch (error) {
      if(!error.response) return error
      else return error?.response?.data
    }
  }
}

export default new AuthService()
