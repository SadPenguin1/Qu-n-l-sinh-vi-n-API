import React, { useEffect, useState } from "react";
import { searchScoreAPI,createScoreAPI,updateScoreAPI,deleteScoreAPI } from "../../service/score.service";


export function Score() {
    
    let [scoreArr, setScoreArr] = useState([])
    let [data,setData] = useState({
        "value": "%%",
        "page": 0,
        "size": 10
    })
    useEffect(() => {
        const readData = async () => {
            // async request.
            const response = await searchScoreAPI(data);
            setScoreArr(response.data)
            if (response) {
                return;
            }
        }
        readData();
 
    }, [data])

    let handleTextChange = (e)=>{
        setData({...data,[e.target.name]:e.target.value})
    }

    let [scoreDTO, setScoreDTO] = useState();

      let handleChangeScore = (e) => {
        setScoreDTO({ ...scoreDTO, [e.target.name]: e.target.value });
      };
    
    let createScore = async () => {
        try {
          console.log(scoreDTO);
          let resp = await createScoreAPI(scoreDTO); 
          console.log("Tạo course thành công");
        } catch (err) {
          console.log(err);
        }
      };

      let updateScore = async () => {
        try {
          console.log(scoreDTO);
          let resp = await updateScoreAPI(scoreDTO); 
          console.log("Update data: ", resp.data);
          console.log("Update course thành công");
          setScoreArr(resp.data.data);
        } catch (err) {
          console.log(err);
        }
      };

      let deleteScore = async (id) => {
        try {
          let yes = window.confirm("Are you sure want to delete this item ?");
          let resp = await deleteScoreAPI(id); 
          let newArray = scoreArr.filter(function (item) {
           
            return item.id !== id;
          });
          console.log("Delete course thành công");
          setScoreArr(newArray.data.data);
         
        } catch (err) {
          console.log(err);
        }
      }

    return (
        <div>
            
      <th>Create Score</th>
      <form>
        <input name="id" onChange={handleChangeScore} placeholder="ID.." />
        <input name="score" onChange={handleChangeScore} placeholder="Score.."/>
        <input name="student" onChange={handleChangeScore} placeholder="student.."/>
        <input name="course" onChange={handleChangeScore} placeholder="course.." />
      

        <button type="button"  onClick={createScore}> Save </button>
    
            <input name ="value" onChange={handleTextChange} placeholder="search..."></input>
            <select name ="size" onChange={handleTextChange}>
                <option value="10">10</option>
                <option value="2">2</option>

            </select>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Score</th>
                    <th>Student</th>
                    <th>Course</th>
                    
                </tr>
                </thead>
                <tbody>
                {
                    scoreArr.map(item => {
                        return (
                            <tr key={item.id}>
                                <td>{item.id}</td>
                                <td>{item.score}</td>
                                <td>{item.student.id}</td>
                                <td>{item.course.id}</td>  
                                                    
                                
                                <td> <button type="button" onClick={() => deleteScore(item.id)}>Delete </button></td>
                                <td><button type="button" onClick={() => updateScore(item.id)}>Update</button></td>
                            </tr>
                        )
                    })
                }
             </tbody>   
            </table>
           </form>
         </div>
    )
 }