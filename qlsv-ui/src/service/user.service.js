import { axiosQLSV } from "../utils/axios";

export const searchUserAPI = async (data) => {
    var config = {
      method: 'POST',
      url: '/user/search',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': ''

      },
     
      data 
    };
    return handleRequest(config)
  }

  export function createUserAPI(userDTO) {
   // let data = JSON.stringify(userDTO);
    let config = {
      method: 'post',
      url: '/user/',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': ''

      },
      data : userDTO
    };
    return handleRequest(config)
  }

  export function updateUserAPI(userDTO) {
   // let data = JSON.stringify(userDTO);
    
    let config = {
      method: 'put',
      url: '/user/',
      headers: { 
        'Authorization': ''

      },
      data : userDTO
    };
    return handleRequest(config)
  }
  //
  export function deleteUserAPI(id) {
    console.log({id});
    let config = {
      method: 'delete',
    //  url: `/user/?id=${id}` ,
      url: '/user/'+id,
      headers: { 
        'Authorization': ''

      }
    };
    return handleRequest(config)
  }


  //
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

 
  