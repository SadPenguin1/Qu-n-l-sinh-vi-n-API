import { axiosQLSV } from '../utils/axios';

export const loginAPI = async (data) => {
  var config = {
    method: 'POST',
    url: '/api/auth/signin',
    headers: { 
      'Authorization': 'eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJzdWIiOiJoYSIsImlhdCI6MTY4MDUxMTIwMCwiZXhwIjoxNjgwNTE0ODAwfQ.cDyGhu5FEzw0WHYpBP1K18OE0r4WBFE7zBvpkNIYzVs'
          
    },
   
    data 
  };
  return handleRequest(config)
}

const handleRequest = async (config) => {
  try {
    const resp = await axiosQLSV(config);
    return resp.data;
  } catch (error) {
    console.log(error);
    if (error.response)
      return (error.response.data)

    return ({ code: "408", message: error.message })
  }
}
