import { axiosQLSV } from "../utils/axios";

export const searchScoreAPI = async (data) => {
    var config = {
      method: 'POST',
      url: '/score/search',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': ''

      },
     
      data 
    };
    return handleRequest(config)
  }

  export function createScoreAPI(scoreDTO) {
    let data = JSON.stringify(scoreDTO);
    let config = {
      method: 'post',
      url: '/score/',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': ''

      },
      data : data
    };
    return handleRequest(config)
  }

  export function updateScoreAPI(scoreDTO) {
    let data = JSON.stringify(scoreDTO);
    
    let config = {
      method: 'put',
      url: '/score/',
      headers: { 
        'Authorization': ' '

      },
      data : data
    };
    return handleRequest(config)
  }
  
  export function deleteScoreAPI(id) {
    console.log({id});
    let config = {
      method: 'delete',
      maxBodyLength: Infinity,
      url: '/score/'+ id ,
      headers: { 
        'Authorization': ' '

      }
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

 
  