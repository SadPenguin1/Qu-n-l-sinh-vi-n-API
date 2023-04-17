import { axiosQLSV } from "../utils/axios";

export const searchStudentAPI = async (data) => {
    var config = {
      method: 'POST',
      url: '/student/search',
      headers: { 
        'Authorization': ''

      },
     
      data 
    };
    return handleRequest(config)
  }

  export function createStudentAPI(studentDTO) {
    let data = JSON.stringify(studentDTO);
    let config = {
      method: 'post',
      url: '/student/',
      headers: { 
        'Authorization': ''

      },
      data : data
    };
    return handleRequest(config)
  }

  export function updateStudentAPI(studentDTO) {
    let data = JSON.stringify(studentDTO);
    
    let config = {
      method: 'put',
      url: '/student/',
      headers: { 
        'Authorization': ''

      },
      data : data
    };
    return handleRequest(config)
  }
  export function deleteStudentAPI(id) {
    console.log({id});
    let config = {
      method: 'delete',
      url: '/student/'+ id ,
      headers: { 
        'Authorization': ''

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

 
  