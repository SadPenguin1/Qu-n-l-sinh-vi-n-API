import { axiosQLSV } from "../utils/axios";

export const searchCourseAPI = async (data) => {
    var config = {
      method: 'POST',
      url: '/course/search',
      headers: { 
        'Content-Type': 'application/json',
        'Authorization': ''

      },
     
      data 
    };
    return handleRequest(config)
  }

  export function createCourseAPI(courseDTO) {
    let data = JSON.stringify(courseDTO);
    let config = {
      method: 'post',
      url: '/course/',
      headers: {
        'Content-Type': 'application/json', 
        'Authorization': ''

      },
      data : data
    };
    return handleRequest(config)
  }

  export function updateCourseAPI(courseDTO) {
    let data = JSON.stringify(courseDTO);
    
    let config = {
      method: 'put',
      url: '/course/',
      headers: { 
        'Authorization': ''

      },
      data : data
    };
    return handleRequest(config)
  }
  export function deleteCourseAPI(id) {
    console.log({id});
    let config = {
      method: 'delete',
      url: '/course/'+ id ,
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

 
  