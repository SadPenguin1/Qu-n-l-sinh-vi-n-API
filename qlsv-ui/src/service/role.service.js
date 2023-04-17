import { axiosQLSV } from "../utils/axios";

export const searchRoleAPI = async (data) => {
    var config = {
      method: 'POST',
      url: '/role/search',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': ''

      },
     
      data 
    };
    return handleRequest(config)
  }

  export function createRoleAPI(roleDTO) {
   // let data = JSON.stringify(roleDTO);
    let config = {
      method: 'post',
      url: '/role/',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': ''

      },
      data : roleDTO
    };
    return handleRequest(config)
  }

  export function updateRoleAPI(roleDTO) {
   // let data = JSON.stringify(roleDTO);
    
    let config = {
      method: 'put',
      url: '/role/',
      headers: { 
        'Authorization': ''

      },
      data : roleDTO
    };
    return handleRequest(config)
  }
  //
  export function deleteRoleAPI(id) {
    console.log({id});
    let config = {
      method: 'delete',
    //  url: `/role/?id=${id}` ,
      url: '/role/'+id,
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

 
  